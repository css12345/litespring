package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionValueResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v2.AccountDao;

public class BeanDefinitionValueResolverTest {

	private DefaultBeanFactory factory;
	private XmlBeanDefinitionReader reader;
	private Resource resource = new ClassPathResource("petstore-v2.xml");
	private BeanDefinitionValueResolver resolver;

	@Before
	public void setUp() {
		factory = new DefaultBeanFactory();
		reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinition(resource);

		resolver = new BeanDefinitionValueResolver(factory);
	}

	// 测试获取convertedValues
	@Test
	public void testResolveRuntimeBeanReference() {
		RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference("accountDao");
		Object object = resolver.resolveValueIfNecessary(runtimeBeanReference);

		assertNotNull(object);
		assertTrue(object instanceof AccountDao);
	}

	@Test
	public void testResolveTypedOfString() {
		TypedStringValue valueHolder = new TypedStringValue("test");
		Object object = resolver.resolveValueIfNecessary(valueHolder);

		assertEquals("test", object);
		assertNotNull(object);
	}
}
