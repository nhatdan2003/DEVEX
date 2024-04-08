package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Comment;

public interface CommentService {

	void deleteAll();

	void delete(Comment entity);

	Comment getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<Comment> findById(Integer id);

	List<Comment> findAllById(Iterable<Integer> ids);

	List<Comment> findAll();

	Page<Comment> findAll(Pageable pageable);

	List<Comment> findAll(Sort sort);

	<S extends Comment> Optional<S> findOne(Example<S> example);

	List<Comment> saveAll(List<Comment> entities);

	Comment save(Comment entity);

    List<Comment> findByProductID(String productId);

	List<Comment> findByProductIDAndStar(String productId,int ratting);

	Comment findByOrOrderDetailsID(String orderDetailsID);

	Comment findByOrOrderDetailsIDSeller(String orderDetailsID);

	List<Comment> getAllCommentBySellerUsername(String username);
}
