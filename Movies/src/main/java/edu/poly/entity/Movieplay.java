package edu.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "Movieplay")
public class Movieplay implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date startDay;
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	@Temporal(TemporalType.TIME)
	private String startTime;
	
	@ManyToOne
	@JoinColumn(name = "roomId")
	private Room roomMp;

	@ManyToOne
	@JoinColumn(name = "mId")
	private Movie movie;

	@JsonIgnore
	@OneToMany(mappedBy = "movieplayBook")
	private List<Booktickets> booktickets;

}
