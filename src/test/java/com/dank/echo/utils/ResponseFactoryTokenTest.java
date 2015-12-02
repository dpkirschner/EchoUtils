package com.dank.echo.utils;

import org.junit.Test;

public class ResponseFactoryTokenTest {
	private static String SPEECH_TEXT = "SPEECH TEXT";
	private static String REPROMPT_TEXT = "REPROMPT TEXT";
	private static String CARD_TITLE = "CARD TITLE";
	private static String CARD_CONTENT = "CARD TEXT";
	
    @Test(expected=IllegalArgumentException.class)
    public void missingOutputSpeech() {
    	ResponseFactoryToken token = ResponseFactoryToken.builder()
        		.repromptSpeech(REPROMPT_TEXT)
        		.build();
        token.validate();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void cardContentWithNoCardTitle() {
    	ResponseFactoryToken token = ResponseFactoryToken.builder()
        		.outputSpeech(SPEECH_TEXT)
        		.cardContent(CARD_CONTENT)
        		.build();
        token.validate();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void cardTitleWithNoCardContent() {
    	ResponseFactoryToken token = ResponseFactoryToken.builder()
        		.outputSpeech(SPEECH_TEXT)
        		.cardTitle(CARD_TITLE)
        		.build();
        token.validate();
    }
}
