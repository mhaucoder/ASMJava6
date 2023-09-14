package edu.poly.controller.admin;

import java.util.List;

import edu.poly.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.entity.Role;

@Controller
@RequestMapping("movies/admin/roles")
public class RolesAdminController {
	@Autowired
    RoleService roleService;

    @RequestMapping("")
    public String roles(Model model) {
        List<Role> list = roleService.findAll();
        model.addAttribute("list", list);
        return "admin/pages/role";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        model.addAttribute("update", false);
        return "admin/pages/role_edit";
    }

    @GetMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        model.addAttribute("update", true);
        return "admin/pages/role_edit";
    }
    
    @PostMapping("/save")
    public String save(Model model,
                       @Validated @ModelAttribute("role") Role role, Errors errors) {
    	if(errors.hasErrors()) {
            model.addAttribute("update", false);
    		return "admin/pages/role_edit";
    	}
        roleService.save(role);
        return "redirect:/movies/admin/roles";
    }
    
    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable String id) {
        roleService.deleteById(id);
        return "redirect:/movies/admin/roles";
    }
}
