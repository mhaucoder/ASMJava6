package edu.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Booktickets")
public class Booktickets implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	private Integer total;	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dayBook;	
	
	@NotNull
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private Account userBo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "bookticket")
	private List<Bookticketsdetail> bookticketsdetails;
	
	@ManyToOne
	@JoinColumn(name = "mpId")
	private Movieplay movieplayBook;
}
