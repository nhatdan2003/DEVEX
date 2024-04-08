package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.Devex.DTO.MessageDTO;
import com.Devex.Entity.ChatMessage;
import com.Devex.Entity.User;
import com.Devex.Repository.ChatMessageRespository;
import com.Devex.Repository.UserRepository;
import com.Devex.Sevice.ChatService;


@Service("chatService")
public class ChatServiceImpl implements ChatService{
	@Autowired
	private ChatMessageRespository chatMessageRespository;
	
	@Autowired
    private UserRepository userRepository;
	
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Override
	public MessageDTO sendMessage(MessageDTO message, String userID) {
    	//Xử lí dữ liệu tin nhắn
    	ChatMessage messToSave = new ChatMessage();
    	messToSave.setContent(message.getContent());
    	messToSave.setCreatedAt(new Date());
    	User userFrom = userRepository.findById(userID).get();
    	messToSave.setSender(userFrom);
    	User userTo = userRepository.findById(message.getReceiverID()).get();
    	messToSave.setReceiver(userTo);
    	
        // Lưu tin nhắn vào cơ sở dữ liệu
    	chatMessageRespository.save(messToSave);
//        // Gửi tin nhắn đến người nhận thông qua WebSocket
//        messagingTemplate.convertAndSendToUser(
//        		message.getReceiverID(),
//                "/topic/message",
//                message
//        );
        return chatMessageRespository.findNewest(userID);
    }
    
    @Override
	public MessageDTO sendMessageAuto(MessageDTO message, String userID) {
    	
    	//Xử lí dữ liệu tin nhắn
    	ChatMessage messToSave = new ChatMessage();
    	messToSave.setCreatedAt(new Date());
    	User userFrom = userRepository.findById(message.getSenderID()).get();
    	messToSave.setSender(userFrom);
    	User userTo = userRepository.findById(userID).get();
    	messToSave.setReceiver(userTo);
    	String content = "Xin chào " + userTo.getFullname() + ", chúng tôi có thể giúp gì cho bạn?";
    	messToSave.setContent(content);
    	
        // Lưu tin nhắn vào cơ sở dữ liệu
    	chatMessageRespository.save(messToSave);

        return chatMessageRespository.findNewest(userID);
    }

	
	@Override
	public List<MessageDTO> findAllByUser(String username) {
		return chatMessageRespository.findAllByUser(username);
	}

	@Override
	public ChatMessage save(ChatMessage entity) {
		return chatMessageRespository.save(entity);
	}

	@Override
	public List<ChatMessage> findAll() {
		return chatMessageRespository.findAll();
	}

	@Override
	public Optional<ChatMessage> findById(Integer id) {
		return chatMessageRespository.findById(id);
	}

	@Override
	public long count() {
		return chatMessageRespository.count();
	}

	@Override
	public ChatMessage getById(Integer id) {
		return chatMessageRespository.getById(id);
	}

	@Override
	public void delete(ChatMessage entity) {
		chatMessageRespository.delete(entity);
	}
	
	
}
