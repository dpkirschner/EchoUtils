package com.dank.echo.utils;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class ResponseFactory {
	
	private ResponseFactory(){};

	public static Reprompt createReprompt(PlainTextOutputSpeech speechOutput) {
		Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speechOutput);
		return reprompt;
	}
	
	public static PlainTextOutputSpeech createSpeech(String speechText) {
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
		return speech;
	}

	public static SimpleCard createCard(String title, String speechText) {
        SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(speechText);
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
     * @return SpeechletResponse the speechlet response
     */
    public static SpeechletResponse tellResponse(String speechText, String cardTitle, String cardText) {
    	PlainTextOutputSpeech outputSpeech = createSpeech(speechText);
    	SimpleCard card = createCard(cardTitle, cardText);
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
     * @return SpeechletResponse the speechlet response
     */
    public static SpeechletResponse closeResponse(String speechText, String cardTitle) {
    	PlainTextOutputSpeech outputSpeech = createSpeech(speechText);
    	SimpleCard card = createCard(cardTitle, speechText);
        
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
    	PlainTextOutputSpeech outputSpeech = createSpeech(speechText);
    	Reprompt reprompt = createReprompt(createSpeech(repromptText));
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt, createCard(cardTitle, speechText));
    }
}
