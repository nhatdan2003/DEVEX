package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.DTO.MessageDTO;
import com.Devex.Entity.ChatMessage;

@EnableJpaRepositories
@Repository("chatMessageRepository")
public interface ChatMessageRespository extends JpaRepository<ChatMessage, Integer>{
//	@Query("SELECT m FROM ChatMessage m WHERE m.sender.username = :username Order By createdAt desc")
//	List<ChatMessage> findAllByUser1(@Param("username") String username);
	
	@Query("SELECT new com.Devex.DTO.MessageDTO(m.id, m.content, m.createdAt, m.sender.username, m.sender.avatar, m.sender.fullname, m.receiver.username, m.receiver.avatar, m.receiver.fullname, :username) FROM ChatMessage m WHERE m.sender.username = :username or m.receiver.username = :username Order By createdAt desc")
	List<MessageDTO> findAllByUser(@Param("username") String username);
	
	@Query("SELECT new com.Devex.DTO.MessageDTO(m.id, m.content, m.createdAt, m.sender.username, m.sender.avatar, m.sender.fullname, m.receiver.username, m.receiver.avatar, m.receiver.fullname, :username) FROM ChatMessage m WHERE m.createdAt = (SELECT MAX(m2.createdAt) FROM ChatMessage m2)")
	MessageDTO findNewest(@Param("username") String username);

}
