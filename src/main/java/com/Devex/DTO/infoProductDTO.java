package com.Devex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class infoProductDTO {
	
	private String id;
	private String name;
	private Boolean active;
	private Integer soldCount;
	private int quantity;
	private Double price;
	private Double priceSale;
	private String nameImageProduct;
	private int cateId;
	
}
