package com.rtasalem.videoGameShopApi.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
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

import com.rtasalem.videoGameShopApi.exception.ResourceNotFoundException;
import com.rtasalem.videoGameShopApi.exception.VideoGameTitleExistsException;
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
	public void testFindGameById_ReturnsVideoGame_WhenGameExists() {
		// Arrange
		int id = 1;
		VideoGame newGame = new VideoGame();
		when(videoGameRepo.findById(id)).thenReturn(Optional.of(newGame));

		// Act
		VideoGame result = videoGameService.findGameById(id);

		// Assert
		assertEquals(newGame, result);
		verify(videoGameRepo).findById(id);
	}

	@Test
	public void testFindGameById_ThrowsResourceNotFoundException_WhenGameDoesNotExist() {
		// Arrange
		int id = 1;
		when(videoGameRepo.findById(id)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> videoGameService.findGameById(id));
		verify(videoGameRepo).findById(id);
	}

	@Test
	public void testCreateNewGame_CreatesGame_WhenValidData() {
		// Arrange
		VideoGame newGame = new VideoGame();
		newGame.setTitle("Uncharted");
		newGame.setDeveloper("Naughty Dog");
		when(videoGameRepo.existsByTitle(newGame.getTitle())).thenReturn(false);
		when(videoGameRepo.save(newGame)).thenReturn(newGame);

		// Act
		VideoGame result = videoGameService.createNewGame(newGame);

		// Assert
		assertEquals(newGame, result);
		verify(videoGameRepo).save(newGame);
	}

	@Test
	public void testCreateNewGame_ThrowsVideoGameTitleExistsException_WhenGameTitleExists() {
		// Arrange
		VideoGame newGame = new VideoGame();
		newGame.setTitle("Uncharted");
		when(videoGameRepo.existsByTitle(newGame.getTitle())).thenReturn(true);

		// Act & Assert
		assertThrows(VideoGameTitleExistsException.class, () -> videoGameService.createNewGame(newGame));
		verify(videoGameRepo, never()).save(newGame);
	}

	@Test 
	public void testEditExistingGame_UpdatesGame_WhenValidData() {
		// Arrange
		int id = 1;
		VideoGame game = new VideoGame();
		game.setId(id);
		when(videoGameRepo.existsById(id)).thenReturn(true);
		when(videoGameRepo.save(game)).thenReturn(game);

		// Act
		VideoGame result = videoGameService.editExistingGame(game, id);

		// Assert
		assertEquals(game, result);
		verify(videoGameRepo).save(game);
	}

	@Test
	public void testEditExistingGame_ThrowsResourceNotFoundException_WhenGameDoesNotExist() {
		// Arrange
		int id = 1;
		VideoGame game = new VideoGame();
		when(videoGameRepo.existsById(id)).thenReturn(false);

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> videoGameService.editExistingGame(game, id));
		verify(videoGameRepo, never()).save(game);
	}

	@Test
	public void testRemoveGameById_RemovesGame_WhenGameExists() {
		// Arrange
		int id = 1;
		when(videoGameRepo.existsById(id)).thenReturn(true);

		// Act
		videoGameService.removeGameById(id);

		// Assert
		verify(videoGameRepo).deleteById(id);
	}

	@Test
	public void testRemoveGameById_ThrowsResourceNotFoundException_WhenGameNotExists() {
		// Arrange
		int id = 1;
		when(videoGameRepo.existsById(id)).thenReturn(false);

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> videoGameService.removeGameById(id));
		verify(videoGameRepo, never()).deleteById(id);
	}
}
