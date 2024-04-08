package com.Devex.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyBillDTO {
	private String shopName;
	private Date createdDay;
	private String orderID;
	private String avt;
}
