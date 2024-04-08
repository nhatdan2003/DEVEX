package com.Devex.Sevice.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Devex.Entity.History;
import com.Devex.Entity.User;
import com.Devex.Repository.HistoryResprository;
import com.Devex.Sevice.HistoryService;

@Service
public class HistorySevviceImpl implements HistoryService {
	
	@Autowired
	HistoryResprository historyResprository;
	@Override
	public void save(History history) {
		historyResprository.save(history);
	}
	@Override
	public List<History> fillAll() {
	return	historyResprository.findAll();
		
	}
	@Override
	public List<History> findByIdUser(User user) {
		String username = user.getUsername();
		return historyResprository.findByIdUser(username);
	}
	@Override
	public String findByIDProduct(String idProduct) {
		
		return  historyResprository.findbyIDProduct(idProduct);
	}	
}
