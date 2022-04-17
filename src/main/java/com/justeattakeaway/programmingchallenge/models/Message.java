package com.justeattakeaway.programmingchallenge.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Message {

	private Integer number;
	
	private String gameId;
	
}
