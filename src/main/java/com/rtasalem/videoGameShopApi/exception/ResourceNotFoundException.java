package com.rtasalem.videoGameShopApi.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8857922258747259845L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
