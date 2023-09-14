package edu.poly.dao;

import edu.poly.entity.Account;
import edu.poly.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.poly.entity.Authority;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityDAO extends JpaRepository<Authority, Integer>{

    @Query("select o from Authority o where o.userAu.active = true and o.role.active = true")
    List<Authority> findAllAuth();

    @Query("SELECT o FROM Authority o WHERE o.userAu.active = TRUE AND o.role.active = TRUE AND o.id like ?1")
    List<Authority> findAuthById(Integer id);

    Boolean existsAuthoritiesByUserAuAndRole(Account account, Role role);
}
