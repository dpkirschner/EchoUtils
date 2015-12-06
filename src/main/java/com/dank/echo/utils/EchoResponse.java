package com.dank.echo.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.google.common.base.Preconditions;

/**
 * Factory class which consolidates the logic required to build the Echo {@link SpeechletResponse}s needed.
 * Would strongly prefer to use @Builder to have lombok generate the EchoResponseFactory, but there isn't
 * AFAIK a way to override the default build() method provided to contain the custom logic here.
 */
public class EchoResponse {
	private EchoResponse(){};

	private static SimpleCard createCard(String cardTitle, String cardContent) {
		if(StringUtils.isEmpty(cardTitle) || StringUtils.isEmpty(cardContent)) {
			return null;
		}
		
        SimpleCard card = new SimpleCard();
        card.setTitle(cardTitle);
        card.setContent(cardContent);
		return card;
	}
	
	private static Reprompt createReprompt(String repromptSpeech) {
		Preconditions.checkArgument(!StringUtils.isEmpty(repromptSpeech), "Reprompt Speech must not be empty");
		
		PlainTextOutputSpeech speechOutput = new PlainTextOutputSpeech();
		speechOutput.setText(repromptSpeech);
        
		Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speechOutput);
		return reprompt;
	}
	
	private static PlainTextOutputSpeech createSpeech(String outputSpeech) {
		Preconditions.checkArgument(!StringUtils.isEmpty(outputSpeech), "Output Speech must not be empty");
		
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(outputSpeech);
		return speech;
	}
	
	public static EchoResponseFactory factory() {
		return new EchoResponse.EchoResponseFactory();
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	public static class EchoResponseFactory {
		private boolean shouldEndSession;
		private String outputSpeech;
		private String repromptSpeech;
		private String cardTitle;
		private String cardContent;
		
		public EchoResponseFactory shouldEndSession(boolean shouldEndSession) {
			this.shouldEndSession = shouldEndSession;
			return this;
		}
		
		public EchoResponseFactory outputSpeech(String outputSpeech) {
			this.outputSpeech = outputSpeech;
			return this;
		}
		
		public EchoResponseFactory repromptSpeech(String repromptSpeech) {
			this.repromptSpeech = repromptSpeech;
			return this;
		}
		
		public EchoResponseFactory cardTitle(String cardTitle) {
			this.cardTitle = cardTitle;
			return this;
		}
		
		public EchoResponseFactory cardContent(String cardContent) {
			this.cardContent = cardContent;
			return this;
		}
		
		public SpeechletResponse create() {
			this.validate();
			SimpleCard card = createCard(cardTitle, cardContent);
			SpeechletResponse response;
			if(StringUtils.isEmpty(repromptSpeech)) {
				response = (card == null) 
						? SpeechletResponse.newTellResponse(createSpeech(outputSpeech)) 
						: SpeechletResponse.newTellResponse(createSpeech(outputSpeech), card);
				response.setShouldEndSession(shouldEndSession);
			} else {
				response = (card == null)
						? SpeechletResponse.newAskResponse(createSpeech(outputSpeech), createReprompt(repromptSpeech))
						: SpeechletResponse.newAskResponse(createSpeech(outputSpeech), createReprompt(repromptSpeech), card);
			}
			return response;
		}
		
		/**
		 * Utility method that consolidates the argument checking for each string passed
		 */
		public void validate() {
			Preconditions.checkArgument(!StringUtils.isEmpty(outputSpeech), "outputSpeech must not be empty");
			
			if(StringUtils.isEmpty(cardTitle)) {
				Preconditions.checkArgument(StringUtils.isEmpty(cardContent), "cardContent must be empty if cardTitle is not provided");
			} else {
				Preconditions.checkArgument(!StringUtils.isEmpty(cardContent), "cardContent can not be empty if cardTitle is provided");
			}
		}
	}
}
