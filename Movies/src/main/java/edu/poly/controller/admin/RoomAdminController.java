package edu.poly.controller.admin;

import java.util.List;

import edu.poly.entity.Movietheater;
import edu.poly.service.RoomService;
import edu.poly.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import edu.poly.entity.Room;

@Controller
@RequestMapping("movies/admin/rooms")
public class RoomAdminController {
	@Autowired
    RoomService roomService;

    @Autowired
    TheaterService theaterService;

    @RequestMapping("")
    public String rooms(Model model) {
        List<Room> list = roomService.findAll();
        model.addAttribute("list", list);
        return "admin/pages/room";
    }
    
    @GetMapping("/edit")
    public String edit(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("update", false);
        return "admin/pages/room_edit";
    }

    @GetMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id) {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        model.addAttribute("update", true);
        return "admin/pages/room_edit";
    }
    
    @PostMapping("/save")
    public String save(Model model,
                       @Validated @ModelAttribute("room") Room room,
                       Errors errors,
                       @RequestParam("movietheaterRoom.id") String theater_id
                       ) {
        if(theater_id.equals("")) {
            errors.rejectValue("movietheaterRoom.id", "field.required", "Not Empty");
            return "admin/pages/room_edit";
        }
        Movietheater theater = theaterService.findById(theater_id);
        if(theater == null) {
            errors.rejectValue("movietheaterRoom.id", "field.required", "Don't have theater");
            return "admin/pages/room_edit";
        }


        if(errors.hasErrors()) {
            return "admin/pages/room_edit";
        }
        room.setMovietheaterRoom(theater);
        roomService.save(room);
        return "redirect:/movies/admin/rooms";
    }
    
    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable String id) {
        roomService.deleteById(id);
        return "redirect:/movies/admin/rooms";
    }

    @ModelAttribute("theaters")
    public List<Movietheater> theaters () {
        return theaterService.findAll();
    }
}
