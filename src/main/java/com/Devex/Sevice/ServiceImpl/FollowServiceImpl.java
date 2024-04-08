package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Follow;
import com.Devex.Repository.FollowRepository;
import com.Devex.Sevice.FollowService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("followService")
public class FollowServiceImpl implements FollowService{
	@Autowired
	FollowRepository followRepository;

	@Override
	public Follow save(Follow entity) {
		return followRepository.save(entity);
	}

	@Override
	public List<Follow> saveAll(List<Follow> entities) {
		return followRepository.saveAll(entities);
	}

	@Override
	public Optional<Follow> findOne(Example<Follow> example) {
		return followRepository.findOne(example);
	}

	@Override
	public List<Follow> findAll(Sort sort) {
		return followRepository.findAll(sort);
	}

	@Override
	public Page<Follow> findAll(Pageable pageable) {
		return followRepository.findAll(pageable);
	}

	@Override
	public List<Follow> findAll() {
		return followRepository.findAll();
	}

	@Override
	public List<Follow> findAllById(Iterable<Integer> ids) {
		return followRepository.findAllById(ids);
	}

	@Override
	public Optional<Follow> findById(Integer id) {
		return followRepository.findById(id);
	}

	@Override
	public long count() {
		return followRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		followRepository.deleteById(id);
	}

	@Override
	public Follow getById(Integer id) {
		return followRepository.getById(id);
	}

	@Override
	public void delete(Follow entity) {
		followRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		followRepository.deleteAll();
	}

	@Override
	public int getCountFollowBySellerUsername(String username) {
		return followRepository.getCountFollowBySellerUsername(username);
	}

	@Override
	public int getCountFollowByCustomerUsername(String username) {
		return followRepository.getCountFollowByCustomerUsername(username);
	}

	@Override
	@Transactional
	public void insertFollow(String userId, String sellerId, Date creadtedday) {
		followRepository.insertFollow(userId, sellerId, creadtedday);
	}

	@Override
	public Follow getFollowByUsernameCustomerAndSeller(String userId, String shopId) {
		return followRepository.getFollowByUsernameCustomerAndSeller(userId, shopId);
	}

	@Transactional
	@Override
	public void deleteByCustomerAndSeller(String userId, String shopId) {
		followRepository.deleteByCustomerAndSeller(userId, shopId);
	}

	@Override
	public List<String> getAllUserFollowShop(String username) {
		return followRepository.getAllUserFollowShop(username);
	}
	
	
}
