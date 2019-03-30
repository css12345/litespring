package org.litespring.test.v1;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class ResourceTest {

	@Test
	public void testClassPathResource() throws IOException {
		Resource resource = new ClassPathResource("petstore-v1.xml");
		InputStream inputStream = resource.getInputStream();
		assertNotNull(inputStream);
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFileSystemResource() throws FileNotFoundException, IOException {
		InputStream configInputStream = this.getClass().getClassLoader().getResourceAsStream("ResourceTest.properties");
		
		Properties properties = new Properties();
		properties.load(configInputStream);
		String filePath = properties.getProperty("filePath");
		
		Resource resource = new FileSystemResource(filePath);
		InputStream inputStream = resource.getInputStream();
		assertNotNull(inputStream);
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
