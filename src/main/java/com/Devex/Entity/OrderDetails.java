package com.Devex.Entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Order_Details")
public class OrderDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", updatable = false)
	private String id;
	@Column(name = "Quantity")
	private int quantity;
	@Column(name = "Price")
	private Double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Status_ID")
	private OrderStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Order_ID")
	private Order order;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Product_ID")
	private ProductVariant productVariant;

	@JsonIgnore
	@OneToMany(mappedBy = "orderDetails")
	private List<Comment> comments;


	@Transient
	private Boolean isComment;
}
