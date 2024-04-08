package com.Devex.Controller.seller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Devex.Sevice.ServiceImpl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Devex.DTO.DateDTO;
import com.Devex.DTO.FlashSaleDTO;
import com.Devex.DTO.ProductDTO;
import com.Devex.DTO.ShopDTO;
import com.Devex.DTO.StatisticalOrderMonthPieDTO;
import com.Devex.DTO.StatisticalRevenueMonthDTO;
import com.Devex.DTO.infoProductDTO;
import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Dwallet;
import com.Devex.Entity.FlashSale;
import com.Devex.Entity.FlashSaleTime;
import com.Devex.Entity.History;
import com.Devex.Entity.ImageProduct;
import com.Devex.Entity.Notifications;
import com.Devex.Entity.Order;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductBrand;
import com.Devex.Entity.Request;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.Seller;
import com.Devex.Entity.TransactionHistory;
import com.Devex.Entity.User;
import com.Devex.Sevice.CartService;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.DwalletService;
import com.Devex.Sevice.FileManagerService;
import com.Devex.Sevice.FlashSalesService;
import com.Devex.Sevice.FlashSalesTimeService;
import com.Devex.Sevice.FollowService;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ProductBrandService;
import com.Devex.Sevice.RequestService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.TransactionHistoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.server.PathParam;

@CrossOrigin("*")
@RestController
public class DevexSellerRestController {

	@Autowired
	private FileManagerService fileManagerService;

	@Autowired
	private SessionService session;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryDetailService categoryDetailService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ImageProductService imageProductService;

	@Autowired
	private ProductBrandService brandService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private FollowService followService;

	@Autowired
	private OrderDetailService detailService;

	@Autowired
	private FlashSalesTimeService flashSalesTimeService;

	@Autowired
	private FlashSalesService flashSalesService;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	private NotiService notiService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private DwalletService dwalletService;

	@Autowired
	private TransactionHistoryService transactionHistoryService;
	
	@Autowired
	private CartService cartService;

	@Value("${myapp.file-storage-path}")
	private String fileStoragePath;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//	@GetMapping("/img/product")
//	public List<String> listImage() {
//		// Kích hoạt FileManagerService
//		User u = session.get("user");
//		String id = session.get("idproduct");
//		List<String> imageUrls = fileManagerService.list(id, u.getUsername());
//
//		return imageUrls;
//	}

	@GetMapping("/img/product/{filename}")
	public byte[] download(@PathVariable("filename") String filename) {
		String username = session.get("usernameseller");
		String idproduct = session.get("idproduct");
		return fileManagerService.read(username, idproduct, filename);
	}

	@DeleteMapping("/img/product/{filename}")
	public void delete(@PathVariable("filename") String filename) {
		String username = session.get("usernameseller");
		String idproduct = session.get("idproduct");
		fileManagerService.delete(username, idproduct, filename);
		imageProductService.deleteImageProductByNameAndProductId(filename, idproduct);
	}

	@PostMapping("/img/product")
	public List<String> upload(@PathParam("files") MultipartFile[] files) {
		String username = session.get("usernameseller");
		String idproduct = session.get("idproduct");
		return fileManagerService.save(username, idproduct, files);
	}

	@GetMapping("/api/product")
	public Map<String, Object> getProduct() {
		Map<String, Object> mapInfoProduct = new HashMap<>();
		String id = session.get("idproduct");
		boolean checkRequest = false;
		Product product = productService.findByIdProduct(id);
		Request productRequest = requestService.findRequestByEntityId(id);
		mapInfoProduct.put("product", product);
		if (productRequest != null) {
			checkRequest = true; 
		}
		mapInfoProduct.put("checkRequest", checkRequest);
		return mapInfoProduct;
	}

	@GetMapping("/categoryDetails/{idca}")
	public List<CategoryDetails> listCategoryDetails(@PathVariable("idca") int idca) {
		// Kích hoạt FileManagerService
		int id = idca;
		List<CategoryDetails> listcategory = categoryDetailService.findAllCategoryDetailsById(id);
		return listcategory;
	}

	@GetMapping("/idCategoryDetails")
	public int idCategorydetails() {
		String idp = session.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getCategoryDetails().getId();
		return id;
	}

	@GetMapping("/idcategory")
	public int idCategory() {
		String idp = session.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getCategoryDetails().getCategory().getId();
		return id;
	}

	@GetMapping("/category")
	public List<Category> idCategoryDetails() {
		List<Category> listCategory = categoryService.findAll();
		return listCategory;
	}

