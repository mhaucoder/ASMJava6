package edu.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.poly.entity.Role;

import java.util.List;

public interface RoleDAO extends JpaRepository<Role, String>{
    List<Role> findAllByActiveEquals(Boolean active);

}
