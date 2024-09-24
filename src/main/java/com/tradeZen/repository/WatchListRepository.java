package com.tradeZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeZen.model.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {
	
	WatchList findByUserId(Long userId);

}
