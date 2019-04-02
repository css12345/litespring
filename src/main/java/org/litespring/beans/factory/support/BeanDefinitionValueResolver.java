package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
	
	private BeanFactory factory;

	public BeanDefinitionValueResolver(BeanFactory factory) {
		this.factory = factory;
	}

	public Object resolveValueIfNecessary(Object object) {
		if(object instanceof RuntimeBeanReference) {
			RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference)object;
			String beanName = runtimeBeanReference.getBeanName();
			return factory.getBean(beanName);
		}
		else if(object instanceof TypedStringValue) 
			return ((TypedStringValue)object).getValue();
		else 
			throw new RuntimeException("the value " + object + " has not implemented");
	}

}
