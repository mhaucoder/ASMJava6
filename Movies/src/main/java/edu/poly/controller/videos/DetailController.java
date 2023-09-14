package edu.poly.controller.videos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DetailController {
    @RequestMapping("/videos/{id}")
    public String detail(){
        return "layout/layout";
    }
}
