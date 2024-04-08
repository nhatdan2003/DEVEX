package com.Devex.Controller.api;

import java.util.ArrayList;

import java.util.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.ProductDTO;
import com.Devex.DTO.infoProductDTO;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Comment;
import com.Devex.Entity.Follow;
import com.Devex.Entity.Notifications;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Entity.UserSearch;
import com.Devex.Entity.Voucher;
import com.Devex.Entity.VoucherProduct;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CommentService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.FollowService;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.RecommendationSystem;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserSearchService;
import com.Devex.Sevice.VoucherProductService;
import com.Devex.Sevice.VoucherService;

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
	private NotificationsService notificationsService;

	@Autowired
	private FollowService followService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private VoucherProductService voucherProductService;

	@Autowired
	private CategoryDetailService categoryDetailService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private ImageProductService imageProductService;

	@Autowired
	private NotiService notiService;

	@Autowired
	private UserSearchService userSearchService;

	private List<Product> uniqueProductList = new ArrayList<>();
	private List<String> listCategory = new ArrayList<>();
	private List<String> listBrand = new ArrayList<>();
	// tạo list chứa đối tượng DTO
	private List<ProductDTO> listProductDTO = new ArrayList<>();
	private List<ProductDTO> temPoraryList = new ArrayList<>();
	private List<String> historySearch = new ArrayList<>();
	private boolean checkHistory = false;

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
		List<UserSearch> historySearch = userSearchService.findAll();
		String cleanKeywords = removeSpecialCharacters(kwords); // loại bỏ kí tự đặc biệt
		Set<String> listHistorySearch = new LinkedHashSet<>();
		checkHistory = false;
		// System.out.println("aa" + historySearch.size());
		if (historySearch.size() == 0) {
			userSearchService.insertKeyWorks(cleanKeywords);
			listHistorySearch.add(cleanKeywords);
		}

		historySearch.forEach(key -> {
			listHistorySearch.add(key.getKeySearch().toLowerCase());
		});
		listHistorySearch.forEach(key -> {
			System.out.println("check: " + !listHistorySearch.contains(cleanKeywords));
			checkHistory = !listHistorySearch.contains(cleanKeywords);
		});
		if (checkHistory == true) {
			userSearchService.insertKeyWorks(cleanKeywords.toLowerCase());
		}

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
		Collections.sort(listProductDTO, Comparator.comparing(product -> {
			String name = ((ProductDTO) product).getName().toLowerCase();
			int index = name.indexOf(kwords.toLowerCase());
			return index == -1 ? Integer.MAX_VALUE : index;
		}));
		temPoraryList = listProductDTO; // lưu list vào 1 list tam thời

		return listProductDTO;
	}

	@GetMapping("historySearch")
	public Set<String> getMethodName() {
		List<UserSearch> listUserSearchs = userSearchService.findAll();
		Set<String> listHistorySearch = new LinkedHashSet<>();
		listUserSearchs.forEach(key -> {
			listHistorySearch.add(key.getKeySearch());
		});

		return listHistorySearch;
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

	@GetMapping("/shoppage/voucher/list")
	public ResponseEntity<List<Voucher>> getAllVoucher(@RequestParam("username") String username) {
		User user = sessionService.get("user");
		List<Voucher> list = voucherService.findAllByShop(username);

		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/shoppage/voucher/prod-voucher/{id}")
	public ResponseEntity<List<VoucherProduct>> getProdVoucher(@PathVariable("id") Integer id) {

		List<VoucherProduct> list = voucherProductService.findAllByVoucher(id);

		if (list != null) {
			return ResponseEntity.ok(list);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/shoppage/voucher/disabled/{id}")
	public ResponseEntity<Void> disabledCartDetail(@PathVariable("id") Integer id) {
		Voucher voucher = voucherService.getById(id);

		if (voucher != null) {
			voucherService.disabledVoucher(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/user/shoppage")
	public Map<String, Object> getInfoShopPage(@RequestParam("username") String username) {
		User u = sessionService.get("user");
		Map<String, Object> mapInfoShopPage = new HashMap<>();
		Seller seller = sellerService.findFirstByUsername(username);
		int amountProduct = productService.getCountProductBySellerUsername(username);
		int amountSellerFollow = followService.getCountFollowByCustomerUsername(username);
		int amountUserFollow = followService.getCountFollowBySellerUsername(username);
		double totalRating = 0;
		double rating = 0;
		int amountComment = 0;
		boolean checkFollow = false;
		List<CategoryDetails> listCategoryDetails = categoryDetailService
				.findAllCategoryDetailsBySellerUsername(username);
		List<Comment> listComment = commentService.getAllCommentBySellerUsername(username);
		for (Comment comment : listComment) {
			rating += comment.getRating();
		}
		if (rating != 0) {
			totalRating = rating / listComment.size();
		}
		amountComment = listComment.size();
		List<infoProductDTO> listInfoProduct = new ArrayList<>();
		List<Product> listProduct = productService.findProductBySellerUsernameAndIsdeleteProduct(username);
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
			dto.setNameImageProduct("/img/product/" + username + "/" + product.getId() + "/"
					+ imageProductService.findFirstImageProduct(product.getId()));
			dto.setCateId(product.getCategoryDetails().getId());
			listInfoProduct.add(dto);
		}
		Follow f = null;
		if (u != null) {
			f = followService.getFollowByUsernameCustomerAndSeller(u.getUsername(), username);
		}

		if (f != null) {
			checkFollow = true;
		}
		mapInfoShopPage.put("seller", seller);
		mapInfoShopPage.put("amountProduct", amountProduct);
		mapInfoShopPage.put("amountSellerFollow", amountSellerFollow);
		mapInfoShopPage.put("amountUserFollow", amountUserFollow);
		mapInfoShopPage.put("totalRating", totalRating);
		mapInfoShopPage.put("amountComment", amountComment);
		mapInfoShopPage.put("listInfoProduct", listInfoProduct);
		mapInfoShopPage.put("listCategoryDetails", listCategoryDetails);
		mapInfoShopPage.put("imageuser", "/img/account/" + username + ".webp");
		mapInfoShopPage.put("checkFollow", checkFollow);
		return mapInfoShopPage;
	}

	@PostMapping("/user/follow")
	public void insertFollow(@RequestParam("username") String username) {
		User u = sessionService.get("user");
		followService.insertFollow(u.getUsername(), username, new Date());
		notiService.sendNotification(u.getUsername(), username, "", "follow", "");
		notiService.sendHistory(u.getUsername(), username, "", "follow", "");
	}

	@DeleteMapping("/user/unfollow")
	public void deleteFollow(@RequestParam("username") String username) {
		User u = sessionService.get("user");
		followService.deleteByCustomerAndSeller(u.getUsername(), username);
		notiService.sendNotification(u.getUsername(), username, "", "unfollow", "");
		notiService.sendHistory(u.getUsername(), username, "", "unfollow", "");
	}

	@PutMapping("/upview")
	public void upView(@RequestParam("id") String id) {
		Product p = productService.findByIdProduct(id);
		if (p.getViewcount() == null) {
			productService.updateViewProduct(id, 1);
		} else {
			productService.updateViewProduct(id, p.getViewcount() + 1);
		}
	}

	public String removeSpecialCharacters(String input) {
		// Biểu thức chính quy để giữ lại chỉ các ký tự chữ cái, khoảng trắng, số và _
		String regex = "[^\\p{L}\\p{N}\\s]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.replaceAll("");
	}
}
