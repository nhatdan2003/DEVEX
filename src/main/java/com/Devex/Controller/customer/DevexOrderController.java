package com.Devex.Controller.customer;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Devex.Entity.*;
import com.Devex.Sevice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.DTO.KeyBillDTO;
import com.Devex.Entity.Comment;
import com.Devex.Entity.Order;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;

@Controller
public class DevexOrderController {

    @Autowired
    SessionService sessionService;

    @Autowired
    CookieService cookieService;

    @Autowired
    ParamService paramService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService detailService;

    @Autowired
    SellerService sellerService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryDetailService categoryDetailService;

//    @Autowired
//    CustomerServiceImpl.FileManagerService fileManagerService;

    @Autowired
    ImageProductService imageProductService;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    CommentService commentService ;

    @Autowired
    NotificationsService notificationsService ;

    @Autowired
    NotiService notiService ;

    @Autowired
    TransactionService transactionService;
    
  
	@Autowired
	UserRoleService userRoleService;
	
	
	  @ModelAttribute("admin")
	  public Boolean getAdmin(Principal principal) {
		  User user = sessionService.get("user");
			if (user != null) {
				List<UserRole> roles = userRoleService.findAllByUserName(user.getUsername());
				for (UserRole u : roles) {
					if (u.getRole().getId().equals("ADMIN")) {
						System.out.println("tôi là admin kk");
						return true;
					}
				}
			}
	      return false;
	  }
	  
	  @ModelAttribute("seller")
	  public Boolean getSeller(Principal principal) {
		  User user = sessionService.get("user");
			if (user != null) {
				List<UserRole> roles = userRoleService.findAllByUserName(user.getUsername());
				for (UserRole u : roles) {
					if (u.getRole().getId().equals("SELLER")) {
						System.out.println("tôi là seller kk");
						return true;
					}
				}
			}
	      return false;
	  }

    @Autowired
    RequestService requestService;

