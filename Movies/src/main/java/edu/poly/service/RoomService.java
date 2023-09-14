package edu.poly.service;

import edu.poly.dao.MovieDAO;
import edu.poly.dao.RoomDAO;
import edu.poly.entity.Movie;
import edu.poly.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomDAO dao;

    public List<Room> findAll() {
        return dao.findAllByActiveEquals(true);
    }

    public Room findById(String id) {
        return dao.findById(id).orElse(null);
    }

    public Room save(Room room) {
        room.setActive(true);
        return dao.save(room);
    }

    public void delete(Room room) {
        room.setActive(false);
        dao.save(room);
    }

    public void deleteById(String id) {
        delete(dao.findById(id).get());
    }
}
