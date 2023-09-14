package edu.poly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.entity.Movie;
import edu.poly.entity.Movieplay;

public interface MovieplayDAO extends JpaRepository<Movieplay, Integer> {
    // phim đang chiếu
    @Query("select m from Movieplay m where m.movie.id in (Select distinct mp.movie.id from Movieplay mp)")
    List<Movieplay> findAllMoviePlaying();

    // load ngày chiếu phim từ id rạp phim và id phim (truyền id rạp phim và id
    // phim)
    @Query("select distinct m.startDay from Movieplay m where m.roomMp.id \r\n"
            + "in(select r.id from Room r inner join Movietheater mt on mt.id = r.movietheaterRoom.id where mt.id = ?1)\r\n"
            + "and m.movie.id = ?2")
    List<String> loadDateFromMtIdAndMId(String mtId, String mId);

    // Load xuất chiếu từ id rạp phim, id phim, ngày (truyền id rạp phim, id phim,
    // ngày)
    @Query("select m.startTime from Movieplay m where m.roomMp.id \r\n"
            + "in(select r.id from Room r inner join Movietheater mt on mt.id = r.movietheaterRoom.id where mt.id = ?1)\r\n"
            + "and m.movie.id = ?2 and m.startDay = ?3")
    List<String> loadTimeFromMtIdAndMIdAndDay(String mtId, String mId, Date day);
    
    @Query("select o.id from Movieplay o where o.movie.id = ?1 "
    		+ "and o.roomMp.id in (select r.id from Room r inner join Movietheater mt on r.movietheaterRoom.id = mt.id where mt.id = ?2)"
    		+ "and o.startDay = ?3 and o.startTime like ?4")
    List<Integer> getMovieplayId(String movieId, String mtId, Date day, String time);
}
