package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Vouchers")
public class Voucher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private int id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Discount")
	private Double discount;
	@Column(name = "Description")
	private String description;
	@Column(name = "Createddate")
	private Date createdDay;
	@Column(name = "Startdate")
	private Date startDate;
	@Column(name = "Enddate")
	private Date endDate;
	@Column(name = "Code")
	private String code;
	@Column(name = "Active")
	private Boolean active;
	@Column(name = "Banner")
	private String banner;
	@Column(name = "Quantity")
	private Integer quantity;
	@Column(name = "Minprice")
	private Double minPrice;
	
	@JsonIgnore
	@OneToMany(mappedBy = "voucher")
	private List<VoucherDetails> voucherDetails;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Category_Voucher_ID")
	private CategoryVoucher categoryVoucher;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Creator")
	private User creator;
	
	@JsonIgnore
	@OneToMany(mappedBy = "voucher")
	private List<OrderDiscount> listOrderDiscount;
	
	@JsonIgnore
	@OneToMany(mappedBy = "voucher")
	private List<VoucherProduct> listVoucherProduct;
	
}
	