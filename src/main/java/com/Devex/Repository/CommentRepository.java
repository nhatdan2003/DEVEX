package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Comment;

import java.util.List;

@EnableJpaRepositories
@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    @Query("SELECT DISTINCT cmt FROM Comment cmt " +
            "WHERE cmt.productComment.id = ?1 " +
            "Order BY cmt.createdAt DESC")
    List<Comment> findByProductID(String productId);

    @Query("SELECT DISTINCT cmt FROM Comment cmt " +
            "WHERE cmt.productComment.id = ?1 " +
            "And cmt.rating = ?2 " +
            "Order BY cmt.createdAt DESC")
    List<Comment> findByProductIDAndStar(String productId,int ratting);

    @Query("SELECT DISTINCT cmt FROM Comment cmt " +
            "WHERE cmt.orderDetails.id = ?1 " +
            "and cmt.isSellerReply = false " +
            "Order BY cmt.createdAt DESC")
    Comment findByOrOrderDetailsID(String orderDetailsID);

    @Query("SELECT DISTINCT cmt FROM Comment cmt " +
            "WHERE cmt.orderDetails.id = ?1 " +
            "and cmt.isSellerReply = true " +
            "Order BY cmt.createdAt DESC")
    Comment findByOrOrderDetailsIDSeller(String orderDetailsID);
    
    @Query("SELECT c FROM Comment c WHERE c.productComment.sellerProduct.username like ?1")
    List<Comment> getAllCommentBySellerUsername(String username);
}
