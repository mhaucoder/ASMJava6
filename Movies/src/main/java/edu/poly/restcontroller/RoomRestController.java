package edu.poly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.poly.dao.MovieDAO;
import edu.poly.dao.MovietheaterDAO;
import edu.poly.dao.RoomDAO;
import edu.poly.entity.Movie;
import edu.poly.entity.Movietheater;
import edu.poly.entity.Room;

@CrossOrigin("*")
@RestController
public class RoomRestController {
	
	@Autowired
	RoomDAO dao;
	@Autowired
	MovietheaterDAO mtDao;
	
	@GetMapping("/rest/room/{mtId}")
	public List<Room> getRoomByMtId(@PathVariable String mtId){
		Movietheater mt = new Movietheater();
		try {
			mt = mtDao.findById(mtId).get();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movietheater not found");
		}
		return dao.findByMovietheaterRoom(mt);
	}
}
