package com.justeattakeaway.programmingchallenge.service;

import com.justeattakeaway.programmingchallenge.models.Acknowledgement;
import com.justeattakeaway.programmingchallenge.models.Message;

public interface GameService {
	
	public String startGame(String mode, Integer number);

	public void playGame(Message message);

	public void acknowledge(Acknowledgement acknowledgement);

	public void handShake(Message message);

}
