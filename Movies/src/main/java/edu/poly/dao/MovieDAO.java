package edu.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.poly.entity.Movie;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MovieDAO extends JpaRepository<Movie, String> {
    List<Movie> findAllByActiveEquals(Boolean active);

    @Query("SELECT DISTINCT m.categorys FROM Movie m")
    List<String> findDistinctCategorysMovie();

    // phim sắp chiếu
    List<Movie> findAllByReleaseDayGreaterThanEqual(Date date);

    // Năm phát hành
    @Query("SELECT DISTINCT YEAR(m.releaseDay) FROM Movie m")
    List<Integer> findDistinctReleaseYears();

    List<Movie> findByNameContainingIgnoreCase(String name);

    List<Movie> findByCategorysContainingIgnoreCase(String categoryName);
}
