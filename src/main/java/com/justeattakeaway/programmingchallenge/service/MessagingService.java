package com.justeattakeaway.programmingchallenge.service;

public interface MessagingService {
		
	public <T> void sendMessage(T message, String gameId, String messageType);

}
