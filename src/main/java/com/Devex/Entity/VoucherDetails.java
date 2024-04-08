package com.Devex.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Voucher_Details")
public class VoucherDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private int id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Customer_ID")
	private Customer customerVoucherDetails;
	
	@ManyToOne
	@JoinColumn(name = "Voucher_ID")
	private Voucher voucher;
	
	@Column(name = "Applied")
	private Boolean applied;

}
