package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Username", updatable = false)
	private String username;
	@Column(name = "Fullname")
	private String fullname;
	@Column(name = "Email")
	private String email;
	@Column(name = "Password")
	private String password;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "Avatar")
	private String avatar;
	@Column(name = "Gender")
	private String gender;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdday")
	private Date createDay;
	@Column(name = "Active")
	private Boolean active;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<UserRole> roles;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<History> listHistory;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Dwallet dwallet;
}
