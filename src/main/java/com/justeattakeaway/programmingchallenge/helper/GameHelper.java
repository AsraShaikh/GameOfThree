package com.justeattakeaway.programmingchallenge.helper;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameHelper {

	@Value("${min.no}")
	private int minNo;

	@Value("${max.no}")
	private int maxNo;

	public int generateRandomNumber() {
		int randomNo = ThreadLocalRandom.current().nextInt(minNo, maxNo + 1);
		log.info("Random no. generated is : {}", randomNo);
		return randomNo;
	}

	public boolean isInvalidNumber(Integer number) {
		return (number <= minNo) || (number >= maxNo);
	}

	// This method generates unique gameId
	public static String generateGameId() {
		return String.valueOf(System.currentTimeMillis());
	}

}
