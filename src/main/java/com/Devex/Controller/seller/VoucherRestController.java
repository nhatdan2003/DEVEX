package com.Devex.Controller.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.Entity.User;
import com.Devex.Entity.Voucher;
import com.Devex.Entity.VoucherProduct;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.VoucherProductService;
import com.Devex.Sevice.VoucherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/seller/voucher")
public class VoucherRestController {
	@Autowired
    private SessionService session;
	
	@Autowired
    private VoucherService voucherService;
	
	@Autowired
    private VoucherProductService voucherProductService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Voucher>> getAllVoucher() {
		User user = session.get("user");
		List<Voucher> list = voucherService.findAllByShop(user.getUsername());

		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/prod-voucher/{id}")
	public ResponseEntity<List<VoucherProduct>> getProdVoucher(@PathVariable("id") Integer id) {
		
		List<VoucherProduct> list = voucherProductService.findAllByVoucher(id);

		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	@PutMapping("/disabled/{id}")
	public ResponseEntity<Void> disabledCartDetail(@PathVariable("id") Integer id) {
		Voucher voucher = voucherService.getById(id);

		if (voucher != null) {
			voucherService.disabledVoucher(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
