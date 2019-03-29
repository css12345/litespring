package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

//BeanFactory的getBean()方法在通过反射创建对象时出现异常就抛出这个异常
public class BeanCreationException extends BeansException {
	
	public BeanCreationException(String message) {
		super(message);
	}

	public BeanCreationException(String message, Throwable throwable) {
		super(message, throwable);
	}

	private static final long serialVersionUID = 1L;

}
