package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v2.AccountDao;
import org.litespring.service.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;

public class ApplicationContextTestV2 {
	@Test
	public void testGetBeanProperty() {
		ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v2.xml");
		PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");
		
		assertNotNull(petStoreService.getAccountDao());
		assertNotNull(petStoreService.getItemDao());
		assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
		assertTrue(petStoreService.getItemDao() instanceof ItemDao);
		
		assertEquals("chesen", petStoreService.getOwner());
		assertEquals(2, petStoreService.getVersion());
	}
}
