package org.litespring.beans;

public class BeansException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BeansException(String message) {
		super(message);
	}
	
	public BeansException(String message,Throwable throwable) {
		super(message, throwable);
	}

}
