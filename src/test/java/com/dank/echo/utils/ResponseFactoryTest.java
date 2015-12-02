package com.dank.echo.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
	
    @Test
    public void withCloseSession() {
    	ResponseFactoryToken token = ResponseFactoryToken.builder()
        		.outputSpeech(SPEECH_TEXT)
        		.shouldEndSession(true)
        		.build();
        SpeechletResponse response = ResponseFactory.from(token);
    	
    	assertTrue(response.getShouldEndSession());
    	assertNull(response.getReprompt());
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNull(response.getCard());
    }
    
    @Test
    public void withRepromptAndCard() {
    	ResponseFactoryToken token = ResponseFactoryToken.builder()
    		.outputSpeech(SPEECH_TEXT)
    		.repromptSpeech(REPROMPT_TEXT)
    		.cardTitle(CARD_TITLE)
        	.cardContent(CARD_CONTENT)
    		.build();
    	SpeechletResponse response = ResponseFactory.from(token);
    	
    	assertFalse(response.getShouldEndSession());
    	assertNotNull(response.getReprompt());
    	assertEquals(REPROMPT_TEXT, ((PlainTextOutputSpeech)((Reprompt)response.getReprompt()).getOutputSpeech()).getText());
    	
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNotNull(response.getCard());
    	assertEquals(CARD_TITLE, ((SimpleCard)response.getCard()).getTitle());
        assertEquals(CARD_CONTENT, ((SimpleCard)response.getCard()).getContent());
    }
    
    @Test
    public void withRepompt() {
    	ResponseFactoryToken token = ResponseFactoryToken.builder()
        		.outputSpeech(SPEECH_TEXT)
        		.repromptSpeech(REPROMPT_TEXT)
        		.build();
        SpeechletResponse response = ResponseFactory.from(token);
    	
    	assertFalse(response.getShouldEndSession());
    	assertNotNull(response.getReprompt());
    	assertEquals(REPROMPT_TEXT, ((PlainTextOutputSpeech)((Reprompt)response.getReprompt()).getOutputSpeech()).getText());
    	
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNull(response.getCard());
    }
    
    @Test
    public void withCard() {
    	ResponseFactoryToken token = ResponseFactoryToken.builder()
        		.outputSpeech(SPEECH_TEXT)
        		.cardTitle(CARD_TITLE)
        		.cardContent(CARD_CONTENT)
        		.build();
    	SpeechletResponse response = ResponseFactory.from(token);
    	
    	assertFalse(response.getShouldEndSession());
    	assertNull(response.getReprompt());
    	
    	assertNotNull(response.getOutputSpeech());
    	assertEquals(SPEECH_TEXT, ((PlainTextOutputSpeech)response.getOutputSpeech()).getText());
    	
    	assertNotNull(response.getCard());
    	assertEquals(CARD_TITLE, ((SimpleCard)response.getCard()).getTitle());
        assertEquals(CARD_CONTENT, ((SimpleCard)response.getCard()).getContent());
    }
}
