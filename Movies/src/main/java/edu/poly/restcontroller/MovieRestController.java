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
import edu.poly.entity.Movie;

@CrossOrigin("*") // Ngăn chặn việc truy cập
@RestController
public class MovieRestController {

	@Autowired
	MovieDAO dao;

	@GetMapping("/rest/movies")
	public List<Movie> getAll() {
		return dao.findAll();
	}

	@GetMapping("/rest/movies/{id}") // Mục đích API trả về phim có id
	public Movie getOne(@PathVariable("id") String id) {
		if (!(dao.findById(id) != null)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found");
		}
		return dao.findById(id).get(); // >>> JSON ( ReponseBoby )
	}

	@PostMapping("/rest/movies")
	public Movie post(@RequestBody Movie movie) {
		if (dao.findById(movie.getId()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "movie exist");
		}
		dao.save(movie);
		return movie;
	}

	@PutMapping("/rest/movies/{id}")
	public Movie put(@PathVariable("id") String id, @RequestBody Movie movie) {
		if (!(dao.findById(movie.getId()) != null)) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "movie not found");
		}
		dao.save(movie);
		return movie;
	}

	@DeleteMapping("/rest/movies/{id}")
	public void delete(@PathVariable("id") String id) {
		if (!(dao.findById(id) != null)) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "movie not found");
		}
		dao.deleteById(id);
	}
}
