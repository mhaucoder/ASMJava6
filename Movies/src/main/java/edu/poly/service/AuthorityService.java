package edu.poly.service;

import edu.poly.dao.AuthorityDAO;
import edu.poly.dao.RoleDAO;
import edu.poly.entity.Authority;
import edu.poly.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    AuthorityDAO dao;

    public List<Authority> findAll() {
        return dao.findAllAuth();
    }

    public Authority findById(Integer id) {
        return dao.findById(id).orElse(null);
    }

    public Authority save(Authority authority) {
        return dao.save(authority);
    }

    public void delete(Authority authority) {
        dao.delete(authority);
    }

    public void deleteById(Integer id) {
        dao.deleteById(id);
    }
}
