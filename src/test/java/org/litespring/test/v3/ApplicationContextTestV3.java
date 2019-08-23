package org.litespring.test.v3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v3.AccountDao;
import org.litespring.service.v3.ItemDao;
import org.litespring.service.v3.PetStoreService;

public class ApplicationContextTestV3 {
	@Test
	public void testGetBeanProperty() {
		ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v3.xml");
		PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

		assertNotNull(petStoreService.getAccountDao());
		assertNotNull(petStoreService.getItemDao());
		assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
		assertTrue(petStoreService.getItemDao() instanceof ItemDao);

		assertEquals(1, petStoreService.getVersion());
	}
}
