package com.Devex.DTO;

import com.Devex.Entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    private String username;
    private String fullname;
    private String shopName;
    private String avatar;
    private Date createDay;
    private Boolean activeShop;
//    private List<Product> products;
}
