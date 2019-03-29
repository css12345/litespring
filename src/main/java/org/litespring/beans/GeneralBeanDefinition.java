package org.litespring.beans;

public class GeneralBeanDefinition implements BeanDefinition {
	
	String id;
	String beanClassName;

	public GeneralBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
	public String getBeanClassName() {
		return beanClassName;
	}

}
