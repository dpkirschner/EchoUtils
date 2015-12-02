package com.dank.echo.utils;

import lombok.Builder;
import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

@Builder
@Getter
public class ResponseFactoryToken {

	private boolean shouldEndSession;
	private String outputSpeech;
	private String repromptSpeech;
	private String cardTitle;
	private String cardContent;
	
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
