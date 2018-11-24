package de.juli.docx4j.service.services.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfWriter;

import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.CreateService;
import de.juli.docx4j.service.services.docx.DocxReadService;

public class PdfCreateService implements CreateService {
	private static final Logger LOG = LoggerFactory.getLogger(PdfCreateService.class);
	private Document document;
	private DocxReadService docxReadService;
	private List<Element> elements = new ArrayList<>();

	public PdfCreateService(Path source) throws Exception {
		this.document = new Document();
	}

	@Override
	public void open() throws FileNotFoundException {
	}

	@Override
	public Path create(Path target) throws Exception {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(target.toFile()));
		document.open();
		for (Element element : elements) {
			document.add(element);
		}
		document.close();
		writer.close();
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

	public List<Element> getElements() {
		return elements;
	}

	public void addElement(Element element) {
		if (element != null) {
			LOG.info("Add: {}", element.getClass());
			this.elements.add(element);
		}
	}

	public void addElements(List<Element> elements) {
		if (elements != null) {
			this.elements.addAll(elements);
		}
	}
}
