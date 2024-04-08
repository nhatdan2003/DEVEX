package com.Devex.Controller.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.FlashSaleTimeDTO;
import com.Devex.DTO.ProductDTO;
import com.Devex.DTO.SellerDTO;
import com.Devex.DTO.StatisticalCategoryDetailsPieDTO;
import com.Devex.DTO.StatisticalRevenueMonthDTO;
import com.Devex.DTO.UpdatedRolesDTO;
import com.Devex.Entity.Dwallet;
import com.Devex.Entity.FlashSaleTime;
import com.Devex.Entity.Notifications;
import com.Devex.Entity.Product;
import com.Devex.Entity.Role;
import com.Devex.Entity.TransactionHistory;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.CartService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.DwalletService;
import com.Devex.Sevice.FlashSalesTimeService;
import com.Devex.Sevice.FollowService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.TransactionHistoryService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class DevexAdminRestController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService detailService;

	@Autowired
	private CookieService cookieService;

	@Autowired
	private CartService cartService;

	@Autowired
	private FlashSalesTimeService flashSalesTimeService;

	@Autowired
	private FollowService followService;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	private SessionService session;

	@Autowired
	private DwalletService dwalletService;

	@Autowired
	private TransactionHistoryService transactionHistoryService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	NotiService notiService;

	@GetMapping("walletAdmin")
	public Dwallet getBalanceWallet() {
		User u = session.get("user");
		String username = u.getUsername();
		return dwalletService.getDwalletAdminByUsername(username);
	}

	@GetMapping("historyAdmin")
	public List<TransactionHistory> getHistoryTransaction() {
		User u = session.get("user");
		String username = u.getUsername();
		Dwallet dwallet = dwalletService.getDwalletAdminByUsername(username);
		List<TransactionHistory> listHistoryTransactions = transactionHistoryService
				.getTransactionByIdWallet(dwallet.getId());
		return listHistoryTransactions;

	}

	@GetMapping("/userDetail")
	public Map<Object, Object> updateUser() {

		String username = sessionService.get("username");
		Map<Object, Object> userDetails = new HashMap<>();
		userDetails.put("userRoles", userRoleService.findAllByUserName(username));
		userDetails.put("roles", roleService.findAll());
		return userDetails;

	}

	@GetMapping("/flashSales")
	public List<FlashSaleTime> getFlashSaleTime(ModelMap model) {

		List<FlashSaleTime> listFlashSaleTime = flashSalesTimeService.findAll(Sort.by(Sort.Direction.DESC, "id"));

		return listFlashSaleTime;

	}

	@PostMapping("/saveFlashSales")
	public List<FlashSaleTime> saveFlashSaleTime(@RequestBody FlashSaleTimeDTO flashSaleTimeDTO, ModelMap model) {
		User u = session.get("user");
		String usernameAdmin = u.getUsername();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm aM/d/yyyy");

		// Chuyển đổi chuỗi thành LocalDateTime
		LocalDateTime firstTime = LocalDateTime.parse(flashSaleTimeDTO.getFirstTime(), formatter);
		LocalDateTime lastTime = LocalDateTime.parse(flashSaleTimeDTO.getLastTime(), formatter);
		flashSalesTimeService.insertFlashSaleTime(firstTime, lastTime);
		notiService.sendHistory(usernameAdmin, null, null, "newFlashSale", null);
		List<FlashSaleTime> listFlashSaleTime = flashSalesTimeService.findAll(Sort.by(Sort.Direction.DESC, "id"));

		return listFlashSaleTime;

	}

	@PostMapping("/updateUserRoles")
	public ResponseEntity<Map<String, String>> updateUserRoles(@RequestBody UpdatedRolesDTO updatedRoles) {
		// System.out.println("passs" +
		// passwordEncoder.encode(updatedRoles.getPhone()));
		User u = session.get("user");
		String usernameAdmin = u.getUsername();
		Map<String, String> response = new HashMap<>();
		User user = userService.findById(updatedRoles.getUserId()).orElse(null);
		SellerDTO seller = sellerService.findSeller(user.getUsername());
		String passUpdate = "";
		System.out.println("cho ngu" + u.getPassword().equals(updatedRoles.getPassword()));
		if (user.getPassword().equals(updatedRoles.getPassword())) {
			passUpdate = u.getPassword();
		} else {
			passUpdate = passwordEncoder.encode(updatedRoles.getPassword());
		}
		if (user != null) {
			// update user trước khi tạo roles
			userService.updateUser(updatedRoles.getFullname(), updatedRoles.getEmail(),
					passUpdate,
					updatedRoles.getPhone(), updatedRoles.getGender(), updatedRoles.isActive(),
					updatedRoles.getUserId());

			List<UserRole> userRolesToDelete = userRoleService.findAllByUserName(updatedRoles.getUserId());
			// Xoá tất cả user roles liên quan đến user dựa trên username
			for (UserRole userRole : userRolesToDelete) {
				userRoleService.delete(userRole);
			}

			// Thêm mới user roles với các role đã chọn
			for (String roleId : updatedRoles.getRoles()) {
				Role role = roleService.findById(roleId).orElse(null);
				if (role != null) {
					UserRole newUserRole = new UserRole();
					newUserRole.setUser(user);
					newUserRole.setRole(role);
					userRoleService.save(newUserRole); // Lưu userRole mới
					// tạo mới seller
					if ("SELLER".equals(roleId)) {
						if (seller == null) {
							sellerService.insertSeller(user.getUsername(), "Default", "Default",
									"Default", false, true,
									null);
						}
					}

				} // END IF ROLE

			} // end for
			notiService.sendNotification(usernameAdmin, user.getUsername(), null,
					"updateUser", null);
			notiService.sendHistory(usernameAdmin, null, "", "updateUser",
					user.getUsername());
			response.put("message", "Cập Nhật Roles Thành Công");
			return ResponseEntity.ok(response);
		} else {
			response.put("error", "User not found");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/admin/revenue")
	public Map<String, Object> getRevenueAdmin() {
		Map<String, Object> mapStatistical = new HashMap<>();
		long amountorder = orderService.count();
		Double amountRevenue = orderService.getTotalPriceOrder() * 0.05;
		Double test = orderService.getTotalPriceOrder();
		int amountUser = userService.getAmountUserOfAdmin();
		long amountProduct = productService.count();
		mapStatistical.put("amountorder", amountorder);
		mapStatistical.put("amountRevenue", amountRevenue);
		mapStatistical.put("amountUser", amountUser);
		mapStatistical.put("amountProduct", amountProduct);
		mapStatistical.put("test", test);
		return mapStatistical;
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/ad/revenue/line/month")
	public Map<String, Object> getRevenueByMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
		Map<String, Object> RevenueByMonth = new HashMap<>();
		User u = sessionService.get("user");
		int yearCompare;
		int monthCompare;
		if (month == 1) {
			yearCompare = year - 1;
			monthCompare = 12;
		} else {
			yearCompare = year;
			monthCompare = month - 1;
		}
		List<Object[]> listt = orderService.getTotalPriceOrderByMonthAndYear(year, month);
		List<Object[]> listtc = orderService.getTotalPriceOrderByMonthAndYear(yearCompare, monthCompare);
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
						price = (ob[1] == null) ? 0 : (double) ob[1] * 0.05;
						break;
					}
				}
				for (Object[] obc : listtc) {
					int day = (obc[0] == null) ? 0 : (int) obc[0];
					if (day == i) {
						priceCompare = (obc[1] == null) ? 0 : (double) obc[1] * 0.05;
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
						price = (ob[1] == null) ? 0 : (double) ob[1] * 0.05;
						break;
					}
				}
				for (Object[] obc : listtc) {
					int day = (obc[0] == null) ? 0 : (int) obc[0];
					if (day == i) {
						priceCompare = (obc[1] == null) ? 0 : (double) obc[1] * 0.05;
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
		RevenueByMonth.put("liststatis", liststatis);
		return RevenueByMonth;
	}

	@GetMapping("/ad/revenue/line/year")
	public Map<String, Object> getRevenueByYear(@RequestParam("year") int year) {
		User u = sessionService.get("user");
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		Map<String, Object> RevenueByMonth = new HashMap<>();
		List<Object[]> listt = orderService.getTotalPriceOrderByYear(year);
		List<Object[]> listtc = orderService.getTotalPriceOrderByYear(year - 1);
		for (int i = 1; i <= 12; i++) {
			double price = 0;
			double priceCompare = 0;
			for (Object[] ob : listt) {
				int day = (ob[0] == null) ? 0 : (int) ob[0];
				if (day == i) {
					price = (ob[1] == null) ? 0 : (double) ob[1] * 0.05;
					break;
				}
			}
			for (Object[] obc : listtc) {
				int day = (obc[0] == null) ? 0 : (int) obc[0];
				if (day == i) {
					priceCompare = (obc[1] == null) ? 0 : (double) obc[1] * 0.05;
					break;
				}
			}
			StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
			statistical.setDay(i);
			statistical.setPrice(price);
			statistical.setPriceCompare(priceCompare);
			liststatis.add(statistical);
		}
		RevenueByMonth.put("liststatis", liststatis);
		return RevenueByMonth;
	}

	@GetMapping("/ad/statistical/pie/year")
	public List<StatisticalCategoryDetailsPieDTO> getStatisticalCategoryDetails(@RequestParam("year") int year) {
		List<Object[]> liststatiscategoryob = detailService.getTop5CategoryDetailsAndAmountProductSell(year);
		List<StatisticalCategoryDetailsPieDTO> liststatiscategory = new ArrayList<>();
		for (Object[] result : liststatiscategoryob) {
			int id = (int) result[0];
			String categoryName = (String) result[1];
			long productCount = (long) result[2];
			StatisticalCategoryDetailsPieDTO entity = new StatisticalCategoryDetailsPieDTO();
			entity.setId(id);
			entity.setName(categoryName);
			entity.setCountProductSell(productCount);
			liststatiscategory.add(entity);
		}
		return liststatiscategory;
	}

	@GetMapping("/admin/notifications")
	public Map<String, Object> getTop10Notifications() {
		User u = sessionService.get("user");
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

	@GetMapping("/admin/history")
	public List<Notifications> getAllHistory() {
		User u = sessionService.get("user");
		List<Notifications> listNotifications = notificationsService.getAllNotificationsByUserfrom(u.getUsername());
		return listNotifications;
	}

	@PutMapping("/admin/updatenotification/{id}")
	public long handlePostRequest(@PathVariable("id") int id) {
		User u = sessionService.get("user");
		notificationsService.updateNotificationsById(id);
		return notificationsService.getCountNotificationsStatusfalseAndUserto(u.getUsername());
	}

	@GetMapping("/admin/allnotifications")
	public List<Notifications> getAllNotifications() {
		User u = sessionService.get("user");
		return notificationsService.getAllNotificationsByUserto(u.getUsername());
	}

	@GetMapping("/admin/listproductcate")
	public List<ProductDTO> getListProductByCategory(@Param("year") int year, @Param("id") int id) {
		List<ProductDTO> listProductDTO = new ArrayList<>();
		List<Product> listProduct = productService.getListProductByCategoryDetailsIdAndYear(id, year);
		for (Product product : listProduct) {
			ProductDTO p = new ProductDTO();
			p.setId(product.getId());
			p.setName(product.getName());
			p.setSoldCount(product.getSoldCount());
			p.setSellerProduct(product.getSellerProduct());
			listProductDTO.add(p);
		}
		return listProductDTO;
	}

	@GetMapping("/ad/order/pie/month")
	public List<StatisticalCategoryDetailsPieDTO> getStatisticalorderMonthPie(@RequestParam("year") int year,
			@RequestParam("month") int month) {
		List<Object[]> liststatisOrderOb = orderService.getStatisticalorderMonthPie(year, month);
		List<StatisticalCategoryDetailsPieDTO> liststatisOrderMonthPie = new ArrayList<>();
		for (Object[] result : liststatisOrderOb) {
			int id = (int) result[0];
			String statusName = (String) result[1];
			int orderCount = (int) result[2];
			StatisticalCategoryDetailsPieDTO entity = new StatisticalCategoryDetailsPieDTO();
			entity.setId(id);
			entity.setName(statusName);
			entity.setCountProductSell(Long.valueOf(orderCount).longValue());
			liststatisOrderMonthPie.add(entity);
		}
		return liststatisOrderMonthPie;
	}

	@GetMapping("/ad/order/pie/year")
	public List<StatisticalCategoryDetailsPieDTO> getStatisticalorderMonthPie(@RequestParam("year") int year) {
		List<Object[]> liststatisOrderOb = orderService.getStatisticalorderYearPie(year);
		List<StatisticalCategoryDetailsPieDTO> liststatisOrderYearPie = new ArrayList<>();
		for (Object[] result : liststatisOrderOb) {
			int id = (int) result[0];
			String statusName = (String) result[1];
			int orderCount = (int) result[2];
			StatisticalCategoryDetailsPieDTO entity = new StatisticalCategoryDetailsPieDTO();
			entity.setId(id);
			entity.setName(statusName);
			entity.setCountProductSell(Long.valueOf(orderCount).longValue());
			liststatisOrderYearPie.add(entity);
		}
		return liststatisOrderYearPie;
	}

}
