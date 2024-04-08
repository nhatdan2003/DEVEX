package com.Devex.DTO;

import java.sql.Date;
import java.util.List;

import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Comment;
import com.Devex.Entity.ImageProduct;
import com.Devex.Entity.ProductBrand;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.Seller;
import com.Devex.Entity.Voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataDTO {
	private List<CartDetailDTo> itemsOrderSession;
    private List<Voucher> voucherApply;
    private Double total;
}