	@GetMapping("/brand")
	public List<ProductBrand> listBrand() {
		return brandService.findAll();
	}

	@GetMapping("/idbrand")
	public int idBrand() {
		String idp = session.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getProductbrand().getId();
		return id;
	}

	@GetMapping("/api/productvariant")
	public List<ProductVariant> getProductVariant() {
		String idp = session.get("idproduct");
		List<ProductVariant> listproductvariant = productVariantService.findAllProductVariantByProductId(idp);
		return listproductvariant;
	}

	@SuppressWarnings("unused")
	@PutMapping("/info/product")
	public Map<String, Object> updateProduct(@RequestBody Object object) throws ParseException {
		User u = session.get("user");
		boolean checkInsert = false;
		// Chuyển object sang json sau đó đọc ra
		JsonNode jsonNode = objectMapper.valueToTree(object);
		JsonNode listNode = jsonNode.get("listvariant");
		List<ProductVariant> myList = objectMapper.convertValue(listNode, new TypeReference<List<ProductVariant>>() {
		});
		int idCategoryDetails = jsonNode.get("idCategoryDetails").asInt();
		Boolean active = jsonNode.get("active").asBoolean();
		int brand = jsonNode.get("brand").asInt();
		String createdDay = jsonNode.get("createdDay").asText();
		Date daycreated = dateFormat.parse(createdDay);
		String description = jsonNode.get("description").asText();
		String id = jsonNode.get("id").asText();
		String name = jsonNode.get("name").asText();
		CategoryDetails categoryDetails = categoryDetailService.findCategoryDetailsById(idCategoryDetails);
		Seller seller = sellerService.findById(u.getUsername()).get();
		Product p = productService.findByIdProduct(id);
		if (p.getActive() == false && p.getDescription() == null && p.getIsdelete() == false
				&& p.getName() == "Nhập tên sản phẩm tại đây") {
			notiService.sendHistory(u.getUsername(), "", "/seller/product/edit/" + id, "newproduct", id);
		} else {
			notiService.sendHistory(u.getUsername(), "", "/seller/product/edit/" + id, "updateproduct", id);
		}
		// Update product
		productService.updateProduct(id, name, brand, description, daycreated, active, seller.getUsername(),
				categoryDetails.getId());
		// Duyệt list productVariant để thực hiện insert hoặc update
		for (ProductVariant productVariant : myList) {
			Integer idProductVariant = productVariant.getId();
			if (idProductVariant >= 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.updateProductVariant(idProductVariant, productVariant.getQuantity(),
						productVariant.getPrice(), productVariant.getPriceSale(), productVariant.getSize(),
						productVariant.getColor());
			} else if (idProductVariant < 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.addProductVariant(productVariant.getQuantity(), productVariant.getPrice(),
						productVariant.getPriceSale(), productVariant.getSize(), productVariant.getColor(), id);
			}
		}
		Map<String, Object> mapInfoProduct = new HashMap<>();
		String idp = session.get("idproduct");
		boolean checkRequest = false;
		Product product = productService.findByIdProduct(idp);
		Request productRequest = requestService.findRequestByEntityId(idp);
		mapInfoProduct.put("product", product);
		if (productRequest != null) {
			checkRequest = true;
		}
		mapInfoProduct.put("checkRequest", checkRequest);
		return mapInfoProduct;
	}

	@DeleteMapping("/delete/product/{idproduct}")
	public void deleteproduct(@PathVariable("idproduct") String idproduct) {
		User u = session.get("user");
		productService.updateProductIsDeleteById(true, idproduct);
		productService.updateProductActiveById(false, idproduct);
		notiService.sendHistory(u.getUsername(), "", "", "deleteproduct", idproduct);
	}

	@PostMapping("/addimageproduct/{id}")
	public void insertImageProduct(@PathVariable("id") String id) {
		User u = session.get("user");
		imageProductService.insertImageProduct("1", "default.webp", id);
		fileManagerService.changeImage(u.getUsername(), id);
	}

