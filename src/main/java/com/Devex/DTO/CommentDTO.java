package com.Devex.DTO;

import com.Devex.Entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    String username;
    String avatar;
    Comment comment;
    String sizeProduct;
    String colorProduct;
    int orderStatus;
    Boolean isSeller = false;
    Boolean isReply = false;

}
