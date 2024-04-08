package com.Devex.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {
	String userID;
	String shopName;
	String fullname;
	String address;
	String phoneAddress;
	Boolean mall;
	String description;
	
}
