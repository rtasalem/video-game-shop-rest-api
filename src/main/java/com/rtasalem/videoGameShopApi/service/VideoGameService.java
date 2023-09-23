package com.rtasalem.videoGameShopApi.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rtasalem.videoGameShopApi.exception.ResourceNotFoundException;
import com.rtasalem.videoGameShopApi.exception.VideoGameTitleExistsException;
import com.rtasalem.videoGameShopApi.model.VideoGame;
import com.rtasalem.videoGameShopApi.repository.VideoGameDAO;

@Service
public class VideoGameService {
	
	// Creating an instance of the VideoGameDAO class to access methods from the repository layer.
	private final VideoGameDAO videoGameRepo;
	

	// The repository layer is injected into the service layer via constructor injection.
	public VideoGameService(VideoGameDAO videoGameRepo) {
		super();
		this.videoGameRepo = videoGameRepo;
	}
	
	private final static Logger log = LoggerFactory.getLogger(VideoGameService.class);

	public List<VideoGame> findAllGames() {
		log.info("Entering findAllGames()");
		log.info("Exiting findAllGames()");
		return videoGameRepo.findAll();
	}
	
	public VideoGame findGameById(int id) {
		log.info("Entering findGameById()");
		Optional<VideoGame> videoGameOpt = videoGameRepo.findById(id);
		if (videoGameOpt.isEmpty()) {
			log.info("Exiting findGameById()");
			throw new ResourceNotFoundException("A video game with an ID of " + id + " does not exist.");
		}
		log.info("Exiting findGameById()");
		return videoGameOpt.get();
	}
	
	public VideoGame createNewGame(VideoGame videoGame) {
		log.info("Entering createNewGame()");
		if (videoGameRepo.existsByTitle(videoGame.getTitle())) {
			log.info("Exiting createNewGame()");
			throw new VideoGameTitleExistsException("A video game with the title of " + videoGame.getTitle() + " already exists.");
		}
		log.info("Exiting createNewGame()");
		return videoGameRepo.save(videoGame);
	}

	public VideoGame editExistingGame(VideoGame videoGame, int id) {
		log.info("Entering editExistingGame()");
		if (!videoGameRepo.existsById(id)) {
			log.info("Exiting editExistingGame()");
			throw new ResourceNotFoundException("A video game with an ID of " + id + " does not exist.");
		}
		videoGame.setId(id);
		log.info("Exiting editExistngGame()");
		return videoGameRepo.save(videoGame);
	}

	public void removeGameById(int id) {
		log.info("Entering removeGameById()");
		if (!videoGameRepo.existsById(id) ) {
			log.info("Exiting removeGameById()");
			throw new ResourceNotFoundException("A video game with an ID of " + id + " does not exist.");
		}
		log.info("Exiting removeGameById()");
		videoGameRepo.deleteById(id);
	}

}
