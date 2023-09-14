package edu.poly.controller;

import edu.poly.dao.MovieDAO;
import edu.poly.dao.RoomDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/movie")
public class MovieDetailController {
    @Autowired
    MovieDAO movieDAO;
    @Autowired
    RoomDAO roomDAO;

    @RequestMapping("/detail/{id}")
    public String detailMovie(@PathVariable("id") String idMovie, Model model) {
        try {
            model.addAttribute("movieDetail", movieDAO.findById(idMovie));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "users/movie";
    }

    @RequestMapping("/search/key")
    public String searchMovieByKey(@RequestParam("searchNameMovie") String key, Model model) {
        try {
            model.addAttribute("movieSearch", movieDAO.findByNameContainingIgnoreCase(key));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "users/movieSearch";
    }

    @RequestMapping("/search/category/{key}")
    public String searchMovieByCategory(@PathVariable("key") String key, Model model) {
        try {
            model.addAttribute("movieSearch", movieDAO.findByCategorysContainingIgnoreCase(key));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "users/movieSearch";
    }

    @RequestMapping("/search/theater/{key}")
    public String searchMovieByTheater(@PathVariable("key") String key, Model model) {
        try {
            model.addAttribute("movieSearch", roomDAO.findDistinctMoviesByMovietheaterName(key));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "users/movieSearch";
    }
}
