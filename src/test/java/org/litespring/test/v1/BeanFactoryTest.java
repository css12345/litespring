package org.litespring.test.v1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {

	@Test
	public void testGetBean() {
		//根据配置文件创建BeanFactory
		BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");
		//从BeanFactory中获取Bean定义
		BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
		
		//判断class是否是配置文件中定义的
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		
		//从Factory中获取bean对象
		PetStoreService petStoreService = (PetStoreService)beanFactory.getBean("petStore");
		
		assertNotNull(petStoreService);
	}
	
	//在配置文件中定义一个不存在的类，检查是否抛出BeanCreationException
	@Test
	public void testInvalidBeans() {
		BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");
		
		try {
			beanFactory.getBean("invalidBean");
		} catch (BeanCreationException e) {
			return;
		}
		
		fail("expect BeanCreationException");
	}
	
	//通过一个不存在的文件检查是否抛出BeanDefinitionStoreException
	@Test
	public void testInvalidXml() {
		try {
			BeanFactory beanFactory = new DefaultBeanFactory("xxx.xml");
		} catch (BeanDefinitionStoreException e) {
			return;
		}
		
		fail("expect BeanDefinitionStoreException");
	}

}
