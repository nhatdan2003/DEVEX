package com.Devex.Controller.seller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.CategoryVoucher;
import com.Devex.Entity.Product;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Entity.Voucher;
import com.Devex.Entity.VoucherProduct;
import com.Devex.Sevice.CategoryVoucherService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;
import com.Devex.Sevice.VoucherProductService;
import com.Devex.Sevice.VoucherService;

@Controller
@RequestMapping("/seller")
public class VoucherController {
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ParamService param;

	@Autowired
	private CategoryVoucherService categoryVoucherService;

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private VoucherProductService voucherProductService;

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private NotiService notiService;

	@GetMapping("/voucher/form")
	public String showFormVoucher(Model model) {
		User user = sessionService.get("user");
		List<Product> listProd = productService.findProductBySellerUsernameAndIsdeleteProduct(user.getUsername());
//		Seller shop = sellerService.findById(user.getUsername()).get();
		String code = user.getUsername().substring(0, 5).toUpperCase();
		System.out.println(code);
		System.out.println(listProd.get(0).getName());

		model.addAttribute("listProd", listProd);
		model.addAttribute("code", code);

		return "seller/voucher/formVoucher";
	}
	
	@GetMapping("/voucher/manage")
	public String showManageVoucher(Model model) {
//		User user = sessionService.get("user");
//		List<Product> listProd = productService.findProductBySellerUsernameAndIsdeleteProduct(user.getUsername());
////		Seller shop = sellerService.findById(user.getUsername()).get();
//		String code = user.getUsername().substring(0, 4).toUpperCase();
//		System.out.println(code);
//		System.out.println(listProd.get(0).getName());
//
//		model.addAttribute("listProd", listProd);
//		model.addAttribute("code", code);

		return "seller/voucher/manageVoucher";
	}

	@PostMapping("/voucher/create")
	public String createVoucher(Model model, @RequestParam("products") List<String> selectedProducts) {
		User user = sessionService.get("user");
		List<Product> listProd = productService.findProductBySellerUsernameAndIsdeleteProduct(user.getUsername());
		Seller shop = sellerService.findById(user.getUsername()).get();
		Voucher voucher = new Voucher();

		String name = param.getString("name", "Dùng thử");
		String code = param.getString("code", "");
		Integer qty = param.getInteger("quantity", 1);
		String time = param.getString("time", null);
		String minPrice = param.getString("minPriceSale", "1000");
		String rangePrice = param.getString("rangePriceSale", "");
		String description = param.getString("description", "");

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
		if (listProd.size() == selectedProducts.size()) {
			CategoryVoucher categoryVoucher = categoryVoucherService.findById(100003).get();
			voucher.setCategoryVoucher(categoryVoucher);
		} else {
			CategoryVoucher categoryVoucher = categoryVoucherService.findById(100004).get();
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
			// Chuyển đổi Date thành java.sql.Timestamp trước khi lưu vào cơ sở dữ liệu
			Timestamp sqlTimestampStart = new Timestamp(startDate.getTime());
			Timestamp sqlTimestampEnd = new Timestamp(endDate.getTime());
			voucher.setEndDate(sqlTimestampEnd);
			voucher.setStartDate(sqlTimestampStart);

		} catch (Exception e) {
			e.printStackTrace();
		}

		voucherService.save(voucher);
		// Sử lí sản phẩm được áp voucher
		for (String str : selectedProducts) {
			VoucherProduct voucherProduct = new VoucherProduct();
			Product prod = productService.findByIdProduct(str);
			voucherProduct.setProduct(prod);
			voucherProduct.setVoucher(voucher);
			voucherProductService.save(voucherProduct);
		}

//		model.addAttribute("listProd", listProd);
		Voucher v = voucherService.getVoucherNew();
		notiService.sendHistory(user.getUsername(), "", "/seller/voucher/manage", "createvoucher", String.valueOf(v.getId()));
		return "redirect:/seller/voucher/form";
	}
}
