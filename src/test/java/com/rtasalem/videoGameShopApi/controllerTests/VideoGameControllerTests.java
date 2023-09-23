package com.rtasalem.videoGameShopApi.controllerTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rtasalem.videoGameShopApi.controller.VideoGameController;
import com.rtasalem.videoGameShopApi.model.VideoGame;
import com.rtasalem.videoGameShopApi.service.VideoGameService;

@ExtendWith(MockitoExtension.class)
class VideoGameControllerTests {
	
	private MockMvc mockMvc;
	
	@Mock
	private VideoGameService mockVideoGameService;
	
	@InjectMocks
	private VideoGameController mockVideoGameController;
	
	private VideoGame createGame(int id) {
		VideoGame videoGame = new VideoGame();
		videoGame.setId(id);
		return videoGame;
	}
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(mockVideoGameController).build();
	}
	
	@Test
	public void testGetAllGames_ReturnsListOfVideoGamesAndOkResponse() {
		// Arrange
		VideoGame game1 = createGame(1);
		VideoGame game2 = createGame(2);
		List<VideoGame> videoGames = Arrays.asList(game1, game2);
		when(mockVideoGameService.findAllGames()).thenReturn(videoGames);
		
		// Act
		ResponseEntity<List<VideoGame>> response = mockVideoGameController.getAllGames();
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(videoGames, response.getBody());
		verify(mockVideoGameService, times(1)).findAllGames();
	}
	
	@Test
	public void testGetGameById_ReturnsVideoGameAndOkResponse() {
		// Arrange 
		int id = 1;
		VideoGame game = createGame(id);
		when(mockVideoGameService.findGameById(id)).thenReturn(game);
		
		// Act
		ResponseEntity<VideoGame> response = mockVideoGameController.getGameById(id);
		
		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(game, response.getBody());
		verify(mockVideoGameService, times(1)).findGameById(id);
	}
	
}
