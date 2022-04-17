package com.justeattakeaway.programmingchallenge.models;

import com.justeattakeaway.programmingchallenge.GameState;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Acknowledgement {
	
	private String gameId;
	
	private GameState gameState;
}
