package edu.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.poly.entity.Account;

import java.util.List;
public interface UserDAO extends JpaRepository<Account, String>{

    List<Account> findAllByActiveEquals(Boolean active);
    Account findAccountByIdAndPassword(String id, String password);
}
