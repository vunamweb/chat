package com.vunam.chat.model;

public class Message {
	private String message;
	private String urlImage;

	public Message(String message, String urlImage) {
		this.message = message;
		this.urlImage = urlImage;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
}
