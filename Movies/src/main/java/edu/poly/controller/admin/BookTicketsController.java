package edu.poly.controller.admin;

import edu.poly.dao.BookticketsDAO;
import edu.poly.entity.Booktickets;
import edu.poly.entity.Bookticketsdetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("movies/admin/book-tickets")
public class BookTicketsController {

    @Autowired
    BookticketsDAO bookticketsDAO;

    @RequestMapping("")
    public String bookTickets(Model model) {
        List<Booktickets> list = bookticketsDAO.findAll();
        model.addAttribute("list", list);
        return "admin/pages/book_tickets";
    }

    @GetMapping("/details/{id}")
    public String details(Model model,
                          @PathVariable Integer id) {
        Booktickets booktickets = bookticketsDAO.findById(id).get();
        List<Bookticketsdetail> list = booktickets.getBookticketsdetails();
        model.addAttribute("list", list);
        return "admin/pages/book_tickets_details";
    }
}
