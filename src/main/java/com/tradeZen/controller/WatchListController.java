package com.tradeZen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties.Bundles.Watch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tradeZen.model.Coin;
import com.tradeZen.model.User;
import com.tradeZen.model.WatchList;
import com.tradeZen.service.CoinService;
import com.tradeZen.service.UserService;
import com.tradeZen.service.WatchListService;

@RestController
@RequestMapping("/api/watchList")
public class WatchListController {
	
	@Autowired
	private WatchListService watchListService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@GetMapping("/user")
	public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		WatchList watchList= watchListService.findUserWatchList(user.getId());
		return ResponseEntity.ok(watchList);
	}
	
	@GetMapping("/{watchListId}")
	public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId) throws Exception{
		WatchList watchList = watchListService.findByid(watchListId);
		return ResponseEntity.ok(watchList);
	}
	
	@PatchMapping("/add/coin/{coinId}")
	public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization") String jwt, 
			@PathVariable String coinId) throws Exception {
		User user = userService.findUserProfileByJwt(jwt);
		Coin coin = coinService.findById(coinId);
		Coin addedCoin = watchListService.addItemToWatchList(coin, user);
		return ResponseEntity.ok(addedCoin);
	}
	
	

}
