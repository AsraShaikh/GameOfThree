package com.justeattakeaway.programmingchallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justeattakeaway.programmingchallenge.models.Acknowledgement;
import com.justeattakeaway.programmingchallenge.models.Message;
import com.justeattakeaway.programmingchallenge.service.GameService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GameController {

	@Autowired
	private GameService gameService;

	@PostMapping("/start")
	public String startGame(@RequestParam(name = "mode", required = false, defaultValue = "AUTO") String mode,
			@RequestParam(name = "number", required = false) Integer number
	// , @RequestBody(required = false) GameRequest gameRequest
	) {
		log.info("\n");
		log.info("Starting Game, Mode : {}", mode);
		return gameService.startGame(mode, number);
	}

	@PostMapping(value = "/handshake")
	public void handShake(@RequestBody Message message) {
		log.info("\n");
		log.info("Received handshake request : {}", message);
		gameService.handShake(message);
	}

	@PostMapping(value = "/play")
	public void playGame(@RequestBody Message message) {
		log.info("\n");
		log.info("Received number from other player : {}", message);
		gameService.playGame(message);
	}

	@PostMapping(value = "/end")
	public void acknowledge(@RequestBody Acknowledgement acknowledgement) {
		log.info("\n");
		log.info("Received acknowledgement : {}", acknowledgement);
		gameService.acknowledge(acknowledgement);
	}

}
