package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import com.Devex.DTO.MessageDTO;
import com.Devex.Entity.ChatMessage;
import com.Devex.Entity.User;

public interface ChatService {

	void delete(ChatMessage entity);

	ChatMessage getById(Integer id);

	long count();

	Optional<ChatMessage> findById(Integer id);

	List<ChatMessage> findAll();

	ChatMessage save(ChatMessage entity);

	List<MessageDTO> findAllByUser(String username);

	MessageDTO sendMessage(MessageDTO message, String userID);

	MessageDTO sendMessageAuto(MessageDTO message, String userID);

}
