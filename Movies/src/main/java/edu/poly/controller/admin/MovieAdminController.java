package edu.poly.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.poly.service.MovieService;
import edu.poly.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import edu.poly.entity.Movie;

@Controller
@RequestMapping("movies/admin/movies")
public class MovieAdminController {
	@Autowired
    MovieService movieService;

    @RequestMapping("")
    public String movies(Model model) {
        List<Movie> list = movieService.findAll();
        model.addAttribute("list", list);
        return "admin/pages/movie";
    }
    
    @GetMapping("/edit")
    public String edit(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        model.addAttribute("update", false);
        return "admin/pages/movie_edit";
    }

    @GetMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id) {
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("update", true);
        return "admin/pages/movie_edit";
    }
    
    @PostMapping("/save")
    public String save(Model model,
                       @Validated @ModelAttribute("movie") Movie movie,
                       Errors errors,
                       @RequestParam("releaseDay") String releaseDay
                       ) {
        try {
            movie.setReleaseDay(DateUtils.parseStringToDate(releaseDay));
        } catch (ParseException e) {
            errors.rejectValue("releaseDay", "invalid.date", "Invalid date format");
            return "admin/pages/movie_edit";
        }

        if(errors.hasErrors()) {
    		return "admin/pages/movie_edit";
    	}

        movieService.save(movie);
        return "redirect:/movies/admin/movies";
    }
    
    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable String id) {
        movieService.deleteById(id);
        return "redirect:/movies/admin/movies";
    }
    
}
