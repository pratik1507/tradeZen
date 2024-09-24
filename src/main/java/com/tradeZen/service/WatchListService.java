package com.tradeZen.service;

import com.tradeZen.model.Coin;
import com.tradeZen.model.User;
import com.tradeZen.model.WatchList;

public interface WatchListService {
	
	WatchList findUserWatchList(Long userId) throws Exception;
	WatchList createWatchList(User user);
	WatchList findByid(Long id) throws Exception;
	
	Coin addItemToWatchList(Coin coin, User user) throws Exception;
	

}
