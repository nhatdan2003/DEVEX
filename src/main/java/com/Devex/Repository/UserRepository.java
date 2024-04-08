package com.Devex.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.User;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String>{
	@Query("Select o FROM User o WHERE o.username = :username AND o.password = :password")
	User checkLogin(@Param("username") String username, @Param("password") String pass);
	
	@Query("Select o FROM User o WHERE o.username = :username AND o.active = true")
	User findByIdActive(@Param("username") String username);

	@Query("Select o FROM User o WHERE o.email = :email")
	User findEmail(@Param("email") String email);

	@Query("Select o FROM User o WHERE o.phone = :phone")
	User findPhone(@Param("phone") String phone);
	
	@Modifying
	@Transactional 
	@Query(value = "UPDATE Users SET Fullname = ?, Email = ?, Password = ?, Phone = ?, Gender = ?, Active = ? WHERE Username = ?", nativeQuery = true)
	void updateUser(String Fullname, String Email, String Password, String Phone, String Gender, Boolean Active,String Username);
	
	@Query("SELECT COUNT(u) FROM User u " + 
		       "JOIN u.roles rs " +
		       "JOIN rs.role r " +
		       "WHERE r.id IN ('CUSTOMER', 'SELLER')")
	int getAmountUserOfAdmin();
	
}
