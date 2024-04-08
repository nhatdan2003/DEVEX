package com.Devex.Entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product_Variant")
public class ProductVariant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private int id;
	@Column(name = "Quantity")
	private int quantity;
	@Column(name = "Price")
	private Double price;
	@Column(name = "Pricesale")
	private Double priceSale;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Product_ID")
	private Product product;
	
	private String size;
	
	private String color;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productVariant")
	private List<OrderDetails> orderDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productVariant")
	private List<FlashSale> listFlashSale;

}