    @GetMapping("/order")
    public String getOrderPage(Model model) {
        User u = sessionService.get("user");
        //Tìm toàn bộ hó đơn
        String username = u.getUsername();
        List<OrderDetails> allOrder = orderDetailService.findOrdersByCustomerID(username);
        HashMap<KeyBillDTO, List<OrderDetails>> allOrderByShop = new HashMap<>();
        allOrderByShop = setHashMapBillDetail(allOrderByShop,allOrder);
        model.addAttribute("allOrder", allOrderByShop);
        //Tìm hó đơn chờ thanh toán và chờ xác nhận
        HashMap<KeyBillDTO, List<OrderDetails>> allOrderPayingByShop = new HashMap<>();
            //Hoá đơn chờ xác nhận
        List<OrderDetails> payingOrder = orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(), 1001);
            //Hoá đơn chờ thanh toán
        payingOrder.addAll(orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(),1002));
            //Hoá đơn đã xác nhận
        payingOrder.addAll(orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(),1009));
        allOrderPayingByShop = setHashMapBillDetail(allOrderPayingByShop,payingOrder);
        model.addAttribute("payingOrder", allOrderPayingByShop);
       //Tìm hoá đơn vận chuyển và hoá đơn đan giao
        HashMap<KeyBillDTO, List<OrderDetails>> allOrderShipingByShop = new HashMap<>();
            //Đang vận chuyển
        List<OrderDetails> shipingOrder = orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(), 1003);
            //Đang giao
        shipingOrder.addAll(orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(), 1004));
        allOrderShipingByShop = setHashMapBillDetail(allOrderShipingByShop,shipingOrder);
        model.addAttribute("shipingOrder", allOrderShipingByShop);
        //tìm hoá đơn hoàn thành
        HashMap<KeyBillDTO, List<OrderDetails>> allOrderSuccessByShop = new HashMap<>();
        List<OrderDetails> successOrder = orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(), 1006);
        allOrderSuccessByShop = setHashMapBillDetail(allOrderSuccessByShop,successOrder);
        model.addAttribute("successOrder", allOrderSuccessByShop);
        //tìm hoá đơn đã huỷ
        HashMap<KeyBillDTO, List<OrderDetails>> allOrderCancelByShop = new HashMap<>();
        List<OrderDetails> cancelOrder = orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(), 1007);
        allOrderCancelByShop = setHashMapBillDetail(allOrderCancelByShop,cancelOrder);
        model.addAttribute("cancelOrder", allOrderCancelByShop);
        //tìm hoá đơn trả hàng hoàn tiền
        HashMap<KeyBillDTO, List<OrderDetails>> allOrderRefundByShop = new HashMap<>();
        List<OrderDetails> refundOrder = orderDetailService.findOrderByUsernameAndStatusID(u.getUsername(), 1008);
        allOrderRefundByShop = setHashMapBillDetail(allOrderRefundByShop,refundOrder);
        model.addAttribute("refundOrder", allOrderRefundByShop);

        System.out.println("co tong cong " + allOrderSuccessByShop.size() + " don hang");
        return "user/userOrder";
    }

    @GetMapping("/orderDetail/{id}")
    public String getOrderDetail(@PathVariable("id") String id, Model model) {
        User u = sessionService.get("user");
        String check = "";
        List<OrderDetails> listOrderDetails = detailService.findOrderDetailsByOrderID(id);
        List<OrderDetails> listcheckbutton = detailService.findOrderDetailsByOrderIDAndSellerUsername(id, "%%");
        for (OrderDetails orderDetails : listcheckbutton) {
            if (orderDetails.getStatus().getId() == 1009) {
                check = "Đã xác nhận";
            } else if (orderDetails.getStatus().getId() == 1007) {
                check = "Đã huỷ";
            } else if (orderDetails.getStatus().getId() == 1001) {
                check = "Chờ xác nhận";
            }
        }

        sessionService.set("listIdOrderDetails", listOrderDetails);
        model.addAttribute("orderDetails", listOrderDetails);
        model.addAttribute("idPrint", id);
        model.addAttribute("check", check);
        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("u", u.getUsername());
        model.addAttribute("user", u);
        if (order.getOrderStatus() != null && order.getOrderStatus().getName().equalsIgnoreCase("Hoàn thành")) {
            model.addAttribute("checko", true);
        } else {
            model.addAttribute("checko", false);
        }
        return "user/orderDetail";
    }

    @GetMapping("/order/huy")
    public String huyDonHang(@RequestParam("id") String id) {
        User u = sessionService.get("user");
        String userfrom = u.getUsername();
        String userto = "";
        String link = "http://localhost:8888/details/";
        OrderDetails od = detailService.findAllById(Collections.singleton(id)).get(0);
        id = od.getOrder().getId();
        List<OrderDetails> listOrderDetails = detailService.findOrderDetailsByOrderID(id);
        for (OrderDetails odL: listOrderDetails) {
            detailService.updateIdOrderDetailsStatus(1007, odL.getId());
        }
        transactionService.transactionBackToUser(listOrderDetails.get(0));
        return "redirect:/order#/cancel";
    }
    private HashMap<KeyBillDTO,List<OrderDetails>> setHashMapBillDetail(HashMap<KeyBillDTO,List<OrderDetails>> allOrderByShop,List<OrderDetails> allOrder){
            for (OrderDetails od : allOrder) {
                KeyBillDTO keyDTO = new KeyBillDTO();
                Date keyDate = orderService.findOrderById(od.getOrder().getId()).getCreatedDay();
                String keyOrderID = od.getOrder().getId();
                keyDTO.setShopName(od.getProductVariant().getProduct().getSellerProduct().getShopName());
                keyDTO.setCreatedDay(keyDate);
                keyDTO.setOrderID(keyOrderID);
                keyDTO.setAvt(od.getProductVariant().getProduct().getSellerProduct().getAvatar());
                if(commentService.findByOrOrderDetailsID(od.getId()) == null){
                    od.setIsComment(true);
                } else {
                    od.setIsComment(false);
                }
                // Thêm một giá trị vào key
                allOrderByShop.computeIfAbsent(keyDTO, k -> new ArrayList<>()).add(od);
            }
        // Chuyển HashMap thành danh sách các cặp key-value
        List<Map.Entry<KeyBillDTO, List<OrderDetails>>> entryList = new ArrayList<>(allOrderByShop.entrySet());
        // Sắp xếp danh sách dựa trên thuộc tính createdDay của KeyBillDTO
        Collections.sort(entryList, Collections.reverseOrder(Comparator.comparing(entry -> entry.getKey().getCreatedDay())));
        // Tạo một HashMap mới từ danh sách đã sắp xếp
        LinkedHashMap<KeyBillDTO, List<OrderDetails>> sortedOrderByShop = new LinkedHashMap<>();
        for (Map.Entry<KeyBillDTO, List<OrderDetails>> entry : entryList) {
            sortedOrderByShop.put(entry.getKey(), entry.getValue());
        }
        return sortedOrderByShop;
    }

    @PostMapping("/order/addcomment/{orderDetailID}")
    public String addComment(@PathVariable("orderDetailID") String orderDetailID,
                             @RequestParam("rating") int rating,
                             @RequestParam("content") String content) {
        User u = sessionService.get("user");
        OrderDetails od = orderDetailService.findById(orderDetailID).get();
        Comment comment = new Comment();
        comment.setIsSellerReply(false);
        comment.setContent(content);
        comment.setRating(rating);
        comment.setProductComment(od.getProductVariant().getProduct());
        comment.setUser(u);
        comment.setOrderDetails(od);
        commentService.save(comment);

        String userfrom = u.getUsername();
        String userto = od.getProductVariant().getProduct().getSellerProduct().getUsername();
        String link = "http://localhost:8888/details/"+od.getProductVariant().getProduct().getId();
        String object = od.getProductVariant().getProduct().getName();
        notiService.sendNotification(userfrom,userto,link,"comment", object);
        notiService.sendHistory(userfrom, userto, link, "comment", object);
        
        return "redirect:/details/"+od.getProductVariant().getProduct().getId()+"#comment";
    }

    @GetMapping("/order/thanhcong")
    public String xacNhanThanhCong(@RequestParam("id") String id) {
        User u = sessionService.get("user");
        String userfrom = u.getUsername();
        String userto = "";
        String link = "http://localhost:8888/details/";
        OrderDetails listOrderDetails = detailService.findById(id).get();
            detailService.updateIdOrderDetailsStatus(1006, listOrderDetails.getId());
            userto = listOrderDetails.getProductVariant().getProduct().getSellerProduct().getUsername();
        System.out.println(userto);
        //Chuyển tiền về cho người bán khi đơn hàng thành công
        transactionService.transactionBackToSeller(listOrderDetails);
        return "redirect:/order#/success";
    }

    @PostMapping("/order/refund/{orderDetailID}")
    public String refund(@PathVariable("orderDetailID") String orderDetailID,
                             @RequestParam("contentRefund") String contentRefund){
        User u = sessionService.get("user");
        //Tạo yêu cầu trả hàng hoàn tiền
        Request request = new Request();
        request.setContent(contentRefund);
        request.setStatusRequest(3);
        request.setEntityId(orderDetailID);
        requestService.save(request);

        return "redirect:/home";
    }
}
