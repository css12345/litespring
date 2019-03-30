package org.litespring.context.support;

import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {
	public FileSystemXmlApplicationContext(String filePath) {
		super(filePath);
	}

	@Override
	protected Resource getResourceByPath(String filePath) {
		return new FileSystemResource(filePath);
	}

}
