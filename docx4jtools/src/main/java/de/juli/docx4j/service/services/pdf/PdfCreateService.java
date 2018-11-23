package de.juli.docx4j.service.services.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.CreateService;
import de.juli.docx4j.service.services.docx.DocxReadService;

public class PdfCreateService implements CreateService {
	private static final Logger LOG = LoggerFactory.getLogger(PdfCreateService.class);
	private Document document;
	private DocxReadService docxReadService;

	public PdfCreateService(Path source) throws Exception {
		this.document =  new Document();
	}

	@Override
	public void open() throws FileNotFoundException {
		document.open();
	}

	@Override
	public Path create(Path target) throws Exception {
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(target.toFile()));
		if (document == null) {
			throw new IllegalStateException("Kein Dokument vorhanden");
		}
		return target;
	}
	
	public Object objectGen(String docx) throws Docx4JException, FileNotFoundException, JAXBException {
		Object obj = docxReadService.unmarschallDocx(docx);
		LOG.info("{}", obj);
		return obj;
	}

	public void addAttrib(Attribut attibut) {
		this.document.addCreationDate();
		this.document.addAuthor(attibut.getAuthor());
		this.document.addCreator(attibut.getCreator());
		this.document.addTitle(attibut.getTitle());
		this.document.addSubject(attibut.getSubject());
	}
	
	// Getter / Setter

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}
