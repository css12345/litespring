package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

//BeanFactory从配置文件读取出现异常时会抛出这个异常
public class BeanDefinitionStoreException extends BeansException {
	
	public BeanDefinitionStoreException(String message) {
		super(message);
	}
	
	public BeanDefinitionStoreException(String message, Throwable throwable) {
		super(message, throwable);
	}

	private static final long serialVersionUID = 1L;

}
