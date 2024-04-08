package com.Devex.DTO;

import java.sql.Date;
import java.util.List;

import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Comment;
import com.Devex.Entity.ImageProduct;
import com.Devex.Entity.ProductBrand;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.Seller;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductDTO {
	 	private String id;
	    private String name;
	    private String description;
	    private Boolean active;
	    private Boolean isdelete;
	    private Integer soldCount;
	    private Seller sellerProduct; // Thêm thuộc tính này
	    private CategoryDetails categoryDetails; // Thêm thuộc tính này
	    private ProductBrand productbrand;// Brand
	    private List<ImageProduct> imageProducts; // Thêm thuộc tính này
		private List<ProductVariant> productVariants;
	    private List<Comment> comments; // Thêm thuộc tính này
}
