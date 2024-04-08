package com.Devex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalRevenueMonthDTO {

	private int day;
	private double price;
	private double priceCompare;
	
}
