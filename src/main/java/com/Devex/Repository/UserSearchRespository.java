package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.UserSearch;

import jakarta.transaction.Transactional;

import java.util.List;

@EnableJpaRepositories
@Repository("userSearchRepository")
public interface UserSearchRespository extends JpaRepository<UserSearch, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO  User_Search (Key_Search) VALUES (?)", nativeQuery = true)
    void insertKeyWorks(String key);

    @Query(value = "SELECT TOP 10 Key_Search FROM User_Search ORDER BY ID DESC", nativeQuery = true)
    List<String> selectTop10();
}
