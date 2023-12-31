package com.Devex.Controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.ProductDTO;
import com.Devex.Entity.Notifications;
import com.Devex.Entity.Product;
import com.Devex.Entity.User;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.FollowService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.RecommendationSystem;
import com.Devex.Sevice.SessionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class FillAPIController {
	@Autowired
	private SessionService sessionService;

	@Autowired
	private CookieService cookieService;

	@Autowired
	private ParamService paramService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RecommendationSystem recomendationService;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private OrderService orderService;

	private List<Product> uniqueProductList = new ArrayList<>();
	private List<String> listCategory = new ArrayList<>();
	private List<String> listBrand = new ArrayList<>();
	// tạo list chứa đối tượng DTO 
	private List<ProductDTO> listProductDTO = new ArrayList<>();
	private List<ProductDTO> temPoraryList = new ArrayList<>();

	@GetMapping("/filter")
	public List<ProductDTO> getProductDTO() {
		List<Product> products = productService.findAll();
		List<ProductDTO> productsList = products.stream().map(pr -> {
			// Thực hiện chuyển đổi từ Product thành ProductDTO ở đây
			// Ví dụ:
			ProductDTO dto = new ProductDTO();
			dto.setId(pr.getId());
			dto.setName(pr.getName());
			dto.setDescription(pr.getDescription());
			dto.setCategoryDetails(pr.getCategoryDetails());
			dto.setActive(pr.getActive());
			dto.setIsdelete(pr.getIsdelete());
			dto.setSoldCount(pr.getSoldCount());
			dto.setSellerProduct(pr.getSellerProduct());
			dto.setCategoryDetails(pr.getCategoryDetails());
			dto.setProductbrand(pr.getProductbrand());
			dto.setImageProducts(pr.getImageProducts());
			dto.setProductVariants(pr.getProductVariants());
			dto.setComments(pr.getComments());
			// Điền các thuộc tính khác của ProductDTO ở đây
			return dto;
		}).collect(Collectors.toList());

		return productsList;
	}

	@GetMapping("/search")
	public List<ProductDTO> getProductSearch() {
		String kwords = sessionService.get("keywordsSearch");
//		String kwords = "laptop";

		List<Product> list = new ArrayList<>();
		Set<Product> uniqueProducts = new LinkedHashSet<>();
		
		// Tìm tên từ theo từ khóa
		list.addAll(productService.findByKeywordName(kwords));
		// Tìm theo shop bán
		list.addAll(productService.findProductBySellerUsername("%" + kwords + "%"));
		// FILLTER SẢN PHẨM TRÙNG NHAU
		List<Product> pByC = new ArrayList<>();// hào
		if (list.size() > 0) {
			pByC = productService.findProductsByCategoryId(list.get(0).getCategoryDetails().getCategory().getId());
		} // hào
		list.addAll(pByC);

		list.forEach(pr -> {
			uniqueProducts.add(pr);
		});
		// Chuyển đổi lại thành danh sách (List)
		uniqueProductList = new ArrayList<>(uniqueProducts);
		listProductDTO = changeProductToProductDTO(uniqueProductList);
//		listProductDTO.sort(Comparator.comparing(ProductDTO::getName)
//		        .thenComparing(product -> product.getName().contains(kwords))); // sort theo keyword
//		Collections.reverse(listProductDTO);
		temPoraryList = listProductDTO; // lưu list vào 1 list tam thời

		return listProductDTO;
	}

	List<ProductDTO> changeProductToProductDTO(List<Product> list) {
		
		List<ProductDTO> productsList = list.stream().map(pr -> {
			// Thực hiện chuyển đổi từ Product thành ProductDTO ở đây
			// Ví dụ:
			ProductDTO dto = new ProductDTO();
			dto.setId(pr.getId());
			dto.setName(pr.getName());
			dto.setDescription(pr.getDescription());
			dto.setCategoryDetails(pr.getCategoryDetails());
			dto.setActive(pr.getActive());
			dto.setIsdelete(pr.getIsdelete());
			dto.setSoldCount(pr.getSoldCount());
			dto.setSellerProduct(pr.getSellerProduct());
			dto.setCategoryDetails(pr.getCategoryDetails());
			dto.setProductbrand(pr.getProductbrand());
			dto.setImageProducts(pr.getImageProducts());
			dto.setProductVariants(pr.getProductVariants());
			dto.setComments(pr.getComments());
			// Điền các thuộc tính khác của ProductDTO ở đây
			return dto;
		}).collect(Collectors.toList());

		return productsList;
	}// end change Product

	
	@GetMapping("/user/notifications")
    public Map<String, Object> getTop10Notifications() {
        User u = sessionService.get("user");
        Map<String, Object> mapNotifications = new HashMap<>();
        List<Notifications> listNotifications = notificationsService.getTop10NotificationsByUserto(u.getUsername());
        long amountNotifications = notificationsService.getCountNotificationsStatusfalseAndUserto(u.getUsername());
        long acountNotifications = notificationsService.getCountNotificationsByUserto(u.getUsername());
        mapNotifications.put("listNotifications", listNotifications);
        mapNotifications.put("amountNotifications", amountNotifications);
        mapNotifications.put("acountNotifications", acountNotifications);
        return mapNotifications;
    }
	
	@PutMapping("/user/updatenotification/{id}")
    public long handlePostRequest(@PathVariable("id") int id) {
        User u = sessionService.get("user");
        notificationsService.updateNotificationsById(id);
        return notificationsService.getCountNotificationsStatusfalseAndUserto(u.getUsername());
    }

    @GetMapping("/user/allnotifications")
    public List<Notifications> getAllNotifications() {
        User u = sessionService.get("user");
        return notificationsService.getAllNotificationsByUserto(u.getUsername());
    }
    
    @GetMapping("/user/history")
    public List<Notifications> getAllHistory() {
        User u = sessionService.get("user");
        List<Notifications> listNotifications = notificationsService.getAllNotificationsByUserfrom(u.getUsername());
        return listNotifications;
    }
    
    @GetMapping("/user/info")
    public Map<String, Object> getInfoUserProfile() {
        User u = sessionService.get("user");
        Map<String, Object> mapInfoUser = new HashMap<>();
        int amountOrder = orderService.getCountOrderByCustomerUsername(u.getUsername());
        int amountFollow = followService.getCountFollowByCustomerUsername(u.getUsername());
        mapInfoUser.put("amountOrder", amountOrder);
        mapInfoUser.put("amountFollow", amountFollow);
        return mapInfoUser;
    }
}
