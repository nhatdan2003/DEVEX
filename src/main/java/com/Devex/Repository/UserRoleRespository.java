package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.UserRole;

@EnableJpaRepositories
@Repository("userRoleRepository")
public interface UserRoleRespository extends JpaRepository<UserRole, Integer>{
	
	@Query("SELECT ur FROM UserRole ur WHERE ur.user.username LIKE ?1")
	List<UserRole> findAllByUserName(String username);
	
	
	@Query("SELECT ur.role.id FROM UserRole ur WHERE ur.user.username LIKE ?1")
	List<String> findAllroleByUserName(String username);
}
