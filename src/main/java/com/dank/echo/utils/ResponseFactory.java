package com.dank.echo.utils;

import lombok.NonNull;

import org.apache.commons.lang3.StringUtils;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.google.common.base.Preconditions;

public class ResponseFactory {
	/*TODO: Refactor to use a token POJO as input instead of directly using strings
	 * This will reduce the amount of repetition and also make the function much more clear
	 */
	
	private ResponseFactory(){};

	public static Reprompt createReprompt(@NonNull PlainTextOutputSpeech speechOutput) {	
		Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speechOutput);
		return reprompt;
	}
	
	public static PlainTextOutputSpeech createSpeech(String speechText) {
		validateStrings(speechText);
		
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
		return speech;
	}

	public static SimpleCard createCard(String title, String content) {
		validateStrings(title, content);
		
        SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(content);
		return card;
	}

    /**
     * Wrapper for creating the Tell response. The OutputSpeech and {@link Card} objects are
     * created from the input strings.
     *
     * @param speechText
     *            the output to be spoken
     * @param cardTitle
     *            the title of the card to be returned
     * @param cardContent
     * 			  the content the card should contain
     * @return SpeechletResponse the speechlet response
     */
    public static SpeechletResponse tellResponse(String speechText, String cardTitle, String cardContent) {
    	validateStrings(speechText, cardTitle, cardContent);
    	
    	PlainTextOutputSpeech outputSpeech = createSpeech(speechText);
    	SimpleCard card = createCard(cardTitle, cardContent);
        return SpeechletResponse.newTellResponse(outputSpeech, card);
    }
    
    /**
     * Wrapper for creating the Tell response. The OutputSpeech and {@link Card} objects are
     * created from the input strings.
     *
     * @param speechText
     *            the output to be spoken
     * @param cardTitle
     *            the title of the card to be returned
     * @param cardContent
     * 			  the content the card should contain
     * @return SpeechletResponse the speechlet response
     */
    public static SpeechletResponse closeResponse(String speechText, String cardTitle, String cardContent) {
    	validateStrings(speechText, cardTitle);
    	
    	PlainTextOutputSpeech outputSpeech = createSpeech(speechText);
    	SimpleCard card = createCard(cardTitle, cardContent);
        
    	SpeechletResponse response = SpeechletResponse.newTellResponse(outputSpeech, card);
        response.setShouldEndSession(true);
        return response;
    }
    
    /**
     * Wrapper for creating the Ask response. The OutputSpeech and {@link Reprompt} objects are
     * created from the same input string.
     *
     * @param speechText
     *            the output to be spoken as well as the reprompt if the user doesn't reply or is misunderstood
     * @return SpeechletResponse the speechlet response
     */
    public static SpeechletResponse askResponse(String speechText) {
    	validateStrings(speechText);
    	
    	return askResponse(speechText, speechText);
    }
	
    /**
     * Wrapper for creating the Ask response. The OutputSpeech and {@link Reprompt} objects are
     * created from the input strings.
     *
     * @param speechText
     *            the output to be spoken
     * @param repromptText
     *            the reprompt for if the user doesn't reply or is misunderstood.
     * @return SpeechletResponse the speechlet response
     */
    public static SpeechletResponse askResponse(String speechText, String repromptText) {
    	validateStrings(speechText, repromptText);
    	
    	PlainTextOutputSpeech outputSpeech = createSpeech(speechText);
    	Reprompt reprompt = createReprompt(createSpeech(repromptText));
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }
    
    /**
     * Wrapper for creating the Ask response. The OutputSpeech, {@link Reprompt}, and {@link Card} objects are
     * created from the input strings.
     *
     * @param speechText
     *            the output to be spoken
     * @param repromptText
     *            the reprompt for if the user doesn't reply or is misunderstood.
     * @return SpeechletResponse the speechlet response
     */
    public static SpeechletResponse askResponse(String speechText, String repromptText, String cardTitle) {
    	validateStrings(speechText, repromptText, cardTitle);
    	
    	PlainTextOutputSpeech outputSpeech = createSpeech(speechText);
    	Reprompt reprompt = createReprompt(createSpeech(repromptText));
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt, createCard(cardTitle, speechText));
    }
	
	/**
	 * Utility method that consolidates the argument checking for each string passed
	 */
	private static void validateStrings(String... texts) {
		for(String text : texts) {
			Preconditions.checkArgument(!StringUtils.isEmpty(text), "text must not be empty");
		}
	}
}
