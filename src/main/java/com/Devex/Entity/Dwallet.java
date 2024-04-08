package com.Devex.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Dwallet")
public class Dwallet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", updatable = false)
	private String id;
	@Column(name = "Balance")
	private Double balance;
	@Column(name = "Active")
	private Boolean active;
	
	@OneToOne
    @JoinColumn(name = "Username")
	private User user;

}
