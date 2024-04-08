package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comment")
public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private int id;
	@Column(name = "Is_Seller_Reply")
	private Boolean isSellerReply;
	@Column(name = "Content")
	private String content;
	@Column(name = "Rating")
	private int rating;
	@Column(name = "Created_at")
	private Date createdAt = new Date();
	@Column(name = "Active")
	private Boolean isActive = true;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Product_ID")
	private Product productComment;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "User_ID")
	private User user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Order_Detail_ID")
	private OrderDetails orderDetails;



}
