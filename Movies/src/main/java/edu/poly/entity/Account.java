package edu.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "[User]")
public class Account implements Serializable {
	@Id
	@NotBlank
	private String id;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	private String phone;
	@NotNull
	@Min(15)
	@Max(100)
	private int age;
	private Boolean active = true;
	@NotBlank
	private String email;
	@JsonIgnore
	@OneToMany(mappedBy = "userAu", fetch = FetchType.EAGER)
	private List<Authority> authorities;

	@JsonIgnore
	@OneToMany(mappedBy = "userBo")
	private List<Booktickets> booktickets;
}
