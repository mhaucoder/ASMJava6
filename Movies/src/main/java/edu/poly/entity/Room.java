package edu.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Room")
public class Room implements Serializable{
	@Id
	@NotBlank
	private String id;
	@NotBlank
	private String name;
	@NotNull
	@Min(10)
	@Max(50)
	private int soGhe;
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "mtId")
	private Movietheater movietheaterRoom;
	
	@JsonIgnore
	@OneToMany(mappedBy = "roomMp")
	private List<Movieplay> movieplays;
}
