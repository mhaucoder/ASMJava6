package edu.poly.controller.admin;

import edu.poly.entity.Movietheater;
import edu.poly.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("movies/admin/theaters")
public class TheaterAdminController {

    @Autowired
    TheaterService service;

    @RequestMapping("")
    public String theaters(Model model) {
        List<Movietheater> list = service.findAll();
        model.addAttribute("list", list);
        return "admin/pages/theater";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        Movietheater movietheater = new Movietheater();
        model.addAttribute("update", false);
        model.addAttribute("theater", movietheater);
        return "admin/pages/theater_edit";
    }

    @GetMapping("/edit/{id}")
    public String update(Model model, @PathVariable("id") String id) {
        Movietheater movietheater = service.findById(id);
        model.addAttribute("update", true);
        model.addAttribute("theater", movietheater);
        return "admin/pages/theater_edit";
    }

    @PostMapping("/save")
    public String save(Model model,
                       @Validated @ModelAttribute("theater") Movietheater movietheater,
                       Errors errors) {
        if(errors.hasErrors()) {
            return "admin/pages/theater_edit";
        }
        service.save(movietheater);
        return "redirect:/movies/admin/theaters";
    }

    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/movies/admin/theaters";
    }
}
