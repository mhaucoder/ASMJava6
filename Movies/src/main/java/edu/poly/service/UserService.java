package edu.poly.service;

import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.poly.dao.UserDAO;
import edu.poly.entity.Account;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	UserDAO usDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			Account account = usDao.findById(username).get();
			String password = account.getPassword();
			String[] roles = account.getAuthorities().stream()
							.map(au -> au.getRole().getId())
							.collect(Collectors.toList()).toArray(new String[0]);
			return User.withUsername(username)
					   .password(new BCryptPasswordEncoder().encode(password))
					   .roles(roles).build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new UsernameNotFoundException(username + " not found!");
		}
	}

}
