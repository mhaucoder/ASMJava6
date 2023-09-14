package edu.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "[Role]")
public class Role implements Serializable{
	@Id
	@NotBlank
	private String id;
	@NotBlank
	private String role;	
	private boolean active;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private List<Authority> authorities;
}
