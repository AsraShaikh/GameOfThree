package com.justeattakeaway.programmingchallenge.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.justeattakeaway.programmingchallenge.GameState;

public class GameStateManager {
	
	private GameStateManager() {}

	private static Map<String, GameState> gameStateMap;

	public static Map<String, GameState> getGameStateMap() {
		return gameStateMap;
	}

	public static void updateGameState(String gameId, GameState gameState) {
		if (Objects.isNull(gameStateMap)) {
			gameStateMap = new HashMap<>();
			gameStateMap.put(gameId, gameState);
		}
	}

	public static void resetGameState(String gameId) {
		if (Objects.nonNull(gameStateMap)) {
			gameStateMap.remove(gameId);
		}
	}

}
