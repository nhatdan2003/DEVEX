package com.Devex.Entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Sellers")
public class Seller extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	 
	
	@Column(name = "Shopname")
	private String shopName;
	@Column(name = "Address")
	private String address;
	@Column(name = "Phoneaddress")
	private String phoneAddress;
	@Column(name = "Mall")
	private Boolean mall;
	@Column(name = "Activeshop")
	private Boolean activeShop;
	
	@JsonIgnore
	@OneToMany(mappedBy = "seller")
	private List<Follow> follows;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sellerProduct")
	private List<Product> products;
	
	@Column(name = "Description")
	private String description;
}
