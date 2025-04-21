package com.Devex.Controller.seller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.Order;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.Product;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.FileManagerService;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.ServiceImpl.CustomerServiceImpl;

@Controller
@RequestMapping("/seller")
public class DevexSellerController {

    @Autowired
    private SessionService session;

    @Autowired
    private CookieService cookie;

    @Autowired
    private ParamService param;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDetailService categoryDetailService;

    @Autowired
    private FileManagerService fileManagerService;

    @Autowired
    private ImageProductService imageProductService;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private NotiService notiService;

    // @ModelAttribute("isMall")
    // public Boolean getUserAndIsMall() {
    // User u = session.get("user");
    // if (u != null) {
    // return sellerService.findFirstByUsername(u.getUsername()).getMall();
    // }
    // return false; // hoặc giá trị mặc định của bạn nếu không có user
    // }

    @GetMapping({ "/home", "/*" })
    public String getHomePage(Model model) {
        User u = session.get("user");
        // System.out.println(1);
        Boolean isMall = false;
        if (u != null) {
            Seller shop = sellerService.findById(u.getUsername()).get();
            isMall = shop.getMall();
        }
        model.addAttribute("isMall", isMall);
        // System.out.println(isMall);
        return "seller/index";
    }

    @GetMapping("/list/{listName}")
    public String getAnyList(@PathVariable("listName") String listName, Model model) {
        User u = session.get("user");
        switch (listName) {
            case "products": {
                model.addAttribute("titleType", "Sản phẩm");
                List<Product> listProducts = productService
                        .findProductBySellerUsernameAndIsdeleteProduct(u.getUsername());
                model.addAttribute("products", listProducts);
                break;
            }
            case "orders": {
                model.addAttribute("titleType", "Đơn hàng");
                List<Order> listOrder = orderService.findOrdersBySellerUsername(u.getUsername());
                // System.out.println(listOrder.size());
                model.addAttribute("orders", listOrder);
                break;
            }
            case "rating": {
                model.addAttribute("titleType", "Đánh giá");
                // câu lệnh select seller ở đây
                break;
            }
            case "restore": {
                model.addAttribute("titleType", "Khôi phục sản phẩm đã xóa");
                List<Product> listproduct = productService
                        .findProductBySellerUsernameAndIsdeleteTrueAndActiveTrueProduct(u.getUsername());
                model.addAttribute("restore", listproduct);
                break;
            }
            default:

        }
        model.addAttribute("listName", listName);
        return "seller/listManage";
    }

    @GetMapping("/product/restore/{idproduct}")
    public String restore(@PathVariable("idproduct") String idproduct) {
        User u = session.get("user");
        productService.updateProductIsDeleteById(false, idproduct);
        notiService.sendHistory(u.getUsername(), "", "/seller/product/edit/" + idproduct, "khoiphucsanpham", idproduct);
        return "redirect:/seller/list/restore";
    }

    @GetMapping("/product/show")
    public String showProduct() {

        return "seller/formManage";
    }

    @GetMapping("/product/edit/{idproduct}")
    public String editProduct(@PathVariable("idproduct") String idproduct, Model model) {
        Product product = productService.findByIdProduct(idproduct);
        session.set("usernameseller", product.getSellerProduct().getUsername());
        session.set("idproduct", idproduct);
        return "seller/formManage";
    }

    @GetMapping("/order/xacnhan")
    public String xacNhanDonHang(@RequestParam("id") String id) {
        User u = session.get("user");
        List<OrderDetails> listOrderDetails = session.get("listIdOrderDetails");
        for (OrderDetails orderDetails : listOrderDetails) {
            if (orderDetails.getProductVariant().getProduct().getSellerProduct().getUsername()
                    .equalsIgnoreCase(u.getUsername())) {
                detailService.updateIdOrderDetailsStatus(1009, orderDetails.getId());
            }
        }
        String userId = listOrderDetails.get(0).getOrder().getCustomerOrder().getUsername();
        String maHoaDon = listOrderDetails.get(0).getOrder().getId();
        notiService.sendHistory(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon, "xacnhandonhang", maHoaDon);
        notiService.sendNotification(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon, "donhangdaxacnhan",
                maHoaDon);
        return "redirect:/seller/orderDetail/" + id;
    }

