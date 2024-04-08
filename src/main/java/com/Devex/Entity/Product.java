package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", updatable = false)
	private String id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Description")
	private String description;
	@Column(name = "Createdday")
	private Date createdDay;
	@Column(name = "Active")
	private Boolean active;
	@Column(name = "Isdelete")
	private Boolean isdelete;
	@Column(name = "Viewcount")
	private Long viewcount;

	@Formula("(SELECT COUNT(od.ID) FROM Order_Details od INNER JOIN Product_Variant pv ON od.Product_ID = pv.ID INNER JOIN Orders o ON o.ID = od.Order_ID WHERE pv.Product_ID = ID AND o.Status_ID = 1006)")
    private Integer soldCount;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "Shop_ID")
	private Seller sellerProduct;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "Category_ID")
	private CategoryDetails categoryDetails;

	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "Brand_ID")
	private ProductBrand productbrand;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<ImageProduct> imageProducts;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<ProductVariant> productVariants;

	@JsonIgnore
	@OneToMany(mappedBy = "productComment")
	private List<Comment> comments;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
