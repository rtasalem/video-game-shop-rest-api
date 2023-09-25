package com.rtasalem.videoGameShopApi.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rtasalem.videoGameShopApi.model.VideoGame;
import com.rtasalem.videoGameShopApi.repository.VideoGameDAO;
import com.rtasalem.videoGameShopApi.service.VideoGameService;

@ExtendWith(MockitoExtension.class)
class VideoGameServiceTests {

	@Mock
	private VideoGameDAO videoGameRepo;

	@InjectMocks
	private VideoGameService videoGameService;

	@Test
	public void testFindAllGames_ReturnsListOfVideoGames() {
		// Arrange
		List<VideoGame> games = new ArrayList<>();
		when(videoGameRepo.findAll()).thenReturn(games);

		// Act
		List<VideoGame> result = videoGameService.findAllGames();

		// Assert
		assertEquals(games, result);
		verify(videoGameRepo).findAll();
	}

	@Test
	public void testFindGameById_ReturnsVideoGame() {
		// Arrange
		int id = 1;
		VideoGame game = new VideoGame();
		when(videoGameRepo.findById(id)).thenReturn(Optional.of(game));

		// Act
		VideoGame result = videoGameService.findGameById(id);

		// Assert
		assertEquals(game, result);
		verify(videoGameRepo).findById(id);
	}

}
