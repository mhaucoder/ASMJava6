package edu.poly.service;

import edu.poly.dao.UserDAO;
import edu.poly.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    UserDAO userDAO;

    public List<Account> findAll() {
        return userDAO.findAllByActiveEquals(true);
    }

    public Account findById(String id) {
        return userDAO.findById(id).orElse(null);
    }

    public Account save(Account account) {
        account.setActive(true);
        return userDAO.save(account);
    }

    public void delete(Account user) {
        user.setActive(false);
        userDAO.save(user);
    }

    public void deleteById(String id) {
        delete(findById(id));
    }
}
