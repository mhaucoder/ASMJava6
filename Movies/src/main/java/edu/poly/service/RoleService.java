package edu.poly.service;

import edu.poly.dao.RoleDAO;
import edu.poly.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService {
    @Autowired
    RoleDAO dao;

    public List<Role> findAll() {
        return dao.findAllByActiveEquals(true);
    }

    public Role findById(String id) {
        return dao.findById(id).orElse(null);
    }

    public Role save(Role role) {
        role.setActive(true);
        return dao.save(role);
    }

    public void delete(Role role) {
        role.setActive(false);
        dao.save(role);
    }

    public void deleteById(String id) {
        delete(Objects.requireNonNull(dao.findById(id).orElse(null)));
    }
}
