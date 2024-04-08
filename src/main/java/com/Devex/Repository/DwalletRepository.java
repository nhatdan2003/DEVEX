package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Dwallet;
import com.Devex.Entity.TransactionHistory;

import jakarta.transaction.Transactional;

import java.util.List;

@EnableJpaRepositories
@Repository("dwalletRepository")
public interface DwalletRepository extends JpaRepository<Dwallet, String> {

    @Transactional
    @Query(value = "SELECT d.* FROM Dwallet d JOIN User_Roles ur ON d.Username = ur.Username WHERE ur.Role_ID LIKE 'SELLER' AND d.Username LIKE :username", nativeQuery = true)
    Dwallet getDwalletByUsername(@Param("username") String username);

    @Transactional
    @Query(value = "SELECT d.* FROM Dwallet d JOIN User_Roles ur ON d.Username = ur.Username WHERE ur.Role_ID LIKE 'CUSTOMER' AND d.Username LIKE :username", nativeQuery = true)
    Dwallet getDwalletCustomerByUsername(@Param("username") String username);

    @Transactional
    @Query(value = "SELECT d.* FROM Dwallet d JOIN User_Roles ur ON d.Username = ur.Username WHERE ur.Role_ID LIKE 'ADMIN' AND d.Username LIKE :username", nativeQuery = true)
    Dwallet getDwalletAdminByUsername(@Param("username") String username);

    @Query("SELECT dw.id FROM Dwallet dw WHERE dw.user.username like ?1")
    String findDwalletIDbyUsername(String username);
   
    
    @Transactional
    @Modifying
    @Query("UPDATE Dwallet SET balance = balance+ ?1 where user.username= ?2  ")
    void updateDwalletbyUsername1(int balance , String username);
    
}
