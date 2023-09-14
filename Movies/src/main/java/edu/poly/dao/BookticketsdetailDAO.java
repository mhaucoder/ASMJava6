package edu.poly.dao;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.entity.Bookticketsdetail;

public interface BookticketsdetailDAO extends JpaRepository<Bookticketsdetail, Integer>{
	//lấy ghế đã book (truyền vào ngày, id phim, id rạp, giờ)
	@Query("select btdt.soGhe from Booktickets bt \r\n"
			+ "inner join Movieplay mp on bt.movieplayBook.id = mp.id \r\n"
			+ "inner join Bookticketsdetail btdt on bt.id = btdt.bookticket.id\r\n"
			+ "inner join Room r on mp.roomMp.id = r.id\r\n"
			+ "where startDay = ?1 and movie.id = ?2 \r\n"
			+ "and movietheaterRoom.id = ?3 and startTime like ?4")
	List<Integer> getChartBooked(Date day, String movieId, String mtId, String time);
}
