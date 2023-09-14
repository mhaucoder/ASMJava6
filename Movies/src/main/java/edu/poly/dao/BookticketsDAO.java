package edu.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.poly.entity.Booktickets;

public interface BookticketsDAO extends JpaRepository<Booktickets, Integer> {
    @Query("SELECT b FROM Booktickets b WHERE b.userBo.id = :userId")
    List<Booktickets> findByUserId(@Param("userId") String userId);
}
