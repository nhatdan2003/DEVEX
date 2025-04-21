package com.Devex.Controller.customer;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Sevice.MerchantService;
import com.Devex.Sevice.SessionService;


import io.github.cdimascio.dotenv.Dotenv;

@Controller
public class ACBController {

    @Autowired
    SessionService session;

    @Autowired
    MerchantService merchantService;

    @PostMapping("/acb-payment")
    public String ACB_Payment(Model model) {
        try {
            // Tải biến môi trường từ file .env
            Dotenv dotenv = Dotenv.load();
            String token_ACB = dotenv.get("API_KEY_ACB"); // Token ACB      
            String number_account = dotenv.get("Number_Account"); // Token ACB

            // // Lấy danh sách giỏ hàng từ session
            List<CartDetailDTo> list = session.get("listItemOrder", null);
            System.out.println(list);
            if (list == null || list.isEmpty()) {
                model.addAttribute("error", "Giỏ hàng trống.");
                return "error";  // Trả về trang lỗi nếu giỏ hàng rỗng
            }
            
            // // // Tính tổng tiền từ giỏ hàng
            double total = session.get("total", 0.0);
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTotal = currencyFormatter.format(total);
            int id_oder = list.get(0).getId();
            // int id_transaction = session.get("id");
            System.out.println(id_oder);
            String qrUrl = "https://img.vietqr.io/image/acb-"+number_account+"-qr.png?amount="+total+"&addInfo= Thanh toán hóa đơn: "+id_oder;
        
            model.addAttribute("total", formattedTotal);
            model.addAttribute("id", id_oder);
            model.addAttribute("qrUrl", qrUrl);
            return "user/ACBPayment";  // View hiển thị QR
    

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình thanh toán.");
            return "admin/erorr404";  // Trả về trang lỗi nếu có lỗi
        }
    }

 
}
