package com.Devex.Controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.MessageDTO;
import com.Devex.DTO.ShopDTO;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Sevice.ChatService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/message")
public class ChatAPIController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	
	@GetMapping("/list")
	public ResponseEntity<List<MessageDTO>> getAllMessage() {
		User user = sessionService.get("user", null);
		if(user != null) {
			List<MessageDTO> list = chatService.findAllByUser(user.getUsername());
			if(list != null) {
				return ResponseEntity.ok(list);
			}else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();


	}
	
	@GetMapping("/list-shop")
	public ResponseEntity<List<ShopDTO>> getAllShop() {
		List<ShopDTO> list = sellerService.findAllId();
		if(list != null) {
			return ResponseEntity.ok(list);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
//	@PostMapping("/send")
//	@MessageMapping("/message")
//	@SendTo("/user/topic/message")
//	public void sendMessage(@RequestBody MessageDTO message) {
//	    // Lưu tin nhắn vào cơ sở dữ liệu
//		System.out.println(0);
//		System.out.println(1);
//		User user = userService.getById("baolh");
//		System.out.println(111);
//		System.out.println(user.getFullname());
////		chatService.sendMessage(message);
//		System.out.println(11);
//
//	}

	
	
}
