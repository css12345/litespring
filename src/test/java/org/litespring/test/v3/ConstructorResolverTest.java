package org.litespring.test.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.ConstructorResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v3.AccountDao;
import org.litespring.service.v3.ItemDao;
import org.litespring.service.v3.PetStoreService;
import org.litespring.service.v3.PetStoreService1;

public class ConstructorResolverTest {

	@Test
	public void testAutowireConstructor() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("petstore-v3.xml"));
		
		BeanDefinition petStore = beanFactory.getBeanDefinition("petStore");
		ConstructorResolver resolver = new ConstructorResolver(beanFactory);
		PetStoreService petStoreService = (PetStoreService) resolver.autowireConstructor(petStore);
		assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
		assertTrue(petStoreService.getItemDao() instanceof ItemDao);
		assertEquals(1, petStoreService.getVersion());
	}
	
	@Test
	public void testAutowireMultiMatchConstructor() {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinition(new ClassPathResource("petstore-v3.xml"));
		
		BeanDefinition petStore = beanFactory.getBeanDefinition("petStore1");
		ConstructorResolver resolver = new ConstructorResolver(beanFactory);
		PetStoreService1 petStoreService = (PetStoreService1) resolver.autowireConstructor(petStore);
		assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
		assertTrue(petStoreService.getItemDao() instanceof ItemDao);
		assertEquals("1", petStoreService.getVersion());
	}
} 
