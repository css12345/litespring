package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * 将获取beanDefinition从BeanFactory中抽取出来，达到单一职责原则<br>
 * 同时方法registerBeanDefinition为XmlBeanDefinitionReader读取配置文件后注册BeanDefinition提供了接口<br>
 * 引入该接口的原因是降低XmlBeanDefinitionReader与BeanFactory之间的耦合，使XmlBeanDefinitionReader达到最少知识原则
 * 
 * @author cs
 *
 */
public interface BeanDefinationRegistry {
	BeanDefinition getBeanDefinition(String beanId);

	void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);
}
