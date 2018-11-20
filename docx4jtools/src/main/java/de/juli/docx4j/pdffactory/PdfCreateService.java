package de.juli.docx4j.pdffactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfWriter;

import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.CreateService;

class PdfCreateService implements CreateService {
	private static final Logger LOG = LoggerFactory.getLogger(PdfCreateService.class);
	private Document document;

	protected PdfCreateService() throws Exception {
	}

	@Override
	public void open() throws FileNotFoundException {
		if (this.document == null || !this.document.isOpen()) {
			this.document = new Document();
			document.open();
		}
	}

	@Override
	public Path create(Path target) throws Exception {
		if (document == null) {
			throw new IllegalStateException("Kein Dokument vorhanden");
		}
		
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(target.toFile()));

		if (document.isOpen()) {
			try {
				document.close();
			} catch (Exception e) {
				LOG.error("{}", e.getMessage());
				return null;
			}
			if (!writer.isCloseStream()) {
				try {
					writer.close();
				} catch (Exception e) {
					LOG.error("{}", e.getMessage());
					return null;
				}
			}
		}
		return target;
	}

	public void addAttrib(Attribut attibut) throws FileNotFoundException {
		if (this.document == null || !this.document.isOpen()) {
			open();
		}
		this.document.addCreationDate();
		this.document.addAuthor(attibut.getAuthor());
		this.document.addCreator(attibut.getCreator());
		this.document.addTitle(attibut.getTitle());
		this.document.addSubject(attibut.getSubject());
	}

	public void addElement(Element element) {
		try {
			document.add(element);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	// Getter / Setter

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
