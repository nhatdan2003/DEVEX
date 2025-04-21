package com.Devex.Sevice.ServiceImpl;

import com.Devex.Entity.Dwallet;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.TransactionHistory;
import com.Devex.Sevice.DwalletService;
import com.Devex.Sevice.TransactionHistoryService;
import com.Devex.Sevice.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Service("transactionServiceImpl")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    DwalletService dwalletService;

    @Autowired
    TransactionHistoryService transactionHistoryService;

    //Trả tiền lại cho user khi huỷ đơn hàng
    @Override
    public void transactionBackToUser(OrderDetails orderDetails) {
        String fromUser = "haopg";
        String toUser = orderDetails.getOrder().getCustomerOrder().getUsername();
        double value = orderDetails.getOrder().getTotal();
        this.transactionDwallet(fromUser,toUser,value+orderDetails.getOrder().getTotalShip(),"Wallet");
    }
    //Hàm này chạy khi trả hàng hoàn tiền
    @Override
    public void transactionRefundToUser(OrderDetails orderDetails) {
        String fromUser = "haopg";
        String toUser = orderDetails.getOrder().getCustomerOrder().getUsername();
        double value = orderDetails.getPrice();
        this.transactionDwallet(fromUser,toUser,value,"Wallet");
    }
    //Hàm này chạy khi mà người dùng bấm vào đã hoàn thành đơn hàng-- tiền từ ví tổng sẽ chuyển về cho seller
    @Override
    public void transactionBackToSeller(OrderDetails orderDetails) {
        String fromUser = "haopg";
        String toUser = orderDetails.getProductVariant().getProduct().getSellerProduct().getUsername();
        double value = orderDetails.getPrice() * orderDetails.getQuantity();
        this.transactionDwallet(fromUser,toUser,value,"Wallet");
    }


    //Hàm này chạy khi thanh toán
    @Override
    public Boolean transactionDwallet(String fromUser, String toUser, double value,String payment) {
        //Tìm id ví dựa trên username truyền vào
        String from = dwalletService.findDwalletIDbyUsername(fromUser);
        String to = dwalletService.findDwalletIDbyUsername(toUser);
        if(to == null){
            to = "";
        }
        //tạo ví nhận tiền
        Dwallet toWallet;
        // ví nhận tham số rỗng thì số tiền sẽ chuyển về cho admin
        if(to.isBlank() || to.isEmpty()){
            //địa chỉ ví admin
            toWallet = dwalletService.findById("8103372244761076521115370034496188830661494547164347275008908087551803866339925788053704017408556230").get();
        } else{
            //nếu ví nhận có tham số thì sẽ truyền vào
            toWallet = dwalletService.findById(to).get();
        }
        Dwallet fromWallet = new Dwallet();
        if(!payment.equalsIgnoreCase("vnpayInToWallet")){
            //đây là ví người giử
            fromWallet = dwalletService.findById(from).get();
        }


        //Các trường hợp thanh toán
        switch (payment){
            
            case "ACB":
                from = "ACB-"+fromUser;
                break;
            case "vnpay":
                from ="vnpay-"+fromUser;
                break;
            case "vnpayInToWallet":
                from ="vnpay-"+fromUser;
                break;
            default:
            	//Kiem tra so du sau khi thanh toan co lon hon 0 khong?
            	double newBalance = 0;
                newBalance = fromWallet.getBalance() - value;
                if(newBalance >= 0) {
                	//Trừ số dư tương ứng từ ví người giử khi thanh toán bằng Dwallet
                    fromWallet.setBalance(newBalance);
                }else {
                	//neu nho hon 0 se return false
                	return false;
                }
                break;
        }

        //Thêm số dư tương ứng vào ví người nhận
        double newBalanceToWallet = toWallet.getBalance() + value;
        toWallet.setBalance(newBalanceToWallet);

        //Tạo lịch sử giao cho giao dịch
        TransactionHistory newHistory = new TransactionHistory();
        newHistory.setFrom(from);
        newHistory.setTo(toWallet.getId());
        newHistory.setValue(value);
        transactionHistoryService.save(newHistory);
        
        return true;

    }


}
