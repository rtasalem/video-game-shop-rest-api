package com.rtasalem.videoGameShopApi.exception;

public class VideoGameTitleExistsException extends RuntimeException {

	private static final long serialVersionUID = 595528910535481833L;

	public VideoGameTitleExistsException(String message) {
		super(message);
	}
	
}
