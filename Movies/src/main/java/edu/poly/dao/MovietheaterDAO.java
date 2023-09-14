package edu.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.entity.Movietheater;

public interface MovietheaterDAO extends JpaRepository<Movietheater, String> {

	List<Movietheater> findAllByActiveEquals(Boolean active);

	// Load rạp phim đang chiếu phim (truyền id phim)
	@Query("select mt.id, mt.name, mt.phone, mt.email, mt.address, mt.description, mt.active from Movietheater mt inner join Room r on mt.id = r.movietheaterRoom.id\r\n"
			+ "where r.id in (select m.roomMp.id from Movieplay m where m.movie.id = ?1)")
	List<Object> findAllByMovie(String movieId);

	// Load tên các rạp phim
	@Query("SELECT DISTINCT mt.name FROM Movietheater mt")
	List<String> findDistinctMovietheater();
}
