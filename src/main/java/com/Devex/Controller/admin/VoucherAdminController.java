package com.Devex.Controller.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.CategoryVoucher;
import com.Devex.Entity.User;
import com.Devex.Entity.Voucher;
import com.Devex.Sevice.CategoryVoucherService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;
import com.Devex.Sevice.VoucherProductService;
import com.Devex.Sevice.VoucherService;

@Controller
@RequestMapping("/ad")
public class VoucherAdminController {
	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	SessionService sessionService;

	@Autowired
	ParamService param;

	@Autowired
	CategoryVoucherService categoryVoucherService;

	@Autowired
	VoucherService voucherService;

	@Autowired
	VoucherProductService voucherProductService;

	@Autowired
	SellerService sellerService;

	@GetMapping("/voucher/form")
	public String showFormVoucher(Model model) {

		return "admin/voucherManage/formVoucher";
	}

	@GetMapping("/voucher/formShip")
	public String showFormVoucherShip(Model model) {

		return "admin/voucherManage/formVoucherShip";
	}

	@GetMapping("/voucher/manage")
	public String showManageVoucher(Model model) {

		return "admin/voucherManage/manageVoucher";
	}

	@PostMapping("/voucher/create")
	public String createVoucher(Model model) {
		User user = sessionService.get("user");
		Voucher voucher = new Voucher();

		String name = param.getString("name", "Dùng thử");
		String code = param.getString("code", "");
		Integer qty = param.getInteger("quantity", 1);
		String time = param.getString("time", null);
		String minPrice = param.getString("minPriceSale", "1000");
		String rangePrice = param.getString("rangePriceSale", "");
		String description = param.getString("description", "");
		String type = param.getString("type", "devex");

		voucher.setName(name);
		voucher.setCode(code);
		voucher.setQuantity(qty);
		voucher.setCreatedDay(new Date());
		voucher.setDescription(description);
		voucher.setActive(true);
		voucher.setCreator(user);
		voucher.setBanner("abc.webp");
		voucher.setMinPrice(Double.parseDouble(minPrice));

		// Sử lí loại giảm giá
		if (type.equals("devex")) {
			CategoryVoucher categoryVoucher = categoryVoucherService.findById(100001).get();
			voucher.setCategoryVoucher(categoryVoucher);
		} else if (type.equals("ship")) {
			CategoryVoucher categoryVoucher = categoryVoucherService.findById(100002).get();
			voucher.setCategoryVoucher(categoryVoucher);
		}

		// Sử lí mức giảm của voucher
		if (rangePrice.length() <= 2) {
			Double range = Double.parseDouble(rangePrice);
			range = range / 100;
			voucher.setDiscount(range);
		} else {
			Double range = Double.parseDouble(rangePrice);
			voucher.setDiscount(range);
		}

		// Sử lí thời gian áp dụng của voucher
		String[] parts = time.split(" - ");
		String startTime = parts[0];
		String endTime = parts[1];

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		try {
			Date startDate = sdf.parse(startTime);
			Date endDate = sdf.parse(endTime);
			System.out.println("Start time: " + startDate);
			System.out.println("End time: " + endDate);
			// Chuyển đổi Date thành java.sql.Timestamp trước khi lưu vào cơ sở dữ liệu
			Timestamp sqlTimestampStart = new Timestamp(startDate.getTime());
			Timestamp sqlTimestampEnd = new Timestamp(endDate.getTime());
			voucher.setEndDate(sqlTimestampEnd);
			voucher.setStartDate(sqlTimestampStart);

		} catch (Exception e) {
			e.printStackTrace();
		}

		voucherService.save(voucher);
		// model.addAttribute("listProd", listProd);

		return "redirect:/ad/voucher/manage";
	}
}
