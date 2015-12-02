package com.dank.echo.utils;

import org.apache.commons.lang3.StringUtils;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.google.common.base.Preconditions;

public class ResponseFactory {
	private ResponseFactory(){};

	private static Reprompt createReprompt(ResponseFactoryToken token) {
		Preconditions.checkArgument(!StringUtils.isEmpty(token.getRepromptSpeech()), "Reprompt Speech must not be empty");
		
		PlainTextOutputSpeech speechOutput = new PlainTextOutputSpeech();
		speechOutput.setText(token.getRepromptSpeech());
        
		Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speechOutput);
		return reprompt;
	}
	
	private static PlainTextOutputSpeech createSpeech(ResponseFactoryToken token) {
		Preconditions.checkArgument(!StringUtils.isEmpty(token.getOutputSpeech()), "Output Speech must not be empty");
		
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(token.getOutputSpeech());
		return speech;
	}

	private static SimpleCard createCard(ResponseFactoryToken token) {
		if(StringUtils.isEmpty(token.getCardTitle()) || StringUtils.isEmpty(token.getCardContent())) {
			return null;
		}
		
        SimpleCard card = new SimpleCard();
        card.setTitle(token.getCardTitle());
        card.setContent(token.getCardContent());
		return card;
	}
	
	public static SpeechletResponse from(ResponseFactoryToken token) {
		token.validate();
		SimpleCard card = createCard(token);
		SpeechletResponse response;
		if(StringUtils.isEmpty(token.getRepromptSpeech())) {
			response = (card == null) 
					? SpeechletResponse.newTellResponse(createSpeech(token)) 
					: SpeechletResponse.newTellResponse(createSpeech(token), card);
			response.setShouldEndSession(token.isShouldEndSession());
		} else {
			response = (card == null)
					? SpeechletResponse.newAskResponse(createSpeech(token), createReprompt(token))
					: SpeechletResponse.newAskResponse(createSpeech(token), createReprompt(token), card);
		}
		return response;
	}
}
