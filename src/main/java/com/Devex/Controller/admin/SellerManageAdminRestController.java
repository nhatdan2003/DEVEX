package com.Devex.Controller.admin;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.Devex.DTO.SellerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.StatisticalRevenueMonthDTO;
import com.Devex.Entity.Comment;
import com.Devex.Entity.Order;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.CommentService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.FileManagerService;
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
import com.Devex.Sevice.UserService;
import com.Devex.Sevice.ServiceImpl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SellerManageAdminRestController {


	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private CategoryDetailService categoryDetailService;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private NotiService notiService;
	
	@Autowired
	private ProductBrandService brandService;
	
	@Autowired
	private RequestService productRequestService;
	
	@Autowired
    private FileManagerService fileManagerService;
	
	@Autowired
	private ImageProductService imageProductService;
	
	@Autowired
	private ProductVariantService productVariantService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService detailService;
	
	@Value("${myapp.file-storage-path}")
	private String fileStoragePath;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping("/getlistdistributor")
	public Map<String, Object> getAllDistributor() {
	    Map<String, Object> mapDistributor = new HashMap<>();
	    List<SellerDTO> listSeller = sellerService.findAllSellerDTOSortUp();
	    List<Double> listRating = new ArrayList<>();
	    List<String> listTime = new ArrayList<>();
	    List<Integer> listAmountOrder = new ArrayList<>();
	    List<Double> listRevenue = new ArrayList<>();
	    for (SellerDTO s : listSeller) {
	    	String time = "";
	    	Double averageRating = 0.0;
	    	Integer amountOrder = 0;
	    	Double revenue = 0.0;
	    	
	        List<Comment> listComment = commentService.getAllCommentBySellerUsername(s.getUsername());
	        averageRating = listComment.stream().mapToInt(Comment::getRating).average().orElse(0.0);

	        Calendar createDayCalendar = Calendar.getInstance();
	        createDayCalendar.setTime(s.getCreateDay());
	        Calendar currentCalendar = Calendar.getInstance();

	        long milliseconds = currentCalendar.getTimeInMillis() - createDayCalendar.getTimeInMillis();
	        long seconds = milliseconds / 1000;
	        long minutes = seconds / 60;
	        long hours = minutes / 60;
	        long days = hours / 24;
	        long years = days / 365;
	        long months = (days % 365) / 30;

	        if (minutes < 60) {
	        	time = minutes + " Phút";
	        } else if (hours < 60) {
	        	time = hours + " Giờ";
	        } else if (days < 32) {
	        	time = days + " Ngày";
	        } else if (months <= 12) {
	        	time = months + " Tháng";
	        } else {
	        	time = years + " Năm";
	        }
	        
	        amountOrder = orderService.getCountOrderForSeller(s.getUsername());
	        revenue = detailService.getTotalRevenueSeller(s.getUsername());
	        if(revenue == null) {
	        	revenue = 0.0;
	        }
	        
	        listTime.add(time);
	        listRating.add(averageRating);
	        listAmountOrder.add(amountOrder);
	        listRevenue.add(revenue);
	    }
	    
	    mapDistributor.put("listSeller", listSeller);
        mapDistributor.put("listRating", listRating);
        mapDistributor.put("listTime", listTime);
        mapDistributor.put("listAmountOrder", listAmountOrder);
        mapDistributor.put("listRevenue", listRevenue);
	    return mapDistributor;
	}

	@GetMapping("/search/distributor")
	public Map<String, Object> searchDistributor(@RequestParam("keyword") String keyword) {
	    Map<String, Object> mapDistributor = new HashMap<>();
	    List<SellerDTO> listSeller = sellerService.findByShopNameAndUsernameContainingKeyword(keyword);
	    List<Double> listRating = new ArrayList<>();
	    List<String> listTime = new ArrayList<>();
	    List<Integer> listAmountOrder = new ArrayList<>();
	    List<Double> listRevenue = new ArrayList<>();
	    for (SellerDTO s : listSeller) {
	    	String time = "";
	    	Double averageRating = 0.0;
	    	Integer amountOrder = 0;
	    	Double revenue = 0.0;
	    	
	        List<Comment> listComment = commentService.getAllCommentBySellerUsername(s.getUsername());
	        averageRating = listComment.stream().mapToInt(Comment::getRating).average().orElse(0.0);

	        Calendar createDayCalendar = Calendar.getInstance();
	        createDayCalendar.setTime(s.getCreateDay());
	        Calendar currentCalendar = Calendar.getInstance();

	        long milliseconds = currentCalendar.getTimeInMillis() - createDayCalendar.getTimeInMillis();
	        long seconds = milliseconds / 1000;
	        long minutes = seconds / 60;
	        long hours = minutes / 60;
	        long days = hours / 24;
	        long years = days / 365;
	        long months = (days % 365) / 30;

	        if (minutes < 60) {
	        	time = minutes + " Phút";
	        } else if (hours < 60) {
	        	time = hours + " Giờ";
	        } else if (days < 32) {
	        	time = days + " Ngày";
	        } else if (months <= 12) {
	        	time = months + " Tháng";
	        } else {
	        	time = years + " Năm";
	        }
	        
	        amountOrder = orderService.getCountOrderForSeller(s.getUsername());
	        revenue = detailService.getTotalRevenueSeller(s.getUsername());
	        if(revenue == null) {
	        	revenue = 0.0;
	        }
	        
	        listTime.add(time);
	        listRating.add(averageRating);
	        listAmountOrder.add(amountOrder);
	        listRevenue.add(revenue);
	    }
	    
	    mapDistributor.put("listSeller", listSeller);
        mapDistributor.put("listRating", listRating);
        mapDistributor.put("listTime", listTime);
        mapDistributor.put("listAmountOrder", listAmountOrder);
        mapDistributor.put("listRevenue", listRevenue);
	    return mapDistributor;
	}
	
	@GetMapping("/list/productseller")
	public Map<String, Object> getListProductBySeller(@RequestParam("username") String username){
		Map<String, Object> mapProduct = new HashMap<>();
		List<Product> listProduct = productService.findProductBySellerUsername(username);
		List<ProductVariant> listProductVariant = new ArrayList<>();
		List<Double> listRating = new ArrayList<>();
		List<Double> listRevenue = new ArrayList<>();
		List<String> listImage = new ArrayList<>();
		for (Product p : listProduct) {
			Double averageRating = 0.0;
			Double revenue = 0.0;
			String image = "";
			
			ProductVariant pv = productVariantService.findProductVariantByProductId(p.getId());
			List<Comment> listComment = commentService.getAllCommentBySellerUsername(username);
	        averageRating = listComment.stream().mapToInt(Comment::getRating).average().orElse(0.0);
	        revenue = detailService.getTotalPriceByProductId(p.getId());
	        image = "/img/product/" + username + "/" + p.getId() + "/" + imageProductService.findFirstImageProduct(p.getId());
			
			listProductVariant.add(pv);
			listRating.add(averageRating);
			listRevenue.add(revenue);
			listImage.add(image);
		}
		mapProduct.put("listProduct", listProduct);
		mapProduct.put("listProductVariant", listProductVariant);
		mapProduct.put("listRating", listRating);
		mapProduct.put("listRevenue", listRevenue);
		mapProduct.put("listImage", listImage);
		return mapProduct;
	}
	
	@GetMapping("/search/productseller")
	public Map<String, Object> searchListProductOfSeller(@RequestParam("keyword") String keyword, @RequestParam("username") String username){
		Map<String, Object> mapProduct = new HashMap<>();
		List<Product> listProduct = productService.findAllProductByUsernameContainingKeyword(keyword, username);
		List<ProductVariant> listProductVariant = new ArrayList<>();
		List<Double> listRating = new ArrayList<>();
		List<Double> listRevenue = new ArrayList<>();
		List<String> listImage = new ArrayList<>();
		for (Product p : listProduct) {
			Double averageRating = 0.0;
			Double revenue = 0.0;
			String image = "";
			
			ProductVariant pv = productVariantService.findProductVariantByProductId(p.getId());
			List<Comment> listComment = commentService.getAllCommentBySellerUsername(username);
	        averageRating = listComment.stream().mapToInt(Comment::getRating).average().orElse(0.0);
	        revenue = detailService.getTotalPriceByProductId(p.getId());
	        image = "/img/product/" + username + "/" + p.getId() + "/" + imageProductService.findFirstImageProduct(p.getId());
			
			listProductVariant.add(pv);
			listRating.add(averageRating);
			listRevenue.add(revenue);
			listImage.add(image);
		}
		mapProduct.put("listProduct", listProduct);
		mapProduct.put("listProductVariant", listProductVariant);
		mapProduct.put("listRating", listRating);
		mapProduct.put("listRevenue", listRevenue);
		mapProduct.put("listImage", listImage);
		return mapProduct;
	}
	
	@GetMapping("/seller/revenue/month")
	public List<StatisticalRevenueMonthDTO> getRevenueByMonth(@RequestParam("year") int year,
			@RequestParam("month") int month, @RequestParam("username") String username) {
		System.out.println(year);
		System.out.println(month);
		System.out.println(username);
		List<Object[]> listRevenue = detailService.getTotalPriceByMonthAndSellerUsername(year, month, username);
		System.out.println(listRevenue.size());
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		LocalDate date = LocalDate.of(year, month, 1);
		int lengthOfMonth = date.lengthOfMonth();
		for (int i = 1; i <= lengthOfMonth; i++) {
			double price = 0;
			double priceContribute = 0;
			for (Object[] ob : listRevenue) {
				int day = (ob[0] == null) ? 0 : (int) ob[0];
				if (day == i) {
					price = (ob[1] == null) ? 0 : (double) ob[1];
					priceContribute = price * 0.05;
					System.out.println(price+"a");
					break;
				}
			}
			StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
			statistical.setDay(i);
			statistical.setPrice(price);
			statistical.setPriceCompare(priceContribute);
			liststatis.add(statistical);
		}
		return liststatis;
	}
	
	@GetMapping("/seller/revenue/year")
	public List<StatisticalRevenueMonthDTO> getRevenueByYear(@RequestParam("year") int year, @RequestParam("username") String username) {
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		List<Object[]> listt = detailService.getTotalPriceByYearAndSellerUsername(year, username);
		for (int i = 1; i <= 12; i++) {
			double price = 0;
			double priceContribute = 0;
			for (Object[] ob : listt) {
				int day = (ob[0] == null) ? 0 : (int) ob[0];
				if (day == i) {
					price = (ob[1] == null) ? 0 : (double) ob[1];
					priceContribute = price * 0.05;
					System.out.println(price);
					break;
				}
			}
			StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
			statistical.setDay(i);
			statistical.setPrice(price);
			statistical.setPriceCompare(priceContribute);
			liststatis.add(statistical);
		}
		return liststatis;
	}
	
	@PutMapping("/update/productactive")
	public void updateActiveShop(@RequestParam("username") String username) {
		Seller s = sellerService.findFirstByUsername(username);
		if(s.getActiveShop() == true) {
			sellerService.updateActiveSellerByUsername(false, username);
		}else if(s.getActiveShop() == false){
			sellerService.updateActiveSellerByUsername(true, username);
			System.out.println(username);
		}
	}
	
	@GetMapping("/list/orderseller")
	public Map<String, Object> getListOrderByUsername(@RequestParam("username") String username){
		Map<String, Object> mapOrder = new HashMap<>();
		List<Order> listOrders = orderService.findOrdersBySellerUsername(username);
		List<String> listCustomer = new ArrayList<>();
		List<String> listPayment = new ArrayList<>();
		List<String> listStatus = new ArrayList<>();
		for (Order o : listOrders) {
			listCustomer.add(o.getCustomerOrder().getFullname());
			listPayment.add(o.getPayment().getName());
			listStatus.add(o.getOrderStatus().getName());
		}
		mapOrder.put("listOrders", listOrders);
		mapOrder.put("listCustomer", listCustomer);
		mapOrder.put("listPayment", listPayment);
		mapOrder.put("listStatus", listStatus);
		return mapOrder;
	}
	
	@GetMapping("/search/orderseller")
	public Map<String, Object> searchOrderByUsername(@RequestParam("keyword") String keyword, @RequestParam("username") String username){
		Map<String, Object> mapOrder = new HashMap<>();
		List<Order> listOrders = orderService.findAllOrderByIdAndUsernameContainingKeyword(username, keyword);
		System.out.println(keyword);
		System.out.println(username);
		System.out.println(listOrders.size());
		List<String> listCustomer = new ArrayList<>();
		List<String> listPayment = new ArrayList<>();
		List<String> listStatus = new ArrayList<>();
		for (Order o : listOrders) {
			listCustomer.add(o.getCustomerOrder().getFullname());
			listPayment.add(o.getPayment().getName());
			listStatus.add(o.getOrderStatus().getName());
		}
		mapOrder.put("listOrders", listOrders);
		mapOrder.put("listCustomer", listCustomer);
		mapOrder.put("listPayment", listPayment);
		mapOrder.put("listStatus", listStatus);
		return mapOrder;
	}
	
}
