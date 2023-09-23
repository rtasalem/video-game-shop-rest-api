package com.rtasalem.videoGameShopApi.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/games")
public class VideoGameController {
	
	// Creating an instance of the VideoGameService class to access the methods from the service layer.
	private final VideoGameService videoGameService;

	// The service layer is injected into the controller layer via constructor injection.
	public VideoGameController(VideoGameService videoGameService) {
		super();
		this.videoGameService = videoGameService;
	}
	
	// Importing the Logger so that information logs can be written for each method.
	private final static Logger log = LoggerFactory.getLogger(VideoGameController.class);
	
	@Operation(
			summary = "Retrieves a list of all the video games in the database (which is an in-memory datbase).",
			description = "The end point called getAllGames will return a ResponseEntity "
					+ "object containing a list of VideoGame objects. This is achieved by calling "
					+ "the findAllGames() method on the videoGameService.",
			method = "GET",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Displays a JSON list of video games.",
							content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}
					)
			}
	)
	@GetMapping
	public ResponseEntity<List<VideoGame>> getAllGames() {
		log.info("Entering getAllGames()");
		log.info("Exiting getAllGames()");
		return ResponseEntity.status(HttpStatus.OK).body(videoGameService.findAllGames());
	}
	
	@Operation(
			summary = "Retrieves a video game using the supplied id.",
			description = "Specifies a URL pattern and retrieves an VideoGame object based on the "
					+ "supplied id by calling the findGameById() method on the videoGameService.",
			method = "GET",
			responses = {
				@ApiResponse(
						responseCode = "200",
						description = "If the video game exists it will be returned as a JSON object.",
						content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}
				),
				@ApiResponse(
						responseCode = "404",
						description = "If the video game does not exist an error message will be returned as plain text.",
						content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE)}
				)
			}
	)
	@GetMapping("/{id}")
	public ResponseEntity<VideoGame> getGameById(@PathVariable int id) {
		log.info("Entering getGameById()");
		log.info("Exiting getGameById()");
		return ResponseEntity.status(HttpStatus.OK).body(videoGameService.findGameById(id));
	}
	
	@Operation(
			summary = "Creates a new video game in the database.",
			description = "Accepts and validates an VideoGame object passed through the body of the request.",
			method = "POST",
			responses = {
					@ApiResponse(
							responseCode = "201",
							description = "Video game was added to the database and returned as JSON object.",
							content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)},
							headers = {
									@Header(
											name = HttpHeaders.LOCATION,
											description = "URI of the new video game."
									)
							}
					),
					@ApiResponse(
							responseCode = "400",
							description = "The supplied video game is invalid. Returns a CSV plain text of validation errors.",
							content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE)}
					)
			}
			
	)
	@PostMapping
	public ResponseEntity<VideoGame> addNewGame(@Valid @RequestBody VideoGame videoGame) {
		log.info("Entering addNewGame()");
		videoGameService.createNewGame(videoGame);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(videoGame.getId()).toUri();
		log.info("Exiting addNewGame()");
		return ResponseEntity.created(location).body(videoGame);		
	}
	
	@Operation(
			summary = "Allows for the details of an existing video game to be updated.",
			description = "Specifies a URL pattern and allows for an existing VideoGame object to be updated using a supplied "
					+ "id with the data in the request body (videoGame). This is achieved by calling the editExistingGame() method on the "
					+ "videoGameService. The updated VideoGame object is then returned as the response body.",
			method = "PUT",
			responses = {
						@ApiResponse(
								responseCode = "200",
								description = "Video game information updated and returned as JSON object.",
								content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}
						),
						@ApiResponse(
								responseCode = "404",
								description = "Video game information cannot be updated as the id does not exist. Returns a plain text error message.",
								content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE)}
						)
			}
	)
	@PutMapping("/{id}")
	public ResponseEntity<VideoGame> updateExistingGame(@PathVariable int id, @Valid @RequestBody VideoGame videoGame) {
		log.info("Entering updateExistingGame()");
		log.info("Exiting updateExistingGame()");
		return ResponseEntity.ok(videoGameService.editExistingGame(videoGame, id));
	}
	
	@Operation(
			summary = "Allows for a video game to be completely removed from the database.",
			description = "Specifies a URL pattern and removes an VideoGame object from the database that is identified by a supplied id. "
					+ "This is achieved by calling the removeGameById() method on the videoGameService. A ResponseEntity object is returned with no body "
					+ "content as the response.",
			method = "DELETE",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Video game object deleted. Returns a JSON object with no body content.",
							content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}
					),
					@ApiResponse(
							responseCode = "404",
							description = "Video Game object could not be deleted as the id does not exists. Returns a plain text error message.",
							content = {@Content(mediaType = MediaType.TEXT_PLAIN_VALUE)}
					)
			}
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGameById(@PathVariable int id) {
		log.info("Entering deleteGameById()");
		videoGameService.removeGameById(id);
		log.info("Exiting deleteGameById()");
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
