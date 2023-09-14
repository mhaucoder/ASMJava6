package edu.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "Movietheater")
public class Movietheater implements Serializable{
	@Id
	@NotBlank
	private String id;
	@NotBlank
	private String name;
	@NotBlank	
	private String phone;
	@NotBlank	
	@Email
	private String email;
	@NotBlank	
	private String address;
	@NotBlank		
	private String description;
	private boolean active;
	
	@JsonIgnore
	@OneToMany(mappedBy = "movietheaterRoom")
	private List<Room> rooms;
}
