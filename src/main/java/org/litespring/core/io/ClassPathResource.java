package org.litespring.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.util.Assert;
import org.litespring.util.ClassUtils;

public class ClassPathResource implements Resource {

	private ClassLoader classLoader;
	private String path;

	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		Assert.notNull(path, "Path must not be null");

		this.path = path;
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream inputStream = classLoader.getResourceAsStream(path);
		
		if (inputStream == null) 
			throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
		return inputStream;
	}

	@Override
	public String getDescription() {
		return path;
	}

}
