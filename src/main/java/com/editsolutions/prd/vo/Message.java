package com.editsolutions.prd.vo;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String preMessage;
	private String postMessage;

	public Message() {
	}

	public String getPreMessage() {
		return preMessage;
	}

	public void setPreMessage(String preMessage) {
		this.preMessage = preMessage;
	}

	public String getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}
	
	

}
