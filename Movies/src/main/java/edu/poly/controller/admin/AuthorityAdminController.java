package edu.poly.controller.admin;

import edu.poly.dao.AuthorityDAO;
import edu.poly.entity.Account;
import edu.poly.entity.Authority;
import edu.poly.entity.Role;
import edu.poly.service.AccountService;
import edu.poly.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("movies/admin/authority")
public class AuthorityAdminController {

    @Autowired
    AuthorityDAO authorityDAO;

    @Autowired
    RoleService roleService;

    @Autowired
    AccountService accountService;

    @RequestMapping("")
    public String authority(Model model) {
        List<Authority> list = authorityDAO.findAll();
        model.addAttribute("list", list);
        return "admin/pages/authority";
    }

    @GetMapping("edit")
    public String edit(Model model) {
        Authority authority = new Authority();
        model.addAttribute("authority", authority);
        return "admin/pages/authority_edit";
    }

    @GetMapping("edit/{id}")
    public String update(Model model,
                       @PathVariable Integer id) {
        Authority authority = authorityDAO.findById(id).orElse(null);
        model.addAttribute("authority", authority);
        return "admin/pages/authority_edit";
    }

    @PostMapping("save")
    public String save(@Validated @ModelAttribute("authority") Authority authority,
                       Errors errors,
                       @RequestParam("userAu.id") String username,
                       @RequestParam("role.id") String roleId) {
        if (username.equals("")) {
            errors.rejectValue("userAu.id", "field.required", "Not Empty");
            return "admin/pages/authority_edit";
        }
        if (roleId.equals("")) {
            errors.rejectValue("role.id", "field.required", "Not Empty");
            return "admin/pages/authority_edit";
        }
        Account account = accountService.findById(username);
        Role role = roleService.findById(roleId);
        if((account != null) && (role != null)) {
            if (authorityDAO.existsAuthoritiesByUserAuAndRole(account, role)) {
                errors.rejectValue("role.id", "field.required", "Already have account with role");
                return "admin/pages/authority_edit";
            }
            authority.setUserAu(account);
            authority.setRole(role);
        }
        if(errors.hasErrors()) {
            return "admin/pages/authority_edit";
        }
        authorityDAO.save(authority);
        return "redirect:/movies/admin/authority";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        authorityDAO.deleteById(id);
        return "redirect:/movies/admin/authority";
    }

    @ModelAttribute("accounts")
    public List<Account> accounts() {
        return accountService.findAll();
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return roleService.findAll();
    }
}
