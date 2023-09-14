package edu.poly.service;

import edu.poly.dao.MovieDAO;
import edu.poly.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieDAO dao;

    public List<Movie> findAll() {
        return dao.findAllByActiveEquals(true);
    }

    public Movie findById(String id) {
        return dao.findById(id).orElse(null);
    }

    public Movie save(Movie movie) {
        movie.setActive(true);
        return dao.save(movie);
    }

    public void delete(Movie movie) {
        movie.setActive(false);
        dao.save(movie);
    }

    public void deleteById(String id) {
        delete(dao.findById(id).get());
    }
}
