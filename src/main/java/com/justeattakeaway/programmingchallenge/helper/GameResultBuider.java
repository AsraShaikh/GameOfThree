package com.justeattakeaway.programmingchallenge.helper;

import com.justeattakeaway.programmingchallenge.models.GameResult;

public class GameResultBuider {
	
	private GameResultBuider() {}
	
	public static GameResult buildGameResult(boolean win, Integer number) {
		return new GameResult(win, number);
	}

}
