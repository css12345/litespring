package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

//BeanFactory的getBean()方法在通过反射创建对象时出现异常就抛出这个异常
public class BeanCreationException extends BeansException {

	private String beanName;

	public BeanCreationException(String message) {
		super(message);
	}

	public BeanCreationException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public BeanCreationException(String beanName, String msg) {
		super("Error creating bean with name '" + beanName + "': " + msg);
		this.beanName = beanName;
	}

	public BeanCreationException(String beanName, String msg, Throwable cause) {
		this(beanName, msg);
		initCause(cause);
	}

	public String getBeanName() {
		return this.beanName;
	}

	private static final long serialVersionUID = 1L;

}
