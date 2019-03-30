package org.litespring.test.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {
	DefaultBeanFactory beanFactory = null;
	XmlBeanDefinitionReader reader = null;

	@Before
	public void setUp() {
		beanFactory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(beanFactory);
	}

	@Test
	public void testGetBean() {

		reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));

		BeanDefinition bd = beanFactory.getBeanDefinition("petStore");

		assertTrue(bd.isSingleton());
		assertFalse(bd.isPrototype());
		assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

		// 判断class是否是配置文件中定义的
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

		// 从Factory中获取bean对象
		PetStoreService petStoreService = (PetStoreService) beanFactory.getBean("petStore");

		assertNotNull(petStoreService);

		PetStoreService petStoreService1 = (PetStoreService) beanFactory.getBean("petStore");
		assertTrue(petStoreService.equals(petStoreService1));
	}

	@Test
	public void testGetBeanWithPrototype() {
		reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));

		BeanDefinition bd = beanFactory.getBeanDefinition("petStore1");

		assertFalse(bd.isSingleton());
		assertTrue(bd.isPrototype());
		assertEquals(BeanDefinition.SCOPE_PROTOTYPE, bd.getScope());

		PetStoreService petStoreService1 = (PetStoreService) beanFactory.getBean("petStore1");
		assertNotNull(petStoreService1);
		
		PetStoreService petStoreService2 = (PetStoreService) beanFactory.getBean("petStore1");
		assertFalse(petStoreService1.equals(petStoreService2));
	}

	// 在配置文件中定义一个不存在的类，检查是否抛出BeanCreationException
	@Test
	public void testInvalidBeans() {
		reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));

		try {
			beanFactory.getBean("invalidBean");
		} catch (BeanCreationException e) {
			return;
		}

		fail("expect BeanCreationException");
	}

	// 通过一个不存在的文件检查是否抛出BeanDefinitionStoreException
	@Test
	public void testInvalidXml() {
		try {
			reader.loadBeanDefinition(new ClassPathResource("xxx.xml"));
		} catch (BeanDefinitionStoreException e) {
			return;
		}

		fail("expect BeanDefinitionStoreException");
	}

}
