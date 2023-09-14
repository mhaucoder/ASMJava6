package edu.poly.restcontroller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.poly.dao.BookticketsdetailDAO;
import edu.poly.dao.MovieDAO;
import edu.poly.dao.MovieplayDAO;
import edu.poly.dao.MovietheaterDAO;
import edu.poly.entity.Movie;
import edu.poly.entity.Movieplay;
import edu.poly.entity.Movietheater;

@CrossOrigin("*")
@RestController
public class MovieplayRestController {

	@Autowired
	MovieDAO mvDao;

	@Autowired
	MovieplayDAO dao;

	@Autowired
	MovietheaterDAO MtDao;

	@Autowired
	BookticketsdetailDAO bDao;

	@GetMapping("/rest/movieplay")
	public List<Movieplay> getAll() {
		return dao.findAll();
	}

	// phim đang chiếu
	@GetMapping("/rest/movies-playing")
	public List<Movieplay> getAllPlaying() {
		return dao.findAllMoviePlaying();
	}

	// phim sắp chiếu
	@GetMapping("/rest/movies-coming")
	public List<Movie> getAllComing() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(new Date());
		Date date = new Date();
		try {
			date = format.parse(day);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mvDao.findAllByReleaseDayGreaterThanEqual(date);
	}

	// Load rạp phim đang chiếu phim (truyền id phim)
	@GetMapping("/rest/movie/movietheater/{movieId}")
	public List<Object> getMovieTheaterFromMovieId(@PathVariable String movieId) {
		return MtDao.findAllByMovie(movieId);
	}

	// load ngày chiếu phim từ id rạp phim và id phim (truyền id rạp phim và id
	// phim)
	@GetMapping("/rest/movie/date/{movieTheaterId}/{movieId}")
	public List<String> loadDateFromMtandM(@PathVariable String movieTheaterId, @PathVariable String movieId) {
		return dao.loadDateFromMtIdAndMId(movieTheaterId, movieId);
	}

	// Load xuất chiếu từ id rạp phim, id phim, ngày (truyền id rạp phim, id phim,
	// ngày)
	@GetMapping("/rest/movie/time/{movieTheaterId}/{movieId}/{day}")
	public List<String> loadTimeFromMtandMandDay(@PathVariable String movieTheaterId, @PathVariable String movieId,
			@PathVariable String day) {
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = fomart.parse(day);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dao.loadTimeFromMtIdAndMIdAndDay(movieTheaterId, movieId, date);
	}

	@GetMapping("/rest/movie/time/{movieTheaterId}/{movieId}")
	public List<String> loadTimeFromMtandMandNewDay(@PathVariable String movieTheaterId, @PathVariable String movieId) {
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = fomart.parse(date.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dao.loadTimeFromMtIdAndMIdAndDay(movieTheaterId, movieId, date);
	}

	// lấy ghế đã book (truyền vào ngày, id phim, id rạp, giờ)
	@GetMapping("/rest/movie/chart/{movieTheaterId}/{movieId}/{day}/{time}")
	public List<Integer> loadTimeFromMtandMandDayAndTime(@PathVariable String movieTheaterId,
			@PathVariable String movieId,
			@PathVariable String day, @PathVariable String time) {
		SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;
		try {
			date = fomart.parse(day);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bDao.getChartBooked(date, movieId, movieTheaterId, (time+"%"));
	}

	@GetMapping("/rest/moviesplay/{id}")
	public Movieplay getOne(@PathVariable("id") String id) {
		Integer idMove = Integer.parseInt(id);
		if (!(dao.findById(idMove) != null)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "movie not found");
		}
		return dao.findById(idMove).get();
	}
	
	//get movieplay id (truyền vào ngày, id phim, id rạp, giờ)
		@GetMapping("/rest/movieplay/{movieId}/{movieTheaterId}/{day}/{time}")
		public List<Integer> getMovieplayId(@PathVariable String movieId, @PathVariable String movieTheaterId,
				@PathVariable String day, @PathVariable String time) {
			SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd");

			Date date = null;
			try {
				date = fomart.parse(day);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return dao.getMovieplayId(movieId, movieTheaterId, date, (time+"%"));
		}
}
