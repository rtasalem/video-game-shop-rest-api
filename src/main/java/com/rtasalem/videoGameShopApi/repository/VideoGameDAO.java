package com.rtasalem.videoGameShopApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtasalem.videoGameShopApi.model.VideoGame;

public interface VideoGameDAO extends JpaRepository<VideoGame, Integer> {
	
	boolean existsByTitle(String title);

}
