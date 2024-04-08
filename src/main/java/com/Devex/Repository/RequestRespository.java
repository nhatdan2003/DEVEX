package com.Devex.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Request;

@EnableJpaRepositories
@Repository("requestRespository")
public interface RequestRespository extends JpaRepository<Request, Integer>{

	@Query("SELECT pr FROM Request pr ORDER BY pr.createdDay DESC")
	List<Request> getAllRequestDecreaseByCreatedDay();
	
	@Query("SELECT pr FROM Request pr WHERE pr.id = ?1")
	Request findRequestById(int id);
	
	@Query("SELECT pr FROM Request pr WHERE pr.entityId = ?1")
	Request findRequestByEntityId(String id);
	
	@Modifying
	@Query(value = "INSERT INTO Request (Entityid, Createdday, Statusrequest, Content) VALUES (:entityid, :createdDay, :status, :content)", nativeQuery = true)
	void insertRequest(@Param("entityid") String entityid, @Param("createdDay") Date createdDay, @Param("status") int status, @Param("content") String content);

	@Query("SELECT pr FROM Request pr WHERE pr.statusRequest = 0 ORDER BY pr.createdDay DESC")
	List<Request> getAllRequestFalseDecreaseByCreatedDay();
	
	@Query("SELECT pr FROM Request pr WHERE pr.statusRequest = 1 ORDER BY pr.createdDay DESC")
	List<Request> getAllRequestTrueDecreaseByCreatedDay();
	
	@Query("SELECT pr FROM Request pr WHERE pr.statusRequest = 3 ORDER BY pr.createdDay DESC")
	List<Request> getAllRequestOrderDecreaseByCreatedDay();
}
