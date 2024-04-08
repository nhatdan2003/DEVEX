package com.Devex.Controller.api;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.DTO.CommentDTO;
import com.Devex.Entity.Comment;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;
import com.Devex.Sevice.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
public class CommentAPIController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment) {
        // Xử lý và lưu bình luận vào cơ sở dữ liệu
        return commentService.save(comment);
    }


    @GetMapping("/rest/comment/{productId}")
    public  HashMap<String,List<CommentDTO>> getCommentsByProduct(@PathVariable String productId) {
        User u = sessionService.get("user");
        // Lấy danh sách bình luận theo sản phẩm
        List<Comment> commentList = new ArrayList<>(commentService.findByProductID(productId));
        HashMap<String,List<CommentDTO>> commentDTOList = new HashMap<>();
        if(sessionService.get("user") != null){
            commentList.forEach(cmt ->{
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setUsername(cmt.getUser().getUsername());
                commentDTO.setAvatar(cmt.getUser().getAvatar());
                commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                commentDTO.setComment(cmt);
                commentDTO.setIsSeller(u.getUsername().equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                    commentDTO.setIsReply(true);
                } else{
                    commentDTO.setIsReply(false);
                }
                commentDTOList.computeIfAbsent(cmt.getOrderDetails().getId(), k -> new ArrayList<>()).add(commentDTO);
            });
        }else{
            commentList.forEach(cmt ->{
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setUsername(cmt.getUser().getUsername());
                commentDTO.setAvatar(cmt.getUser().getAvatar());
                commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                commentDTO.setComment(cmt);
                commentDTO.setIsSeller("u.getUsername()".equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                    commentDTO.setIsReply(true);
                } else{
                    commentDTO.setIsReply(false);
                }
                commentDTOList.computeIfAbsent(cmt.getOrderDetails().getId(), k -> new ArrayList<>()).add(commentDTO);
            });
        }
        System.out.println("Co tong cong "+commentList.size() + " comment");

        return commentDTOList;
    }

    @GetMapping("/rest/commentwtseller/{productId}")
    public List<CommentDTO> getCommentsByProductWithoutSeller(@PathVariable String productId) {
        User u = sessionService.get("user");
        // Lấy danh sách bình luận theo sản phẩm
        List<Comment> commentList = new ArrayList<>(commentService.findByProductID(productId));
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if(sessionService.get("user") != null){
            commentList.forEach(cmt ->{
                if(!cmt.getIsSellerReply()){
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setUsername(cmt.getUser().getUsername());
                    commentDTO.setAvatar(cmt.getUser().getAvatar());
                    commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                    commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                    commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                    commentDTO.setComment(cmt);
                    commentDTO.setIsSeller(u.getUsername().equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                    if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                        commentDTO.setIsReply(true);
                    } else{
                        commentDTO.setIsReply(false);
                    }
                    commentDTOList.add(commentDTO);
                }
            });
        }else{
            commentList.forEach(cmt ->{
                if(!cmt.getIsSellerReply()){
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setUsername(cmt.getUser().getUsername());
                    commentDTO.setAvatar(cmt.getUser().getAvatar());
                    commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                    commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                    commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                    commentDTO.setComment(cmt);
                    commentDTO.setIsSeller("u.getUsername()".equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                    if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                        commentDTO.setIsReply(true);
                    } else{
                        commentDTO.setIsReply(false);
                    }
                    commentDTOList.add(commentDTO);
                }
            });
        }

        System.out.println("Co tong cong ListwtSeller "+commentList.size() + " comment");
        return commentDTOList;
    }

    @PostMapping("/rest/commentbystar/{productId}")
    public  HashMap<String,List<CommentDTO>> getCommentsByProductAndStar(@PathVariable String productId,@RequestBody int ratting) {
        User u = sessionService.get("user");
        // Lấy danh sách bình luận theo sản phẩm
        List<Comment> commentList = new ArrayList<>(commentService.findByProductIDAndStar(productId, ratting));
        HashMap<String,List<CommentDTO>> commentDTOList = new HashMap<>();
        if(sessionService.get("user") != null){
            commentList.forEach(cmt ->{
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setUsername(cmt.getUser().getUsername());
                commentDTO.setAvatar(cmt.getUser().getAvatar());
                commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                commentDTO.setComment(cmt);
                commentDTO.setIsSeller(u.getUsername().equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                    commentDTO.setIsReply(true);
                } else{
                    commentDTO.setIsReply(false);
                }
                commentDTOList.computeIfAbsent(cmt.getOrderDetails().getId(), k -> new ArrayList<>()).add(commentDTO);
            });
        }else{
            commentList.forEach(cmt ->{
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setUsername(cmt.getUser().getUsername());
                commentDTO.setAvatar(cmt.getUser().getAvatar());
                commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                commentDTO.setComment(cmt);
                commentDTO.setIsSeller("u.getUsername()".equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                    commentDTO.setIsReply(true);
                } else{
                    commentDTO.setIsReply(false);
                }
                commentDTOList.computeIfAbsent(cmt.getOrderDetails().getId(), k -> new ArrayList<>()).add(commentDTO);
            });
        }

        System.out.println("Co tong cong "+commentList.size() + " comment");

        return commentDTOList;
    }

    @PostMapping("/rest/commentwtsellerbystar/{productId}")
    public List<CommentDTO> getCommentsByProductAndStarWithoutSeller(@PathVariable String productId ,@RequestBody int ratting) {
        User u = sessionService.get("user");
        // Lấy danh sách bình luận theo sản phẩm
        List<Comment> commentList = new ArrayList<>(commentService.findByProductIDAndStar(productId, ratting));
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if(sessionService.get("user") != null){
            commentList.forEach(cmt ->{
                if(!cmt.getIsSellerReply()){
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setUsername(cmt.getUser().getUsername());
                    commentDTO.setAvatar(cmt.getUser().getAvatar());
                    commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                    commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                    commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                    commentDTO.setComment(cmt);
                    commentDTO.setIsSeller(u.getUsername().equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                    if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                        commentDTO.setIsReply(true);
                    } else{
                        commentDTO.setIsReply(false);
                    }
                    commentDTOList.add(commentDTO);
                }
            });
        }else{
            commentList.forEach(cmt ->{
                if(!cmt.getIsSellerReply()){
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setUsername(cmt.getUser().getUsername());
                    commentDTO.setAvatar(cmt.getUser().getAvatar());
                    commentDTO.setColorProduct(cmt.getOrderDetails().getProductVariant().getColor());
                    commentDTO.setSizeProduct(cmt.getOrderDetails().getProductVariant().getSize());
                    commentDTO.setOrderStatus(cmt.getOrderDetails().getStatus().getId());
                    commentDTO.setComment(cmt);
                    commentDTO.setIsSeller("u.getUsername()".equalsIgnoreCase(cmt.getOrderDetails().getProductVariant().getProduct().getSellerProduct().getUsername()));
                    if(commentService.findByOrOrderDetailsID(cmt.getOrderDetails().getId()) != null && commentService.findByOrOrderDetailsIDSeller(cmt.getOrderDetails().getId()) != null){
                        commentDTO.setIsReply(true);
                    } else{
                        commentDTO.setIsReply(false);
                    }
                    commentDTOList.add(commentDTO);
                }
            });
        }

        System.out.println("Co tong cong "+commentDTOList.size() + " comment");
        return commentDTOList;
    }

    @PostMapping("/rest/comment/add/{orderDetailID}")
    public ResponseEntity<Void> addComment(@PathVariable String orderDetailID, @RequestBody String content) {
        User u = sessionService.get("user");
        if(commentService.findByOrOrderDetailsIDSeller(orderDetailID) == null){
            Comment cmt = new Comment();
            cmt.setUser(u);
            cmt.setIsSellerReply(true);
            cmt.setContent(content);
            cmt.setRating(commentService.findByOrOrderDetailsID(orderDetailID).getRating());
            cmt.setProductComment(commentService.findByOrOrderDetailsID(orderDetailID).getProductComment());
            cmt.setOrderDetails(orderDetailService.findById(orderDetailID).get());
            commentService.save(cmt);
        }
        return ResponseEntity.ok().build();
    }
}

