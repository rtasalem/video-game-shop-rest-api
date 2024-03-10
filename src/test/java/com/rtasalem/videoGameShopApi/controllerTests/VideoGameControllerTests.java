package com.rtasalem.videoGameShopApi.controllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

	@Test
	public void testAddNewGame_CreatesNewGameSuccessfully() throws Exception {
		// Arrange
		VideoGame game = new VideoGame("Minecraft", "Open-world", "Mojang Studios", 26.99);
		ArgumentCaptor<VideoGame> captor = ArgumentCaptor.forClass(VideoGame.class);
		when(mockVideoGameService.createNewGame(captor.capture())).thenReturn(game);

		// Act
		mockMvc.perform(post("/api/v1/games").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(game)))
				// Assert
				.andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Minecraft"))
				.andExpect(jsonPath("$.genre").value("Open-world"))
				.andExpect(jsonPath("$.developer").value("Mojang Studios"))
				.andExpect(jsonPath("$.price").value("26.99"));

		verify(mockVideoGameService, times(1)).createNewGame(captor.getValue());
	}

	@Test
	public void testUpdateExistingGame_UpdatesGameSuccessfully() {
		// Arrange
		int id = 1;
		VideoGame updatedGame = createGame(id);
		when(mockVideoGameService.editExistingGame(any(), eq(id))).thenReturn(updatedGame);

		// Act
		ResponseEntity<VideoGame> response = mockVideoGameController.updateExistingGame(id, updatedGame);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedGame, response.getBody());
		verify(mockVideoGameService, times(1)).editExistingGame(updatedGame, id);
	}

	@Test
	public void testDeleteGameById_DeletesGameSuccessfully() {
		// Arrange
		int id = 1;

		// Act
		ResponseEntity<Void> response = mockVideoGameController.deleteGameById(id);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNull(response.getBody());
		verify(mockVideoGameService, times(1)).removeGameById(id);
	}
}
