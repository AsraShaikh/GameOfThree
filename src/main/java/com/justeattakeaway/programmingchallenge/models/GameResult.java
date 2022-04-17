package com.justeattakeaway.programmingchallenge.models;

import lombok.Getter;

@Getter
public class GameResult {
	
	private Boolean win;
	private Integer number;
	
	public GameResult(boolean win, Integer number) {
		this.win = win;
		this.number = number;
	}

	
}
