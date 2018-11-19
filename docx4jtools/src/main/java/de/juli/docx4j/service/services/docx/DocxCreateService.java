package de.juli.docx4j.service.services.docx;

import java.io.File;
import java.nio.file.Path;

import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import de.juli.docx4j.service.services.CreateService;

public class DocxCreateService implements CreateService {

	private Docx4JService docx4jService;

	public DocxCreateService(Path source) throws Exception {
		docx4jService = new Docx4JService(source);
	}

	@Override
	public void open() {
	}

	@Override
	public Path create(Path target) throws Exception {
		MainDocumentPart root = docx4jService.getRootDocPart(); 
		root.addStyledParagraphOfText("Title", "Hello World!");
		root.addParagraphOfText("Welcome To Baeldung");
		File exportFile = target.toFile();
		return docx4jService.save(exportFile);	
	}
}
