package com.justeattakeaway.programmingchallenge.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.justeattakeaway.programmingchallenge.GameState;
import com.justeattakeaway.programmingchallenge.MessageType;
import com.justeattakeaway.programmingchallenge.helper.GameHelper;
import com.justeattakeaway.programmingchallenge.helper.GameResultBuider;
import com.justeattakeaway.programmingchallenge.helper.GameStateManager;
import com.justeattakeaway.programmingchallenge.models.Acknowledgement;
import com.justeattakeaway.programmingchallenge.models.GameResult;
import com.justeattakeaway.programmingchallenge.models.Message;
import com.justeattakeaway.programmingchallenge.service.GameService;
import com.justeattakeaway.programmingchallenge.service.MessagingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

	@Value("${player.name}")
	private String playerName;

	@Autowired
	private GameHelper gameHelper;

	@Autowired
	private MessagingService messagingService;

	private int[] adders = { 0, 1, -1 };

	@Override
	public String startGame(String mode, Integer number) {
		// validations
		if (invalidGameMode(mode)) {
			return "The mode parameter value is not valid. It can be either auto/interactive.";
		}

		if (isInteractiveGameMode(mode) && isNumberNotProvided(number)) {
			return String.join("","No. is mandatory when game mode is ", mode);
		}

		if (isGameAlreadyStarted()) {
			return "The game is already started.";
		}

		Integer startGameNumber = getNumber(mode, number);

		// init game
		return initiateHandShake(startGameNumber);

	}

	private boolean isInteractiveGameMode(String mode) {
		return GameMode.INTERACTIVE.name().equals(mode);
	}

	private boolean isNumberNotProvided(Integer number) {
		return Objects.isNull(number);
	}

	@Override
	public void playGame(Message message) {

		Integer number = message.getNumber();

		if (gameHelper.isInvalidNumber(number)) {
			log.error("Number has crossed min or max allowed threshold, terminating the game.");
			terminateGame(message.getGameId());
		} else {
			GameResult gameResult = playTurn(number);
			checkForNextMove(message.getGameId(), gameResult);
		}

	}

	@Override
	public void acknowledge(Acknowledgement acknowledgement) {
		GameStateManager.resetGameState(acknowledgement.getGameId());
	}

	@Override
	public void handShake(Message message) {
		GameStateManager.updateGameState(message.getGameId(), GameState.STARTED);
		playGame(message);
	}

	private boolean invalidGameMode(String mode) {
		return (Objects.isNull(mode)) || (!EnumUtils.isValidEnum(GameMode.class, mode));

	}

	private boolean isGameAlreadyStarted() {
		return !CollectionUtils.isEmpty(GameStateManager.getGameStateMap());
	}

	private Integer getNumber(String mode, Integer inputNumber) {
		Integer number = null;
		if (mode.equals(GameMode.AUTO.name())) {
			number = gameHelper.generateRandomNumber();
		} else {
			number = inputNumber;
		}
		return number;
	}

	private String initiateHandShake(Integer number) {
		log.info("{} has started the Game!", playerName);
		log.info("{} is initiating handshake with other player..", playerName);
		String gameId = GameHelper.generateGameId();
		GameStateManager.updateGameState(gameId, GameState.STARTED);
		messagingService.sendMessage(getMessage(gameId, number), gameId, MessageType.HAND_SHAKE.name());
		return "Game is started!";
	}

	private Message getMessage(String gameId, Integer number) {
		return Message.builder().gameId(gameId).number(number).build();
	}

	private void checkForNextMove(String gameId, GameResult gameResult) {
		if (failedMove(gameResult)) {
			Message message = getMessage(gameId, gameResult.getNumber());
			log.info("{} - Sending number to other player", playerName);
			messagingService.sendMessage(message, gameId, MessageType.PLAY_GAME.name());
		} else {
			terminateGame(gameId);
		}
	}

	private void terminateGame(String gameId) {
		log.info("{} - Terminating Game", playerName);
		GameStateManager.resetGameState(gameId);
		Acknowledgement acknowledgement = getAcknowledgement(gameId, GameState.ENDED);
		log.info("{} - Sending message to other player to reset game state", playerName);
		messagingService.sendMessage(acknowledgement, gameId, MessageType.ACK.name());
	}

	private Acknowledgement getAcknowledgement(String gameId, GameState gameState) {
		return Acknowledgement.builder().gameId(gameId).gameState(gameState).build();
	}

	private boolean failedMove(GameResult gameResult) {
		return !gameResult.getWin();
	}

	private GameResult playTurn(Integer number) {
		Integer originalNumber = number;
		boolean win = false;
		int winningAdder = adders[2];

		for (int i = 0; i < 3; i++) {
			number = originalNumber + adders[i];
			if (winningMove(number)) {
				win = true;
				winningAdder = adders[i];
				break;
			} else if (divisibleByThree(number)) {
				winningAdder = adders[i];
				break;
			}
		}

		log.info("Number:{} , Added:{}, Resulting Number :{}", originalNumber, winningAdder, getQuotient(number));
		if(win) {
			log.info("Congratulations, YOU WON THE GAME!");
		}
		
		return GameResultBuider.buildGameResult(win, getQuotient(number));

	}

	private boolean divisibleByThree(Integer number) {
		return ((number != 0) && ((number % 3) == 0));
	}

	private boolean winningMove(int number) {
		return (divisibleByThree(number) && (getQuotient(number) == 1));
	}

	private int getQuotient(int number) {
		return (number) / 3;
	}

}
