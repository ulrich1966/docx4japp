package de.juli.docx4j.service.services;

import java.nio.file.Path;

public interface CreateService extends Service {
	public Path create(Path target) throws Exception;
}
