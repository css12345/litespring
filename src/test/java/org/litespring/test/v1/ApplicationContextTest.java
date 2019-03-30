package org.litespring.test.v1;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTest {
	@Test
	public void testGetBean() throws IOException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v1.xml");
		PetStoreService service = (PetStoreService) applicationContext.getBean("petStore");
		assertNotNull(service);
	}

	@Test
	public void testGetBeanFromFileSytemContext() throws IOException {
		InputStream configInputStream = this.getClass().getClassLoader().getResourceAsStream("ResourceTest.properties");

		Properties properties = new Properties();
		properties.load(configInputStream);
		String filePath = properties.getProperty("filePath");
		
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filePath);
		PetStoreService service = (PetStoreService) applicationContext.getBean("petStore");
		assertNotNull(service);
	}
}
