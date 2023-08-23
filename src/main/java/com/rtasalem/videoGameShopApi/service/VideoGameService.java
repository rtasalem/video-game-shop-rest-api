package com.rtasalem.videoGameShopApi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rtasalem.videoGameShopApi.exception.ResourceNotFoundException;
import com.rtasalem.videoGameShopApi.exception.VideoGameTitleExistsException;
import com.rtasalem.videoGameShopApi.model.VideoGame;
import com.rtasalem.videoGameShopApi.repository.VideoGameDAO;

@Service
public class VideoGameService {
	
	private final VideoGameDAO videoGameRepo;
	

	public VideoGameService(VideoGameDAO videoGameRepo) {
		super();
		this.videoGameRepo = videoGameRepo;
	}

	public List<VideoGame> findAllGames() {
		return videoGameRepo.findAll();
	}
	
	public VideoGame findGameById(int id) {
		Optional<VideoGame> videoGameOpt = videoGameRepo.findById(id);
		if (videoGameOpt.isEmpty()) {
			throw new ResourceNotFoundException("A video game with an ID of " + id + " does not exist.");
		}
		return videoGameOpt.get();
	}
	
	public VideoGame createNewGame(VideoGame videoGame) {
		if (videoGameRepo.existsByTitle(videoGame.getTitle())) {
			throw new VideoGameTitleExistsException("A video game with the title of " + videoGame.getTitle() + " already exists.");
		}
		return videoGameRepo.save(videoGame);
	}

	public VideoGame editExistingGame(VideoGame videoGame, int id) {
		if (!videoGameRepo.existsById(id)) {
			throw new ResourceNotFoundException("A video game with an ID of " + id + " does not exist.");
		}
		videoGame.setId(id);
		return videoGameRepo.save(videoGame);
	}

	public void removeGameById(int id) {
		if (!videoGameRepo.existsById(id) ) {
			throw new ResourceNotFoundException("A video game with an ID of " + id + " does not exist.");
		}
		videoGameRepo.deleteById(id);
	}

}
