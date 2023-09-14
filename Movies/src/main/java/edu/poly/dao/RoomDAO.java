package edu.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.entity.Movie;
import edu.poly.entity.Movieplay;
import edu.poly.entity.Movietheater;
import edu.poly.entity.Room;

import java.util.List;

public interface RoomDAO extends JpaRepository<Room, String> {
    List<Room> findAllByActiveEquals(Boolean active);

    List<Room> findByMovietheaterRoom(Movietheater movietheaterRoom);

    @Query("SELECT DISTINCT m FROM Movie m " +
            "JOIN m.movieplays mp " +
            "JOIN mp.roomMp r " +
            "JOIN r.movietheaterRoom mt " +
            "WHERE mt.name = :movietheaterName")
    List<Movie> findDistinctMoviesByMovietheaterName(String movietheaterName);
}
