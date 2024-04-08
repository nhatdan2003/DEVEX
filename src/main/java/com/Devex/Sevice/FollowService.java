package com.Devex.Sevice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Follow;

public interface FollowService {

	void deleteAll();

	void delete(Follow entity);

	Follow getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<Follow> findById(Integer id);

	List<Follow> findAllById(Iterable<Integer> ids);

	List<Follow> findAll();

	Page<Follow> findAll(Pageable pageable);

	List<Follow> findAll(Sort sort);

	Optional<Follow> findOne(Example<Follow> example);

	List<Follow> saveAll(List<Follow> entities);

	Follow save(Follow entity);

	int getCountFollowBySellerUsername(String username);

	int getCountFollowByCustomerUsername(String username);

	void insertFollow(String userId, String sellerId, Date creadtedday);

	Follow getFollowByUsernameCustomerAndSeller(String userId, String shopId);

	void deleteByCustomerAndSeller(String userId, String shopId);

	List<String> getAllUserFollowShop(String username);

}
