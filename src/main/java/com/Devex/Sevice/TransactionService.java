package com.Devex.Sevice;

import com.Devex.Entity.OrderDetails;

public interface TransactionService {

    public void transactionBackToUser(OrderDetails orderDetails);
    public void transactionBackToSeller(OrderDetails orderDetails);
    public Boolean transactionDwallet(String from, String to, double value, String payment);

    void transactionRefundToUser(OrderDetails od);
}
