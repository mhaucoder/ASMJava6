package edu.poly.controller.admin;

import edu.poly.dao.MovieplayDAO;
import edu.poly.entity.Movie;
import edu.poly.entity.Movieplay;
import edu.poly.entity.Movietheater;
import edu.poly.entity.Room;
import edu.poly.service.MovieService;
import edu.poly.service.RoomService;
import edu.poly.service.TheaterService;
import edu.poly.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("movies/admin/movie-play")
public class MoviePlayAdminController {

    @Autowired
    MovieplayDAO dao;

    @Autowired
    TheaterService theaterService;

    @Autowired
    RoomService roomService;

    @Autowired
    MovieService movieService;

    @RequestMapping("")
    public String moviePlay(Model model) {
        List<Movieplay> list = dao.findAll();
        model.addAttribute("list", list);
        return "admin/pages/movie_play";
    }

    @GetMapping("edit")
    public String edit(Model model) {
        Movieplay movieplay = new Movieplay();
        model.addAttribute("moviePlay", movieplay);
        return "admin/pages/movie_play_edit";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model,
                       @PathVariable Integer id) {
        Movieplay movieplay = dao.findById(id).orElse(null);
        model.addAttribute("moviePlay", movieplay);
        return "admin/pages/movie_play_edit";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("moviePlay") Movieplay movieplay,
                       Errors errors,
                       @RequestParam("movie.id") String movieId,
                       @RequestParam("roomMp.id") String roomId,
                       @RequestParam("startTime") String startTime) {
        if(movieId.equals("")) {
            errors.rejectValue("movie.id", "field.required", "Not Empty");
            return "admin/pages/movie_play_edit";
        }
        if(roomId.equals("")) {
            errors.rejectValue("roomMp.id", "field.required", "Not Empty");
            return "admin/pages/movie_play_edit";
        }

//        try {
//            LocalTime time = TimeUtils.convertStringToLocalTime(startTime);
//            movieplay.setStartTime(time);
//        }catch (Exception e) {
//            errors.rejectValue("startTime", "invalid.time", "Invalid time format");
//            return "admin/pages/movie_play_edit";
//        }

        Movie movie = movieService.findById(movieId);
        Room room = roomService.findById(roomId);

        if(errors.hasErrors()) {
            return "admin/pages/movie_play_edit";
        }

        movieplay.setMovie(movie);
        movieplay.setRoomMp(room);
        dao.save(movieplay);
        return "redirect:/movies/admin/movie-play";
    }

    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable Integer id) {
        dao.deleteById(id);
        return "redirect:/movies/admin/movie-play";
    }

    @ModelAttribute("theaters")
    public List<Movietheater> theaters() {
        return theaterService.findAll();
    }

    @ModelAttribute("movies")
    public List<Movie> movies() {
        return movieService.findAll();
    }

    @ModelAttribute("rooms")
    public List<Room> rooms() {
        return roomService.findAll();
    }

}
