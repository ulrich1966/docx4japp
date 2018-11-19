package de.juli.docx4j.service.services.docx;

import java.io.File;
import java.nio.file.Path;

import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import de.juli.docx4j.service.Create;
import de.juli.docx4j.service.Service;
import de.juli.docx4j.service.model.Attribut;

public class DocxCreateService extends Service implements Create{

	private Path target;

	public DocxCreateService(Path source) throws Exception {
		super(source);
	}

	@Override
	public void open() {
	}

	@Override
	public Path create(Path target) throws Exception {
		MainDocumentPart root = service.getRootDocPart();
		root.addStyledParagraphOfText("Title", "Hello World!");
		root.addParagraphOfText("Welcome To Baeldung");
		File exportFile = target.toFile();
		return service.save(exportFile);	
	}

	@Override
	public void addAttrib(Attribut attibut) {
		
	}

}
