package edu.poly.controller.admin;

import edu.poly.entity.Account;
import edu.poly.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("movies/admin/accounts")
public class AccountAdminController {

    @Autowired
    AccountService accountService;

    @RequestMapping("")
    public String accounts(Model model) {
        List<Account> list = accountService.findAll();
        model.addAttribute("list", list);
        return "admin/pages/account";
    }

    @GetMapping("edit")
    public String edit(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        model.addAttribute("update", false);
        return "admin/pages/account_edit";
    }

    @GetMapping("edit/{id}")
    public String update(Model model,
                         @PathVariable String id) {
        Account account = accountService.findById(id);
        model.addAttribute("account", account);
        model.addAttribute("update", true);
        return "admin/pages/account_edit";
    }

    @PostMapping("save")
    public String save(Model model,
                       @Validated @ModelAttribute("account") Account account, Errors errors) {
        if(errors.hasErrors()) {
            model.addAttribute("update", false);
            return "admin/pages/account_edit";
        }
        accountService.save(account);
        return "redirect:/movies/admin/accounts";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable String id) {
        accountService.deleteById(id);
        return "redirect:/movies/admin/accounts";
    }
}
