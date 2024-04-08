package com.Devex.Sevice;

import java.util.List;

import com.Devex.Entity.History;
import com.Devex.Entity.User;

public interface HistoryService {
	void save(History history);
	List<History> fillAll();
	List<History> findByIdUser(User user);
	String findByIDProduct(String idProduct);
}
