package org.litespring.context.support;

import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class FileSystemApplicationContext extends AbstractApplicationContext {
	public FileSystemApplicationContext(String filePath) {
		super(filePath);
	}

	@Override
	protected Resource getResourceByPath(String filePath) {
		return new FileSystemResource(filePath);
	}

}
