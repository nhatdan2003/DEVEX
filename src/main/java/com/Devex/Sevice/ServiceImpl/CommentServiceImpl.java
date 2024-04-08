package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Comment;
import com.Devex.Repository.CommentRepository;
import com.Devex.Sevice.CommentService;

@SessionScope
@Service("commentService")
public class CommentServiceImpl implements CommentService{
	@Autowired
	CommentRepository commentRepository;

	@Override
	public Comment save(Comment entity) {
		return commentRepository.save(entity);
	}



	@Override
	public List<Comment> saveAll(List<Comment> entities) {
		return commentRepository.saveAll(entities);
	}

	@Override
	public <S extends Comment> Optional<S> findOne(Example<S> example) {
		return commentRepository.findOne(example);
	}

	@Override
	public List<Comment> findAll(Sort sort) {
		return commentRepository.findAll(sort);
	}

	@Override
	public Page<Comment> findAll(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public List<Comment> findAllById(Iterable<Integer> ids) {
		return commentRepository.findAllById(ids);
	}

	@Override
	public Optional<Comment> findById(Integer id) {
		return commentRepository.findById(id);
	}

	@Override
	public long count() {
		return commentRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		commentRepository.deleteById(id);
	}

	@Override
	public Comment getById(Integer id) {
		return commentRepository.getById(id);
	}

	@Override
	public void delete(Comment entity) {
		commentRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		commentRepository.deleteAll();
	}

	@Override
	public List<Comment> findByProductID(String productId) {
		return commentRepository.findByProductID(productId);
	}

    @Override
    public List<Comment> findByProductIDAndStar(String productId, int ratting) {
        return commentRepository.findByProductIDAndStar(productId,ratting);
    }

    @Override
    public Comment findByOrOrderDetailsID(String orderDetailsID) {
        return commentRepository.findByOrOrderDetailsID(orderDetailsID);
    }

    @Override
    public Comment findByOrOrderDetailsIDSeller(String orderDetailsID) {
		return commentRepository.findByOrOrderDetailsIDSeller(orderDetailsID);
    }

	@Override
	public List<Comment> getAllCommentBySellerUsername(String username) {
		return commentRepository.getAllCommentBySellerUsername(username);
	}
    
}
