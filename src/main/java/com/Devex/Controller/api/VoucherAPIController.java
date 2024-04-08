package com.Devex.Controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.Entity.Customer;
import com.Devex.Entity.User;
import com.Devex.Entity.Voucher;
import com.Devex.Entity.VoucherDetails;
import com.Devex.Entity.VoucherProduct;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.VoucherDetailService;
import com.Devex.Sevice.VoucherProductService;
import com.Devex.Sevice.VoucherService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/voucher")
public class VoucherAPIController {
	@Autowired
	private SessionService session;

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private VoucherProductService voucherProductService;

	@Autowired
	private VoucherDetailService voucherDetailService;

	@GetMapping("/list")
	public ResponseEntity<List<Voucher>> getAllVoucher() {
		// User user = session.get("user");
		List<Voucher> list = voucherService.findAll();

		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/prod-voucher/{id}") // sản phẩm áp dụng voucher
	public ResponseEntity<List<VoucherProduct>> getProdVoucher(@PathVariable("id") Integer id) {

		List<VoucherProduct> list = voucherProductService.findAllByVoucher(id);

		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/prod-voucher/all")
	public ResponseEntity<List<VoucherProduct>> getProdVoucherAll() {

		List<VoucherProduct> list = voucherProductService.findAll();

		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/saved/list")
	public ResponseEntity<List<VoucherDetails>> getAllVoucherOfUser() {
		User user = session.get("user");
		if (user != null) {
			List<VoucherDetails> list = voucherDetailService.findAllByUsername(user.getUsername());
			if (list != null) {
				return ResponseEntity.ok(list);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/my-saved")
	public ResponseEntity<List<Voucher>> getMyVoucher() {
		User user = session.get("user");
		if (user != null) {
			List<Voucher> list = voucherService.findVoucherOfUser(user.getUsername());
			if (list != null) {
				return ResponseEntity.ok(list);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping("/save")
	public ResponseEntity<Void> saveVoucher(@RequestBody Voucher voucher) {
		User user = session.get("user");
		Customer customer = customerService.findById(user.getUsername()).get();
		VoucherDetails voucherSaved = new VoucherDetails();
		voucherSaved.setVoucher(voucher);
		voucherSaved.setCustomerVoucherDetails(customer);
		voucherSaved.setApplied(true);

		try {
			voucherDetailService.save(voucherSaved);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
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
