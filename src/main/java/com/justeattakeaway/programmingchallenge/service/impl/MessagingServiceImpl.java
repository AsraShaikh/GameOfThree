package com.justeattakeaway.programmingchallenge.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.justeattakeaway.programmingchallenge.MessageType;
import com.justeattakeaway.programmingchallenge.helper.GameStateManager;
import com.justeattakeaway.programmingchallenge.service.MessagingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessagingServiceImpl implements MessagingService {

	@Value("${message.delivery.base.path}")
	private String msgDeliveryBasePath;

	@Value("${play.turn.api.path}")
	private String playTurnApiPath;

	@Value("${end.game.api.path}")
	private String endGameApiPath;

	@Value("${handshake.api.path}")
	private String initHandShakeApiPath;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public <T> void sendMessage(T message, String gameId, String messageType) {
		CompletableFuture.runAsync(() -> callOtherPlayer(getPath(messageType), message, gameId));
	}

	private String getPath(String messageType) {
		String apiPath = null;
		if (messageType.equals(MessageType.HAND_SHAKE.name())) {
			apiPath = initHandShakeApiPath;
		} else if (messageType.equals(MessageType.PLAY_GAME.name())) {
			apiPath = playTurnApiPath;
		} else {
			apiPath = endGameApiPath;
		}
		return String.join("", msgDeliveryBasePath, apiPath);
	}

	private <T> void callOtherPlayer(String messageDeliveryPath, T message, String gameId) {
		try {
			log.info("Request Url - {}", messageDeliveryPath);
			log.info("Request Message - {}", message);

			HttpEntity<T> request = new HttpEntity<>(message, new HttpHeaders());
			restTemplate.postForEntity(messageDeliveryPath, request, String.class);

		} catch (Exception exception) {
			log.error("Other player is not available, the game will be terminated.");
			GameStateManager.resetGameState(gameId);
		}
	}

}
