package edu.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "Movie")
public class Movie implements Serializable{
	@Id
	@NotBlank
	private String id;
	@NotBlank
	private String name;	
	@NotBlank	
	private String description;	
	@NotBlank
	private String poster;	
	@NotBlank
	private String categorys;	
	@NotBlank
	private String actors;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date releaseDay;
	@NotNull
	@Min(0)
	private Double price;
	@NotNull
	@Min(0)
	private Double duration;
	private boolean active;
	
	@JsonIgnore
	@OneToMany(mappedBy = "movie")
	private List<Movieplay> movieplays;
}
