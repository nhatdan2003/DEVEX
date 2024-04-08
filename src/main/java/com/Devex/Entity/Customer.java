package com.Devex.Entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers")

public class Customer extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Column(name = "Address")
	private String address;
	@Column(name = "Phoneaddress")
	private String phoneAddress;
	

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<Follow> follow;
	

	
	@JsonIgnore
	@OneToMany(mappedBy = "customerVoucherDetails")
	private List<VoucherDetails> voucherDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customerOrder")
	private List<Order> orders;
	
//	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
//    private Cart cart;

}
