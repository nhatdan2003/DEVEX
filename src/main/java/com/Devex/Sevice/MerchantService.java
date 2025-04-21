package com.Devex.Sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MerchantService {
    @Autowired
    private RestTemplate restTemplate;
    

    private final String apiUrl = "https://sandbox-api.acb.com.vn/v1/merchant";  // URL API để lấy merchant_id

     public String getMerchantIdFromToken(String token) {
        // Tạo HttpHeaders và thiết lập Authorization Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Tạo HttpEntity với headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Gửi yêu cầu GET đến API và nhận phản hồi
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, 
                                                                org.springframework.http.HttpMethod.GET, 
                                                                entity, 
                                                                String.class);

        // Giả sử phản hồi trả về là JSON chứa merchant_id
        String jsonResponse = response.getBody();
        return extractMerchantIdFromJson(jsonResponse);
    }
    private String extractMerchantIdFromJson(String jsonResponse) {
        com.google.gson.JsonObject jsonObject = com.google.gson.JsonParser.parseString(jsonResponse).getAsJsonObject();
        return jsonObject.get("merchant_id").getAsString();
    }
    
}
