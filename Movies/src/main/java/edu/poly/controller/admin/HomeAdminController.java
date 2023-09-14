package edu.poly.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies/admin")
public class HomeAdminController {

    @RequestMapping("")
    public String home() {
        return "admin/pages/home";
    }

}
