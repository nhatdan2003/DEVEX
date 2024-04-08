package com.Devex.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.Devex.DTO.MessageDTO;
import com.Devex.Sevice.ChatService;
import com.Devex.Sevice.SessionService;

@Controller
public class ChatController {
	@Autowired
    private ChatService chatService;
	
	@Autowired
	private SessionService sessionService;

//	@PostMapping("/send")
	@MessageMapping("/message")
	@SendTo("/topic/message")
	public MessageDTO sendMessage(@RequestBody MessageDTO message, Principal principal) {
	    // Lưu tin nhắn vào cơ sở dữ liệu
		if(message.getId() == 2) {	//kiểm tra có phải tin nhắn tự động hay hong
			return chatService.sendMessageAuto(message, principal.getName());
		}else {
			return chatService.sendMessage(message, principal.getName());
		}
	}
	
}
