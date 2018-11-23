package de.juli.docx4j.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
	private Attribut attibut; 
	private List<Element> elements; 

	protected PdfCreateService() throws Exception {
	}

	@Override
	public void open() throws FileNotFoundException {
	}

	@Override
	public Path create(Path target) {
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(target.toFile()));
			document.open();

			if (attibut != null) {
				addAttrib(attibut, document);
			}
			
			for (Element e : elements) {
				document.add(e);
			}

			if (document.isOpen()) {
				document.close();
				if (!writer.isCloseStream()) {
					writer.close();
				}
			}
			return target;
		} catch (FileNotFoundException | DocumentException e) {
			LOG.error("{}", e.getMessage());
		}
		return null;
	}

	public void addElement(Element element) {
		if(elements == null) {
			elements = new ArrayList<>();
		}
		elements.add(element);
	}

	private void addAttrib(Attribut attibut, Document document) {
		document.addCreationDate();
		document.addAuthor(attibut.getAuthor());
		document.addCreator(attibut.getCreator());
		document.addTitle(attibut.getTitle());
		document.addSubject(attibut.getSubject());
	}

	// Getter / Setter

	public Attribut getAttibut() {
		return attibut;
	}

	public void setAttibut(Attribut attibut) {
		this.attibut = attibut;
	}
}
