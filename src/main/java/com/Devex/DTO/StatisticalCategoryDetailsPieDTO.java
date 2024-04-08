package com.Devex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalCategoryDetailsPieDTO {
	
	private int id;
	private String name;
	private long countProductSell;

}
