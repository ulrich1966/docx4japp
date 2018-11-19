package de.juli.docx4j.service;

import java.nio.file.Path;

import de.juli.docx4j.service.model.Attribut;

public interface Create {
	public Path create(Path target) throws Exception;
	public void addAttrib(Attribut attibut);
	public void open() throws Exception;
}
