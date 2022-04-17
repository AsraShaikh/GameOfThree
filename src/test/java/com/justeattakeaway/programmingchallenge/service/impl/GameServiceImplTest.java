package com.justeattakeaway.programmingchallenge.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.justeattakeaway.programmingchallenge.helper.GameHelper;
import com.justeattakeaway.programmingchallenge.service.MessagingService;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

	@InjectMocks
	private GameServiceImpl gameService;

	@Mock
	private GameHelper gameHelper;

	@Mock
	private MessagingService messagingService;

	@Test
	void testStartGame_InvalidGameMode() {
		String mode = "invalid";
		Integer number = null;
		String result = gameService.startGame(mode, number);
		assertTrue(result.contains("The mode parameter value is not valid."));
	}

	@Test
	void testStartGame_NullGameMode() {
		String mode = null;
		Integer number = null;
		String result = gameService.startGame(mode, number);
		assertTrue(result.contains("The mode parameter value is not valid."));
	}


}
