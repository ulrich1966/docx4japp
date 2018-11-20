package de.juli.docx4j.factory;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.service.services.docx.DocxReadService;
import de.juli.docx4j.service.services.pdf.PdfCreateService;

public class ContentMapFactory {
	private static final Logger LOG = LoggerFactory.getLogger(ContentMapFactory.class);
	private ContentMapFactory instance;
	private Path target;
	private PdfCreateService pdfCreateService;
	private DocxReadService docxReadService;

	
	private ContentMapFactory(Path source) throws Exception {
		super();
		pdfCreateService = new PdfCreateService(source);
		docxReadService = new DocxReadService(source);
	}
	
	public ContentMapFactory getInstance(Path target) throws Exception {
		if(instance == null) {
			instance = new ContentMapFactory(target);
		}
		return instance;
	}

}