    @GetMapping("/order/huy")
    public String huyDonHang(@RequestParam("id") String id) {
        User u = session.get("user");
        List<OrderDetails> listOrderDetails = session.get("listIdOrderDetails");
        for (OrderDetails orderDetails : listOrderDetails) {
            if (orderDetails.getProductVariant().getProduct().getSellerProduct().getUsername()
                    .equalsIgnoreCase(u.getUsername())) {
                detailService.updateIdOrderDetailsStatus(1007, orderDetails.getId());
            }
        }
        String userId = listOrderDetails.get(0).getOrder().getCustomerOrder().getUsername();
        String maHoaDon = listOrderDetails.get(0).getOrder().getId();
        notiService.sendHistory(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon, "huydonhangcuashop",
                maHoaDon);
        notiService.sendNotification(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon, "dahuycuanguoidung",
                maHoaDon);
        return "redirect:/seller/orderDetail/" + id;
    }

    @GetMapping("/order/vanchuyen")
    public String vanChuyen(@RequestParam("id") String id) {
        User u = session.get("user");
        List<OrderDetails> listOrderDetails = session.get("listIdOrderDetails");
        for (OrderDetails orderDetails : listOrderDetails) {
            if (orderDetails.getProductVariant().getProduct().getSellerProduct().getUsername()
                    .equalsIgnoreCase(u.getUsername())) {
                detailService.updateIdOrderDetailsStatus(1003, orderDetails.getId());
            }
        }
        String userId = listOrderDetails.get(0).getOrder().getCustomerOrder().getUsername();
        String maHoaDon = listOrderDetails.get(0).getOrder().getId();
        notiService.sendHistory(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon, "danggiaocuashop",
                maHoaDon);
        notiService.sendNotification(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon, "danggiaocuanguoidung",
                maHoaDon);
        return "redirect:/seller/orderDetail/" + id;
    }

    @GetMapping("/order/hoanthanh")
    public String hoanthanh(@RequestParam("id") String id) {
        User u = session.get("user");
        List<OrderDetails> listOrderDetails = session.get("listIdOrderDetails");
        for (OrderDetails orderDetails : listOrderDetails) {
            if (orderDetails.getProductVariant().getProduct().getSellerProduct().getUsername()
                    .equalsIgnoreCase(u.getUsername())) {
                detailService.updateIdOrderDetailsStatus(1006, orderDetails.getId());
            }
        }
        String userId = listOrderDetails.get(0).getOrder().getCustomerOrder().getUsername();
        String maHoaDon = listOrderDetails.get(0).getOrder().getId();
        notiService.sendHistory(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon, "hoanthanhcuanshop",
                maHoaDon);
        notiService.sendNotification(u.getUsername(), userId, "/seller/orderDetail/" + maHoaDon,
                "hoanthanhcuanguoidung", maHoaDon);
        return "redirect:/seller/orderDetail/" + id;
    }

    @GetMapping("/profile")
    public String getSellerProfile(ModelMap model) {
        User user = session.get("user");
        session.set("userSeller", user.getUsername());

        return "seller/sellerProfile";
    }

    @GetMapping("/orderDetail/{id}")
    public String getOrderDetail(@PathVariable("id") String id, Model model) {
        User u = session.get("user");
        String check = "";
        double totalPrice = 0;
        List<OrderDetails> listOrderDetails = detailService.findOrderDetailsByOrderIDAndSellerUsername(id,
                u.getUsername());
        List<OrderDetails> listcheckbutton = detailService.findOrderDetailsByOrderIDAndSellerUsername(id,
                u.getUsername());
        for (OrderDetails orderDetails : listcheckbutton) {
            if (orderDetails.getStatus().getId() == 1009) {
                check = "Đã xác nhận";
            } else if (orderDetails.getStatus().getId() == 1007) {
                check = "Đã huỷ";
            } else if (orderDetails.getStatus().getId() == 1001) {
                check = "Chờ xác nhận";
            }
        }
        for (OrderDetails od : listOrderDetails) {
            totalPrice += od.getPrice() * od.getQuantity();
        }
        OrderDetails odFirst = listcheckbutton.get(0);
        model.addAttribute("odFirst", odFirst);
        model.addAttribute("totalPrice", totalPrice);
        session.set("listIdOrderDetails", listOrderDetails);
        model.addAttribute("orderDetails", listOrderDetails);
        model.addAttribute("idPrint", id);
        model.addAttribute("check", check);
        Order order = orderService.findOrderById(id);
        // System.out.println(order.getOrderStatus().getName());
        model.addAttribute("order", order);
        model.addAttribute("u", u.getUsername());
        if (odFirst.getStatus() != null && odFirst.getStatus().getName().equalsIgnoreCase("Hoàn thành")) {
            model.addAttribute("checko", true);
        } else {
            model.addAttribute("checko", false);
        }
        return "seller/order/orderDetail";
    }

