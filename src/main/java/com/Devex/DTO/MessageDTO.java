package com.Devex.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Devex.Entity.ImageProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
	private int id;
	private String content;
	private Date createdAt;
	private String senderID;
	private String senderAvatar;
	private String senderName;
	private String receiverID;
	private String receiverAvatar;
	private String receiverName;
	private String userID;
   
    
}
