package de.juli.docx4j.service.services.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblPr;
import org.jvnet.jaxb2_commons.ppp.Child;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import de.juli.docx4j.service.CreateService;
import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.docx.DocxReadService;

public class PdfCreateService extends CreateService {
	private static final Logger LOG = LoggerFactory.getLogger(PdfCreateService.class);
	private DocxReadService docxReader;
	private Document document;
	private PdfWriter writer;

	public PdfCreateService(Path path) throws Exception {
		super(path);
		docxReader = new DocxReadService(path);
	}

	@Override
	public void open() throws FileNotFoundException {
		this.document = new Document();
		document.open();
	}

	@Override
	public Path create(Path target) throws Exception {
		writer = PdfWriter.getInstance(document, new FileOutputStream(target.toFile()));
		if (document == null) {
			throw new IllegalStateException("Kein Dokument vorhanden");
		}
		if(LOG.isDebugEnabled()){
			xmlLogOut();
		}
		docxReader.read();
		return target;
	}
	
	public String xmlLogOut() throws Docx4JException {
		String docx = docxReader.marschallDocx(super.docxService.getJaxbElement());
		LOG.info("{}", docx);
		return docx;
	}

	public Object objectGen(String docx) throws Docx4JException, FileNotFoundException, JAXBException {
		Object obj = docxReader.unmarschallDocx(docx);
		LOG.info("{}", obj);
		return obj;
	}

	public void addAttrib(Document document, Attribut attibut) {
		document.addCreationDate();
		document.addAuthor(attibut.getAuthor());
		document.addCreator(attibut.getCreator());
		document.addTitle(attibut.getTitle());
		document.addSubject(attibut.getSubject());
	}

	private Object itteratePart(Child child) {
		LOG.debug("{}: {}", child.getClass(), child);
		if (child instanceof org.docx4j.wml.P) {
			org.docx4j.wml.P element = (P) child;
			List<Object> content = element.getContent();
			if(content != null && content.size() >=1) {
				content.forEach(c-> itteratePart((Child) c));
			} else {
				LOG.info("no more elements");				
			}
		}
		return null;
	}

	private Paragraph append(Document document, Child child) throws DocumentException {
		return append(document, String.format("%s", child));
	}

	private Paragraph append(Document document, String value) throws DocumentException {
		Chunk chunk = new Chunk(value);
		Paragraph paragraph = new Paragraph(chunk);
		document.add(paragraph);
		return paragraph;
	}

	private void showCildInfo(Child child) {
		LOG.debug("{}", child.getClass());
		// if (cild instanceof org.docx4j.wml.P) {
		// org.docx4j.wml.P pCild = (P) cild;
		// LOG.debug("{}", pCild);
		// //pCild.getContent().forEach(e -> LOG.info("{}", e));
		// }
	}

	private void docxContent(Object value, List<Child> childs) {
		Child child = null;
		if (value.getClass().equals(javax.xml.bind.JAXBElement.class)) {
			javax.xml.bind.JAXBElement<?> jaxb = (JAXBElement<?>) value;
			if (jaxb.getValue() instanceof org.docx4j.wml.Tbl) {
				org.docx4j.wml.Tbl element = (Tbl) jaxb.getValue();
				// handleTableElement(element);
				child = element;
			}
		}
		if (value instanceof org.docx4j.wml.P) {
			org.docx4j.wml.P element = (P) value;
			child = element;
		}
		if (value instanceof org.docx4j.wml.Tbl) {
			org.docx4j.wml.Tbl element = (Tbl) value;
			child = element;
		}
		if (value instanceof org.docx4j.wml.Tr) {
			// org.docx4j.wml.Tr elm = (Tr) value;
			// elementList.add(elm);
		}
		childs.add(child);
	}

	private void handleTableElement(Tbl element, List<Child> childs) {
		LOG.debug("{}", element);
		TblPr tblPr = element.getTblPr();
		docxContent(tblPr, childs);
	}

	private void addChunk(Document document, Child child) throws DocumentException {
		// Font font = FontFactory.getFont(FontFactory.COURIER, 16, Color.BLACK);
		Chunk chunk = new Chunk("" + child);
		document.add(chunk);
	}

	private List<Child> handleParts(Part part) {
		if (part instanceof org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart) {
			org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart header = (HeaderPart) part;
			List<Object> content = header.getContent();
			List<Child> childs = new ArrayList<>();
			content.forEach(e -> docxContent(e, childs));
			return childs;
		}

		if (part instanceof org.docx4j.openpackaging.parts.WordprocessingML.FooterPart) {

		}

		if (part instanceof org.docx4j.openpackaging.parts.WordprocessingML.FontTablePart) {

		}
		return null;
	}

	// Getter / Setter

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@Override
	public void addAttrib(Attribut attibut) {
		// TODO Auto-generated method stub

	}

}