    @GetMapping("/orderPrint")
    public String getOrderPrint(Model model, @RequestParam("id") String id) {
        User u = session.get("user");
        String check = "";
        double totalPrice = 0;
        List<OrderDetails> listOrderDetails = detailService.findOrderDetailsByOrderID(id);
        List<OrderDetails> listcheckbutton = detailService.findOrderDetailsByOrderIDAndSellerUsername(id,
                u.getUsername());
        for (OrderDetails orderDetails : listcheckbutton) {
            if (orderDetails.getStatus().getId() == 1009) {
                check = "Đã xác nhận";
            } else if (orderDetails.getStatus().getId() == 1007) {
                check = "Đã huỷ";
            } else if (orderDetails.getStatus().getId() == 1001) {
                check = "Chờ xác nhận";
            }
        }
        for (OrderDetails od : listOrderDetails) {
            totalPrice += od.getPrice() * od.getQuantity();
        }
        OrderDetails odFirst = listcheckbutton.get(0);
        model.addAttribute("odFirst", odFirst);
        model.addAttribute("totalPrice", totalPrice);
        session.set("listIdOrderDetails", listOrderDetails);
        model.addAttribute("orderDetails", listOrderDetails);
        model.addAttribute("idPrint", id);
        model.addAttribute("check", check);
        Order order = orderService.findOrderById(id);
        // System.out.println(order.getOrderStatus().getName());
        model.addAttribute("order", order);
        model.addAttribute("u", u.getUsername());
        if (odFirst.getStatus() != null && odFirst.getStatus().getName().equalsIgnoreCase("Hoàn thành")) {
            model.addAttribute("checko", true);
        } else {
            model.addAttribute("checko", false);
        }
        return "seller/order/orderPrint";
    }

    @GetMapping("/orderReport")
    public String getOrderReport(Model model) {
        User u = session.get("user");
        String feedback = "";
        notiService.sendNotification(u.getUsername(), "admin", "", "hoanthanhcuanguoidung", feedback);
        notiService.sendNotification(u.getUsername(), "admin", "", "hoanthanhcuanshop", feedback);
        return "seller/order/orderReport";
    }

    @GetMapping("/product/create")
    public String create() {
        User u = session.get("user");
        List<Product> listProducts = productService.findProductBySellerUsernameAndIsdeleteProduct(u.getUsername());
        productService.insertProduct(String.valueOf(listProducts.size() + 1), "Nhập tên sản phẩm tại đây", 101, null,
                new Date(), false, false, u.getUsername(), 101);
        Product product = productService.findLatestProductBySellerUsername(u.getUsername());
        productVariantService.addProductVariant(1, 0.0, 0.0, "", "Đen", product.getId());
        fileManagerService.changeImage(u.getUsername(), product.getId());
        imageProductService.insertImageProduct("1", "default.webp", product.getId());
        session.set("idproduct", product.getId());
        return "redirect:/seller/product/edit/" + product.getId();
    }

    @GetMapping("/product/delete/{idproduct}")
    public String Deleteproduct(@PathVariable("idproduct") String id) {
        User u = session.get("user");
        productService.updateProductIsDeleteById(true, id);
        notiService.sendHistory(u.getUsername(), "", "", "deleteproduct", id);
        return "redirect:/seller/list/" + "products";
    }

    @GetMapping("/flashSale")
    public String getFlashSale() {
        User user = session.get("user");
        session.set("userSeller", user.getUsername());

        return "seller/FlashSale";
    }

    @GetMapping("/add/product")
    public String test() {
        return "seller/insertProduct";
    }
}