	@DeleteMapping("productvariant/delete/{id}")
	public void deleteProductVariant(@PathVariable("id") Integer id) {
		productVariantService.deleteById(id);
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/seller/revenue/gettotalprice")
	public Map<String, Object> getrevenueSeller() {
		User u = session.get("user");
		Map<String, Object> mapStatistical = new HashMap<>();
		int amountOrder = orderService.getCountOrderForSeller(u.getUsername());
		int amountFollow = followService.getCountFollowBySellerUsername(u.getUsername());
		int amountProduct = productService.getCountProductBySellerUsername(u.getUsername());
		Double totalRevenue = orderService.getTotalOrderValueForSeller(u.getUsername());
		mapStatistical.put("amountOrder", amountOrder);
		mapStatistical.put("totalRevenue", totalRevenue);
		mapStatistical.put("amountFollow", amountFollow);
		mapStatistical.put("amountProduct", amountProduct);
		return mapStatistical;
	}

	@GetMapping("/seller/revenue/month")
	public List<StatisticalRevenueMonthDTO> getRevenueByMonth(@RequestParam("year") int year,
			@RequestParam("month") int month) {
		User u = session.get("user");
		int yearCompare;
		int monthCompare;
		if (month == 1) {
			yearCompare = year - 1;
			monthCompare = 12;
		} else {
			yearCompare = year;
			monthCompare = month - 1;
		}
		List<Object[]> listt = detailService.getTotalPriceByMonthAndSellerUsername(year, month, u.getUsername());
		List<Object[]> listtc = detailService.getTotalPriceByMonthAndSellerUsername(yearCompare, monthCompare,
				u.getUsername());
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		LocalDate date = LocalDate.of(year, month, 1);
		LocalDate dateCompare = LocalDate.of(yearCompare, monthCompare, 1);
		int lengthOfMonth = date.lengthOfMonth();
		int lengthOfMonthCompare = dateCompare.lengthOfMonth();
		if (lengthOfMonth > lengthOfMonthCompare) {
			for (int i = 1; i <= lengthOfMonth; i++) {
				double price = 0;
				double priceCompare = 0;
				for (Object[] ob : listt) {
					int day = (ob[0] == null) ? 0 : (int) ob[0];
					if (day == i) {
						price = (ob[1] == null) ? 0 : (double) ob[1];
						break;
					}
				}
				for (Object[] obc : listtc) {
					int day = (obc[0] == null) ? 0 : (int) obc[0];
					if (day == i) {
						priceCompare = (obc[1] == null) ? 0 : (double) obc[1];
						break;
					}
				}
				StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
				statistical.setDay(i);
				statistical.setPrice(price);
				statistical.setPriceCompare(priceCompare);
				liststatis.add(statistical);
			}
		} else {
			for (int i = 1; i <= lengthOfMonthCompare; i++) {
				double price = 0;
				double priceCompare = 0;
				for (Object[] ob : listt) {
					int day = (ob[0] == null) ? 0 : (int) ob[0];
					if (day == i) {
						price = (ob[1] == null) ? 0 : (double) ob[1];
						break;
					}
				}
				for (Object[] obc : listtc) {
					int day = (obc[0] == null) ? 0 : (int) obc[0];
					if (day == i) {
						priceCompare = (obc[1] == null) ? 0 : (double) obc[1];
						break;
					}
				}
				StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
				statistical.setDay(i);
				statistical.setPrice(price);
				statistical.setPriceCompare(priceCompare);
				liststatis.add(statistical);
			}
		}
		return liststatis;
	}

	@GetMapping("/seller/revenue/year")
	public List<StatisticalRevenueMonthDTO> getRevenueByYear(@RequestParam("year") int year) {
		User u = session.get("user");
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		List<Object[]> listt = detailService.getTotalPriceByYearAndSellerUsername(year, u.getUsername());
		List<Object[]> listtc = detailService.getTotalPriceByYearAndSellerUsername(year - 1, u.getUsername());
		for (int i = 1; i <= 12; i++) {
			double price = 0;
			double priceCompare = 0;
			for (Object[] ob : listt) {
				int day = (ob[0] == null) ? 0 : (int) ob[0];
				if (day == i) {
					price = (ob[1] == null) ? 0 : (double) ob[1];
					break;
				}
			}
			for (Object[] obc : listtc) {
				int day = (obc[0] == null) ? 0 : (int) obc[0];
				if (day == i) {
					priceCompare = (obc[1] == null) ? 0 : (double) obc[1];
					break;
				}
			}
			StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
			statistical.setDay(i);
			statistical.setPrice(price);
			statistical.setPriceCompare(priceCompare);
			liststatis.add(statistical);
		}
		return liststatis;
	}

	@GetMapping("/seller/month/pie")
	public List<StatisticalOrderMonthPieDTO> getStatisOrder(@RequestParam("year") int year,
			@RequestParam("month") int month) {
		List<StatisticalOrderMonthPieDTO> listStatisOrderPie = new ArrayList<>();
		User u = session.get("user");
		int choxacnhan = orderService.getTotalOrderByStatusIdAndSellerUsername(1001, 1001, u.getUsername(), year,
				month);
		StatisticalOrderMonthPieDTO choxacnhane = new StatisticalOrderMonthPieDTO();
		choxacnhane.setName("Chờ xác nhận");
		choxacnhane.setTotalOrderStatus(choxacnhan);
		listStatisOrderPie.add(choxacnhane);
		int daxacnhan = orderService.getTotalOrderFalseAndConfirmByStatusIdAndSellerUsername(1009, u.getUsername(),
				year, month);
		StatisticalOrderMonthPieDTO daxacnhane = new StatisticalOrderMonthPieDTO();
		daxacnhane.setName("Đã xác nhận");
		daxacnhane.setTotalOrderStatus(daxacnhan);
		listStatisOrderPie.add(daxacnhane);
		int dangvanchuyen = orderService.getTotalOrderByStatusIdAndSellerUsername(1003, 1009, u.getUsername(), year,
				month);
		StatisticalOrderMonthPieDTO dangvanchuyene = new StatisticalOrderMonthPieDTO();
		dangvanchuyene.setName("Đang vận chuyển");
		dangvanchuyene.setTotalOrderStatus(dangvanchuyen);
		listStatisOrderPie.add(dangvanchuyene);
		int danggiao = orderService.getTotalOrderByStatusIdAndSellerUsername(1004, 1009, u.getUsername(), year, month);
		StatisticalOrderMonthPieDTO danggiaoe = new StatisticalOrderMonthPieDTO();
		danggiaoe.setName("Đang giao");
		danggiaoe.setTotalOrderStatus(danggiao);
		listStatisOrderPie.add(danggiaoe);
		int danhanhang = orderService.getTotalOrderByStatusIdAndSellerUsername(1005, 1009, u.getUsername(), year,
				month);
		StatisticalOrderMonthPieDTO danhanhange = new StatisticalOrderMonthPieDTO();
		danhanhange.setName("Đã nhận hàng");
		danhanhange.setTotalOrderStatus(danhanhang);
		listStatisOrderPie.add(danhanhange);
		int chothanhtoan = orderService.getTotalOrderByStatusIdAndSellerUsername(1002, 1009, u.getUsername(), year,
				month);
		StatisticalOrderMonthPieDTO chothanhtoane = new StatisticalOrderMonthPieDTO();
		chothanhtoane.setName("Chờ thanh toán");
		chothanhtoane.setTotalOrderStatus(chothanhtoan);
		listStatisOrderPie.add(chothanhtoane);
		int dahoanthanh = orderService.getTotalOrderByStatusIdAndSellerUsername(1006, 1009, u.getUsername(), year,
				month);
		StatisticalOrderMonthPieDTO dahoanthanhe = new StatisticalOrderMonthPieDTO();
		dahoanthanhe.setName("Đã hoàn thành");
		dahoanthanhe.setTotalOrderStatus(dahoanthanh);
		listStatisOrderPie.add(dahoanthanhe);
		int dahuy = orderService.getTotalOrderFalseAndConfirmByStatusIdAndSellerUsername(1007, u.getUsername(), year,
				month);
		StatisticalOrderMonthPieDTO dahuye = new StatisticalOrderMonthPieDTO();
		dahuye.setName("Đã hủy");
		dahuye.setTotalOrderStatus(dahuy);
		listStatisOrderPie.add(dahuye);
		int trahang = orderService.getTotalOrderFalseAndConfirmByStatusIdAndSellerUsername(1008, u.getUsername(), year,
				month);
		StatisticalOrderMonthPieDTO trahange = new StatisticalOrderMonthPieDTO();
		trahange.setName("Trả hàng");
		trahange.setTotalOrderStatus(trahang);
		listStatisOrderPie.add(trahange);
		return listStatisOrderPie;
	}

	@GetMapping("/rest/list/order")
	public List<Order> getOrderByStatusIdAndUsernameAndYearAndMonth(@Param("year") int year, @Param("month") int month,
			@Param("trangthai") String trangthai) {
		User u = session.get("user");
		List<Order> listOrder = new ArrayList<>();
		if (trangthai.equals("choxacnhan")) {
			listOrder = orderService.getAllOrderByUsernameAndStatusIdAndYearAndMonth(1001, 1001, u.getUsername(), year,
					month);
		} else if (trangthai.equals("daxacnhan")) {
			listOrder = orderService.getAllOrderFalseByUsernameAndStatusIdAndYearAndMonth(1009, u.getUsername(), year,
					month);
		} else if (trangthai.equals("dangvanchuyen")) {
			listOrder = orderService.getAllOrderByUsernameAndStatusIdAndYearAndMonth(1003, 1009, u.getUsername(), year,
					month);
		} else if (trangthai.equals("danggiao")) {
			listOrder = orderService.getAllOrderByUsernameAndStatusIdAndYearAndMonth(1004, 1009, u.getUsername(), year,
					month);
		} else if (trangthai.equals("danhanhang")) {
			listOrder = orderService.getAllOrderByUsernameAndStatusIdAndYearAndMonth(1005, 1009, u.getUsername(), year,
					month);
		} else if (trangthai.equals("chothanhtoan")) {
			listOrder = orderService.getAllOrderByUsernameAndStatusIdAndYearAndMonth(1002, 1009, u.getUsername(), year,
					month);
		} else if (trangthai.equals("dahoanthanh")) {
			listOrder = orderService.getAllOrderByUsernameAndStatusIdAndYearAndMonth(1006, 1009, u.getUsername(), year,
					month);
		} else if (trangthai.equals("dahuy")) {
			listOrder = orderService.getAllOrderFalseByUsernameAndStatusIdAndYearAndMonth(1007, u.getUsername(), year,
					month);
		} else if (trangthai.equals("trahang")) {
			listOrder = orderService.getAllOrderFalseByUsernameAndStatusIdAndYearAndMonth(1008, u.getUsername(), year,
					month);
		}
		return listOrder;
	}

	@PostMapping("/api/flashTime")
	public List<FlashSaleTime> getTime(@RequestBody DateDTO dateDTO) {
		List<FlashSaleTime> list = flashSalesTimeService.findFlashSaleTimesByDate(dateDTO.getYear(), dateDTO.getMonth(),
				dateDTO.getDay());
		return list;
	}

	@GetMapping("/api/sellerProduct")
	public List<ProductDTO> getSellerProduct() {
		User u = session.get("user");
		String user = session.get("userSeller");

		List<ProductDTO> productDTOList = new ArrayList<>();
		List<Product> list = productService.findProductBySellerUsername(user);
		list.forEach(prDTO -> {
			ProductDTO productDTO = new ProductDTO();

			// Map the attributes from the Product to ProductDTO
			productDTO.setId(prDTO.getId());
			productDTO.setName(prDTO.getName());
			productDTO.setDescription(prDTO.getDescription());
			productDTO.setActive(prDTO.getActive());
			productDTO.setIsdelete(prDTO.getIsdelete());
			productDTO.setSoldCount(prDTO.getSoldCount());
			productDTO.setSellerProduct(prDTO.getSellerProduct());
			productDTO.setCategoryDetails(prDTO.getCategoryDetails());
			productDTO.setProductbrand(prDTO.getProductbrand());
			productDTO.setImageProducts(prDTO.getImageProducts());
			productDTO.setProductVariants(prDTO.getProductVariants());

			productDTOList.add(productDTO);
		});

		return productDTOList;
	}

	@PostMapping("/api/setFlashSale")
	public void setFlashSale(@RequestBody List<FlashSaleDTO> flashSaleDTO) throws Exception {
		List<FlashSaleDTO> list = flashSaleDTO;// 3

		list.forEach(fl -> {
			int idProduct = 0;
			// Optional<ProductVariant> optionalProductVariant = productVariantService
			// .findById( fl.getProductVariantId());

			try {
				FlashSale flashSale = flashSalesService.findByProductVariantId(fl.getProductVariantId());
				idProduct = flashSale.getProductVariant().getId();
			} catch (Exception e) {
				// TODO: handle exception
				idProduct = 0;
			}

			if (fl.getProductVariantId() != idProduct) {

				flashSalesService.insertFlashSale(fl.getDiscount(), fl.getAmountSell(), fl.getAmountOrder(),
						fl.getStatus(), fl.getFashSaleTimeId(), fl.getProductVariantId());

			} else {
				flashSalesService.updatetFlashSale(fl.getDiscount(), fl.getAmountSell(), fl.getAmountOrder(),
						fl.getStatus(), fl.getFashSaleTimeId(), fl.getProductVariantId());
			}

		});

	}

	@PostMapping("/seller/delete/product/{id}")
	public void deleteproductout(@PathVariable("id") String id) {
		System.out.println("Em choi bede");
		productVariantService.deleteProductVariantByProductId(id);
		imageProductService.deleteImageProductByProductId(id);
		productService.deleteById(id);
	}

	// admin rest seller API
	@GetMapping("/api/shop")
	public Map<String, Object> getSeller() {
		User u = session.get("user");
		int amountOrder = orderService.getCountOrderForSeller(u.getUsername());
		int amountFollow = followService.getCountFollowBySellerUsername(u.getUsername());
		int amountProduct = productService.getCountProductBySellerUsername(u.getUsername());
		int amountProductSell = productService.getCountProductSellBySellerUsername(u.getUsername(), 1005, 1009);
		Map<String, Object> sellerDetails = new HashMap<>();
		sellerDetails.put("seller", sellerService.findById(u.getUsername()).get());
		sellerDetails.put("amountOrder", amountOrder);
		sellerDetails.put("amountFollow", amountFollow);
		sellerDetails.put("amountProduct", amountProduct);
		sellerDetails.put("amountProductSell", amountProductSell);
		sellerDetails.put("imageuser", "/img/account/" + u.getUsername() + ".webp");
		return sellerDetails;
	}

	@PutMapping("/api/updateShop")
	public void saveSellerProfile(@RequestBody ShopDTO ShopDTO) {
		// Lấy thông tin người dùng từ session hoặc nguồn dữ liệu khác
		User user = session.get("user");
		List<String> listChange = new ArrayList<>();
		Seller selleru = sellerService.findById(user.getUsername()).get();
		sellerService.updateSeller(ShopDTO.getShopName(), ShopDTO.getAddress(), ShopDTO.getPhoneAddress(),
				ShopDTO.getMall(), true, ShopDTO.getDescription(), user.getUsername());
		if (!selleru.getShopName().equalsIgnoreCase(ShopDTO.getShopName())) {
			listChange.add(ShopDTO.getShopName());
		}
		if (!selleru.getAddress().equalsIgnoreCase(ShopDTO.getAddress())) {
			listChange.add(ShopDTO.getAddress());
		}
		if (!selleru.getPhoneAddress().equalsIgnoreCase(ShopDTO.getPhoneAddress())) {
			listChange.add(ShopDTO.getPhoneAddress());
		}
		if (!selleru.getDescription().equalsIgnoreCase(ShopDTO.getDescription())) {
			listChange.add(ShopDTO.getDescription());
		}
		notiService.sendHistory(user.getUsername(), "", "", "updateprofile", listChange.toString());
		List<String> listf = followService.getAllUserFollowShop(user.getUsername());
		for (String username : listf) {
			notiService.sendNotification(user.getUsername(), username, "/pageseller/" + user.getUsername(),
					"changeprofileshop", user.getUsername());
		}
	}

	@PostMapping("/api/updateimageprofile")
	public void uploadImage(@RequestParam("file") MultipartFile file) {
		User u = session.get("user");
		if (u != null) {
			File dir = Paths.get(fileStoragePath, "/account").toFile();
			if (!dir.exists()) {
				dir.mkdirs(); // Tạo thư mục nếu nó không tồn tại
			}

			// Tạo đường dẫn cho tệp mới
			Path path = Paths.get(dir.getAbsolutePath(), u.getUsername() + ".webp");
			System.out.println(path);
			try {
				// Xóa tệp cũ nếu tồn tại
				File oldFile = path.toFile();
				if (oldFile.exists()) {
					oldFile.delete();
				}
System.out.println("ok");
				file.transferTo(path.toFile());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GetMapping("/api/seller/product")
	public List<infoProductDTO> getAllProductFillBox() {
		User u = session.get("user");
		List<infoProductDTO> listInfoProduct = new ArrayList<>();
		List<Product> listProduct = productService.findProductBySellerUsernameAndIsdeleteProduct(u.getUsername());
		for (Product product : listProduct) {
			infoProductDTO dto = new infoProductDTO();
			ProductVariant pv = productVariantService.findProductVariantByProductId(product.getId());
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setActive(product.getActive());
			dto.setSoldCount(product.getSoldCount());
			dto.setPrice(pv.getPrice());
			dto.setPriceSale(pv.getPriceSale());
			dto.setQuantity(pv.getQuantity());
			dto.setNameImageProduct("/img/product/" + u.getUsername() + "/" + product.getId() + "/"
					+ imageProductService.findFirstImageProduct(product.getId()));
			listInfoProduct.add(dto);
		}
		return listInfoProduct;
	}

	@GetMapping("/api/seller/notifications")
	public Map<String, Object> getTop10Notifications() {
		User u = session.get("user");
		Map<String, Object> mapNotifications = new HashMap<>();
		List<Notifications> listNotifications = notificationsService.getTop10NotificationsByUserto(u.getUsername());
		long amountNotifications = notificationsService.getCountNotificationsStatusfalseAndUserto(u.getUsername());
		long acountNotifications = notificationsService.getCountNotificationsByUserto(u.getUsername());
		mapNotifications.put("listNotifications", listNotifications);
		mapNotifications.put("amountNotifications", amountNotifications);
		mapNotifications.put("acountNotifications", acountNotifications);
		System.out.println(acountNotifications);
		return mapNotifications;
	}

	@GetMapping("/api/seller/history")
	public List<Notifications> getAllHistory() {
		User u = session.get("user");
		List<Notifications> listNotifications = notificationsService.getAllNotificationsByUserfrom(u.getUsername());
		return listNotifications;
	}

	@PutMapping("/api/seller/updatenotification/{id}")
	public long handlePostRequest(@PathVariable("id") int id) {
		User u = session.get("user");
		notificationsService.updateNotificationsById(id);
		return notificationsService.getCountNotificationsStatusfalseAndUserto(u.getUsername());
	}

	@GetMapping("/api/seller/allnotifications")
	public List<Notifications> getAllNotifications() {
		User u = session.get("user");
		return notificationsService.getAllNotificationsByUserto(u.getUsername());
	}

	@PostMapping("/seller/sendRequest")
	public void sendRequest(@RequestParam("id") String id, @RequestParam("content") String content) {
		requestService.insertRequest(id, new Date(), 0, content);
	}

	@DeleteMapping("/seller/cancelRequest")
	public void cancelRequest(@RequestParam("id") String id) {
		Request pr = requestService.findRequestByEntityId(id);
		requestService.deleteById(pr.getId());
	}

	/* get balance wallet */
	@GetMapping("api/wallet")
	public Dwallet getBalanceWallet() {
		User u = session.get("user");
		String username = u.getUsername();
		// System.out.println("ssssssss" + username);
		return dwalletService.getDwalletByUsername(username);
	}

	@GetMapping("/api/history")
	public List<TransactionHistory> getHistoryTransaction() {
		User u = session.get("user");
		String username = u.getUsername();
		// System.out.println("ssssssss" + username);
		Dwallet dwallet = dwalletService.getDwalletByUsername(username);
		// System.out.println("ssss" + dwallet.getId());
		List<TransactionHistory> listHistoryTransactions = transactionHistoryService
				.getTransactionByIdWallet(dwallet.getId());
		return listHistoryTransactions;

	}
	
	@PostMapping("/seller/sendrequestactive")
	public void sendRequestActive(@RequestParam("id") String id, @RequestParam("content") String content) {
		requestService.insertRequest(id, new Date(), 1, content);
	}
	
	@GetMapping("/rest/list/work")
	public Map<String, Object> getListWork(){
		User u = session.get("user");
		Map<String, Object> mapWork = new HashMap<>();
		int choxacnhan = detailService.getCountOrderDetailsStatusShopByStatusId(u.getUsername(), 1001);
		int cholayhang = detailService.getCountOrderDetailsStatusShopByStatusId(u.getUsername(), 1003);
		int chothanhtoan = detailService.getCountOrderDetailsStatusShopByStatusId(u.getUsername(), 1002);
		int danggiao = detailService.getCountOrderDetailsStatusShopByStatusId(u.getUsername(), 1004);
		int nhanhang = detailService.getCountOrderDetailsStatusShopByStatusId(u.getUsername(), 1005);
		int choxuly = chothanhtoan + danggiao + nhanhang;
		int dahuy = detailService.getCountOrderDetailsStatusShopByStatusId(u.getUsername(), 1007);
		int trahang = detailService.getCountOrderDetailsStatusShopByStatusId(u.getUsername(), 1007);
		int hethang = productService.getCountProductQuantityZero(u.getUsername());
		int chuaban = productService.getCountProductActive(u.getUsername(), false);
		int dangban = productService.getCountProductActive(u.getUsername(), true);
		mapWork.put("choxacnhan", choxacnhan);
		mapWork.put("cholayhang", cholayhang);
		mapWork.put("choxuly", choxuly);
		mapWork.put("dahuy", dahuy);
		mapWork.put("trahang", trahang);
		mapWork.put("hethang", hethang);
		mapWork.put("chuaban", chuaban); 
		mapWork.put("dangban", dangban);
		return mapWork;
	}
	
	@GetMapping("/rest/ratiotrade")
	public Map<String, Object> getRatioTrade(@RequestParam("year") int year, @RequestParam("month") int month){
		User u = session.get("user");
		Map<String, Object> mapTrade = new HashMap<>();
		Double viewCount = productService.getCountViewCountProductShop(u.getUsername());
		Double addCart = cartService.getCountCartByProductShopAndCreatedDay(u.getUsername(), year, month);
		Double addOrder = orderService.getCountOrderByYearAndMonthAndProductShop(year, month, u.getUsername());
		Double addCarts = addCart + addOrder;
		if(viewCount == null) {
			viewCount = 0.0;
		}
		Double RatioTrade = (Double.valueOf(addCarts) / Double.valueOf(viewCount)) * 100;
		if(RatioTrade.isNaN()) {
			RatioTrade = 0.0;
		}
		mapTrade.put("viewCount", viewCount); 
		mapTrade.put("addCarts", addCarts);
		mapTrade.put("addOrder", addOrder); 
		mapTrade.put("RatioTrade", RatioTrade);
		return mapTrade;
	}
	
	@GetMapping("/categorynew")
	public List<Category> getListCategoryNewProduct(){
		List<Category> listCategory = categoryService.findAll();
		return listCategory;
	}
	
	@GetMapping("/categorydetailsnew/{id}")
	public List<CategoryDetails> getListCategoryDetailsNewProduct(@PathVariable("id") int id){
		List<CategoryDetails> listCategoryDetails = categoryDetailService.findAllCategoryDetailsById(id);
		return listCategoryDetails;
	}
	
	@GetMapping("/brandnew")
	public List<ProductBrand> getListProductBrandNewProduct(){
		return brandService.findAll();
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/insert/product")
	public Product insertProduct(@RequestBody Object object) throws ParseException {
		User u = session.get("user");
		List<Product> listProducts = productService.findProductBySellerUsernameAndIsdeleteProduct(u.getUsername());
		JsonNode jsonNode = objectMapper.valueToTree(object);
		JsonNode listNode = jsonNode.get("listvariant");
		List<ProductVariant> myList = objectMapper.convertValue(listNode, new TypeReference<List<ProductVariant>>() {});
		int idCategoryDetails = jsonNode.get("idCategoryDetails").asInt();
		int brand = jsonNode.get("brand").asInt();
		String description = jsonNode.get("description").asText();
		String name = jsonNode.get("name").asText();
		// insert sản phẩm
		productService.insertProduct(String.valueOf(listProducts.size() + 1), name, brand, description, new Date(), false, false, u.getUsername(), idCategoryDetails);
		Product product = productService.findLatestProductBySellerUsername(u.getUsername());
		// Duyệt list productVariant để thực hiện insert hoặc update
		for (ProductVariant productVariant : myList) {
			Integer idProductVariant = productVariant.getId();
			if (idProductVariant >= 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.updateProductVariant(idProductVariant, productVariant.getQuantity(),
						productVariant.getPrice(), productVariant.getPriceSale(), productVariant.getSize(),
						productVariant.getColor());
			} else if (idProductVariant < 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.addProductVariant(productVariant.getQuantity(), productVariant.getPrice(),
						productVariant.getPriceSale(), productVariant.getSize(), productVariant.getColor(), product.getId());
			}
		}
		return product;
	}
	
	@PostMapping("/insert/image/productnew")
	public void uploadImageInsert(@RequestParam("id") String id, @PathParam("files") MultipartFile[] files) {
		User u = session.get("user");
		List<String> listimg = fileManagerService.save(u.getUsername(), id, files);
	}
	
	@GetMapping("/img/product")
	public List<String> listImage(){
		User u = session.get("user");
		String id = session.get("idproduct");
		return fileManagerService.list(id, u.getUsername());
	}
	
	@PutMapping("/update/image/product")
	public void updateImageInsert(@PathParam("files") MultipartFile[] files) {
		String username = session.get("usernameseller");
		String id = session.get("idproduct");
		File dir = Paths.get(fileStoragePath + "/product", username, id).toFile();
		if(!dir.exists()){
            dir.mkdirs();
        }
		File[] filess = dir.listFiles();
        for (File file : filess) {
            fileManagerService.delete(username, id, file.getName());
        }
        imageProductService.deleteImageProductByProductId(id);
        List<String> listsave = fileManagerService.save(username, id, files);
	}
	
	@GetMapping("{filename}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(fis));
    }
	
	@PutMapping("/seller/product/restore/{id}")
	public void updateIsdeleteProduct(@PathVariable("id") String id) {
		User u = session.get("user");
        productService.updateProductIsDeleteById(false, id);
	}
}
