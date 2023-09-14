package edu.poly.service;

import edu.poly.dao.MovietheaterDAO;
import edu.poly.entity.Movietheater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    @Autowired
    MovietheaterDAO dao;

    public List<Movietheater> findAll() {
        return dao.findAllByActiveEquals(true);
    }

    public Movietheater findById(String id) {
        return dao.findById(id).orElse(null);
    }

    public Boolean existsById(String id) {
        return dao.existsById(id);
    }

    public Movietheater save(Movietheater movietheater) {
        movietheater.setActive(true);
        return dao.save(movietheater);
    }

    public void delete(Movietheater movietheater) {
        movietheater.setActive(false);
        dao.save(movietheater);
    }

    public void deleteById(String id) {
        delete(findById(id));
    }
}
