package com.dank.echo.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class ResponseFactoryTest {
	private static String SPEECH_TEXT = "SPEECH TEXT";
	private static String REPROMPT_TEXT = "REPROMPT TEXT";
	private static String CARD_TITLE = "CARD TITLE";
	private static String CARD_CONTENT = "CARD TEXT";
	private static String EMPTY_TEXT = "";
	
	@Before
	public void setup() {
		
	}

    @Test
    public void reprompt() {
    	PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    	Reprompt reprompt = ResponseFactory.createReprompt(outputSpeech);

        assertEquals(outputSpeech, reprompt.getOutputSpeech());
    }
    
    @Test(expected=NullPointerException.class)
    public void repromptNullSpeech() {
    	ResponseFactory.createReprompt(null);
    }
    
    @Test
    public void createSpeech() {
    	PlainTextOutputSpeech outputSpeech = ResponseFactory.createSpeech(SPEECH_TEXT);
        
        assertEquals(SPEECH_TEXT, outputSpeech.getText());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void createSpeechEmptyText() {
    	ResponseFactory.createSpeech(EMPTY_TEXT);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void createSpeechNulltext() {
    	ResponseFactory.createSpeech(EMPTY_TEXT);
    }
    
    @Test
    public void createCard() {
    	SimpleCard card = ResponseFactory.createCard(CARD_TITLE, CARD_CONTENT);
    	
    	assertEquals(CARD_TITLE, card.getTitle());
        assertEquals(CARD_CONTENT, card.getContent());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void createCardEmptyTitle() {
    	ResponseFactory.createCard(EMPTY_TEXT, CARD_CONTENT);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void createSpeechEmptyContent() {
    	ResponseFactory.createCard(CARD_TITLE, EMPTY_TEXT);
    }
    
    @Test
    public void createTellReponse() {
    	SpeechletResponse response = ResponseFactory.tellResponse(SPEECH_TEXT, CARD_TITLE, CARD_CONTENT);
    	
    	assertTrue(response.getShouldEndSession());
    	assertNull(response.getReprompt());
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNotNull(response.getCard());
    	assertEquals(CARD_TITLE, ((SimpleCard)response.getCard()).getTitle());
        assertEquals(CARD_CONTENT, ((SimpleCard)response.getCard()).getContent());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void tellResponseEmptySpeechText() {
    	ResponseFactory.tellResponse(EMPTY_TEXT, CARD_TITLE, CARD_CONTENT);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void tellResponseEmptyCardTitle() {
    	ResponseFactory.tellResponse(SPEECH_TEXT, EMPTY_TEXT, CARD_CONTENT);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void tellResponseEmptyCardContent() {
    	ResponseFactory.tellResponse(SPEECH_TEXT, CARD_TITLE, EMPTY_TEXT);
    }
    
    @Test
    public void createCloseReponse() {
    	SpeechletResponse response = ResponseFactory.closeResponse(SPEECH_TEXT, CARD_TITLE, CARD_CONTENT);
    	
    	assertTrue(response.getShouldEndSession());
    	assertNull(response.getReprompt());
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNotNull(response.getCard());
    	assertEquals(CARD_TITLE, ((SimpleCard)response.getCard()).getTitle());
        assertEquals(CARD_CONTENT, ((SimpleCard)response.getCard()).getContent());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void closeResponseEmptySpeechText() {
    	ResponseFactory.closeResponse(EMPTY_TEXT, CARD_TITLE, CARD_CONTENT);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void closeResponseEmptyCardTitle() {
    	ResponseFactory.closeResponse(SPEECH_TEXT, EMPTY_TEXT, CARD_CONTENT);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void closeResponseEmptyCardContent() {
    	ResponseFactory.closeResponse(SPEECH_TEXT, CARD_TITLE, EMPTY_TEXT);
    }
    
    @Test
    public void createAskReponse() {
    	SpeechletResponse response = ResponseFactory.askResponse(SPEECH_TEXT);
    	
    	assertFalse(response.getShouldEndSession());
    	assertNotNull(response.getReprompt());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)((Reprompt)response.getReprompt()).getOutputSpeech()).getText());
    	
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNull(response.getCard());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void askResponseEmptySpeechText() {
    	ResponseFactory.askResponse(EMPTY_TEXT);
    }
    
    @Test
    public void createAskReponseWithRepompt() {
    	SpeechletResponse response = ResponseFactory.askResponse(SPEECH_TEXT, REPROMPT_TEXT);
    	
    	assertFalse(response.getShouldEndSession());
    	assertNotNull(response.getReprompt());
    	assertEquals(REPROMPT_TEXT, ((PlainTextOutputSpeech)((Reprompt)response.getReprompt()).getOutputSpeech()).getText());
    	
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNull(response.getCard());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void askResponseWithRepromptEmptySpeechText() {
    	ResponseFactory.askResponse(EMPTY_TEXT, REPROMPT_TEXT);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void askResponseWithRepromptEmptyRepromptText() {
    	ResponseFactory.askResponse(SPEECH_TEXT, EMPTY_TEXT);
    }
    
    
    @Test
    public void createAskReponseWithRepomptAndCard() {
    	SpeechletResponse response = ResponseFactory.askResponse(SPEECH_TEXT, REPROMPT_TEXT, CARD_TITLE);
    	
    	assertFalse(response.getShouldEndSession());
    	assertNotNull(response.getReprompt());
    	assertEquals(REPROMPT_TEXT, ((PlainTextOutputSpeech)((Reprompt)response.getReprompt()).getOutputSpeech()).getText());
    	
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNotNull(response.getCard());
    	assertEquals(CARD_TITLE, ((SimpleCard)response.getCard()).getTitle());
        assertEquals(SPEECH_TEXT, ((SimpleCard)response.getCard()).getContent());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void askResponseWithRepromptAndCardEmptySpeechText() {
    	ResponseFactory.askResponse(EMPTY_TEXT, REPROMPT_TEXT, CARD_TITLE);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void askResponseWithRepromptAndCardEmptyRepromptText() {
    	ResponseFactory.askResponse(SPEECH_TEXT, EMPTY_TEXT, CARD_TITLE);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void askResponseWithRepromptAndCardEmptyCardTitle() {
    	ResponseFactory.askResponse(SPEECH_TEXT, REPROMPT_TEXT, EMPTY_TEXT);
    }
}
