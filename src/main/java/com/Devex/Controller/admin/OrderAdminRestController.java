package com.Devex.Controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Devex.DTO.infoProductDTO;
import com.Devex.Entity.*;
import com.Devex.Sevice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class OrderAdminRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService detailService;
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private NotiService notiService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/list/order")
	public Map<String, Object> getListOrder(@RequestParam("status") int status, @RequestParam("search") String search){
		Map<String, Object> mapOrders = new HashMap<>();
		List<Order> listOrder = new ArrayList<>();
		if(status == 1 && search.equals("undefined") || status == 1 && search.equals("")) {
			listOrder = orderService.findAllOrderSortDown();
			System.out.println("Tìm tất cả");
		}else if(status == 1 && !search.equals("undefined")) {
			listOrder = orderService.findOrderByIdOrCustomer(search);
			System.out.println("Tìm tất cả theo keyword");
		}else if(status != 1 && search.equals("undefined") || status != 1 && search.equals("")){
			listOrder = orderService.findOrderByOrderStatusId(status);
			System.out.println("Tìm theo trạng thái");
		}else if(status != 1 && !search.equals("undefined")) {
			listOrder = orderService.findOrderByOrderStatusIdAndIdOrCustomer(status, search);
			System.out.println("Tìm trạng thái và keyword");
		}
		List<String> listCustomer = new ArrayList<>();
		List<String> listPayment = new ArrayList<>();
		for (Order o : listOrder) {
			Customer c = customerService.findById(o.getCustomerOrder().getUsername()).get();
			listCustomer.add(c.getFullname());
			listPayment.add(o.getPayment().getName());
		}
		mapOrders.put("listOrder", listOrder);
		mapOrders.put("listCustomer", listCustomer);
		mapOrders.put("listPayment", listPayment);
		return mapOrders;
	}
	
	@GetMapping("/list/status")
	public List<OrderStatus> getListStatus(){
		List<OrderStatus> listStatus = new ArrayList<>();
		List<OrderStatus> listt = orderStatusService.getListOrderStatusAdmin();
		listStatus.add(new OrderStatus(1, "Tất cả", null));
		for (OrderStatus o : listt) {
			listStatus.add(o);
		}
		return listStatus;
	}

	@GetMapping("/getallrequestrefund")
	public Map<String, Object> getAllRequestRefund(){
		Map<String, Object> mapOrderRequest = new HashMap<>();
		List<Request> listRequest = requestService.getAllRequestOrderDecreaseByCreatedDay();
		List<String> listNameProduct = new ArrayList<>();
		List<String> listCustomer = new ArrayList<>();
		List<String> listSeller = new ArrayList<>();
		List<Double> listTotalPrice = new ArrayList<>();
		for (Request r : listRequest) {
			OrderDetails d = detailService.getById(r.getEntityId());
			listNameProduct.add(d.getProductVariant().getProduct().getName());
			listCustomer.add(d.getOrder().getCustomerOrder().getUsername());
			listSeller.add(d.getProductVariant().getProduct().getSellerProduct().getUsername());
			listTotalPrice.add(d.getPrice());
		}
		mapOrderRequest.put("listRequest", listRequest);
		mapOrderRequest.put("listNameProduct", listNameProduct);
		mapOrderRequest.put("listCustomer", listCustomer);
		mapOrderRequest.put("listSeller", listSeller);
		mapOrderRequest.put("listTotalPrice", listTotalPrice);
		return mapOrderRequest;
	}

	@DeleteMapping("/delete/refund")
	public void deleteRefundRequest(@RequestParam("id") int id){
		User user = sessionService.get("user");
		OrderDetails od = orderDetailService.findById(requestService.findRequestById(id).getEntityId()).get();
		notiService.sendNotification(user.getUsername(),od.getOrder().getCustomerOrder().getUsername(), null,"refundFail",null);
		orderDetailService.updateIdOrderDetailsStatus(1006,od.getId());
		transactionService.transactionBackToSeller(od);
		requestService.deleteById(id);
	}

	@PutMapping("/update/refund")
	public void updateRefundRequest(@RequestParam("id") int id){
		User user = sessionService.get("user");
		OrderDetails od = orderDetailService.findById(requestService.findRequestById(id).getEntityId()).get();
		notiService.sendNotification(user.getUsername(),od.getOrder().getCustomerOrder().getUsername(), "/profile","refundSuccess",String.valueOf(od.getQuantity()*od.getPrice()));
		orderDetailService.updateIdOrderDetailsStatus(1008,od.getId());
		transactionService.transactionRefundToUser(od);
		requestService.deleteById(id);
	}
}
