package com.rtasalem.videoGameShopApi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rtasalem.videoGameShopApi.model.VideoGame;
import com.rtasalem.videoGameShopApi.service.VideoGameService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/games")
public class VideoGameController {
	
	private final VideoGameService videoGameService;

	public VideoGameController(VideoGameService videoGameService) {
		super();
		this.videoGameService = videoGameService;
	}
	
	@GetMapping
	public ResponseEntity<List<VideoGame>> getAllGames() {
		return ResponseEntity.status(HttpStatus.OK).body(videoGameService.findAllGames());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VideoGame> getGameById(@PathVariable int id) {
		return ResponseEntity.status(HttpStatus.OK).body(videoGameService.findGameById(id));
	}
	
	@PostMapping
	public ResponseEntity<VideoGame> addNewGame(@Valid @RequestBody VideoGame videoGame) {
		videoGameService.createNewGame(videoGame);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(videoGame.getId()).toUri();
		return ResponseEntity.created(location).body(videoGame);		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<VideoGame> updateExistingGame(@PathVariable int id, @Valid @RequestBody VideoGame videoGame) {
		return ResponseEntity.ok(videoGameService.editExistingGame(videoGame, id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGameById(@PathVariable int id) {
		videoGameService.removeGameById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
