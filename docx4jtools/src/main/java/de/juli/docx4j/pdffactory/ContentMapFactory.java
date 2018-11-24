package de.juli.docx4j.pdffactory;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBElement;

import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;

import de.juli.docx4j.model.Table;
import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.docx.Docx4JService;
import de.juli.docx4j.service.services.docx.DocxReadService;
import de.juli.docx4j.service.services.pdf.PdfCreateService;
import de.juli.docx4j.util.MarshallerUtil;

public class ContentMapFactory {
	private static final Logger LOG = LoggerFactory.getLogger(ContentMapFactory.class);
	private static ContentMapFactory instance;
	private PdfCreateService pdfCreateService;
	private DocxReadService docxReadService;
	private Docx4JService docx4jService;
	@SuppressWarnings("unused")
	private MarshallerUtil marshaller = new MarshallerUtil(org.docx4j.jaxb.Context.jc);

	private ContentMapFactory(Path source) throws Exception {
		super();
		docx4jService = new Docx4JService(source);
		pdfCreateService = new PdfCreateService(source);
		docxReadService = new DocxReadService(docx4jService);
	}

	public static ContentMapFactory getInstance(Path target) throws Exception {
		if (instance == null) {
			instance = new ContentMapFactory(target);
		}
		return instance;
	}

	public Path createPdf(Path target) throws Exception {
//		org.docx4j.wml.Document doc = docx4jService.getDocument();
//		LOG.info("{}", marshaller.marschallDocx(doc));

		List<Object> header = this.docxReadService.readHeader();
		if (header != null) {
			header.forEach(c -> addElementToDoc(c));
		}

		List<Object> body = this.docxReadService.readBody();
		if (body != null) {
			body.forEach(c -> addElementToDoc(c));
		}
		
		LOG.info("Aktuell Anzahl der Elemente: {}", pdfCreateService.getElements().size());
		return pdfCreateService.create(target);
	}

	public void addPdfAttributs(Attribut attibuts) throws FileNotFoundException {
		pdfCreateService.addAttrib(attibuts);
	}

	private void addElementToDoc(Object value) {
		Element elm = null;

		if (value instanceof javax.xml.bind.JAXBElement) {
			javax.xml.bind.JAXBElement<?> jaxb = (JAXBElement<?>) value;
			addElementToDoc(jaxb.getValue());
		}

		if (value instanceof org.docx4j.wml.P) {
			org.docx4j.wml.P element = (P) value;
			elm = addParagraph(element);
			pdfCreateService.addElement(elm);
		}

		if (value instanceof org.docx4j.wml.Tbl) {
			org.docx4j.wml.Tbl element = (Tbl) value;
			elm = addTable(element);
			pdfCreateService.addElement(elm);
		}

		// String xpath = "//w:p";
		// List<Object> list = documentPart.getJAXBNodesViaXPath(xpath, false);
	}

	private Paragraph addParagraph(P element) {
		return null;
	}

	private PdfPTable addTable(org.docx4j.wml.Tbl tab) {
		Table pdfT = new Table(tab);
		List<String> txts = pdfT.getData().getTempTxt();
		LOG.info("count: {}", ""+txts.size());
		txts.forEach(c -> LOG.info("{}", c));
		PdfPTable pdfTab = pdfT.getData().getPdfTab();
		return pdfTab;
	}

	public PdfCreateService getPdfCreateService() {
		return pdfCreateService;
	}

	public DocxReadService getDocxReadService() {
		return docxReadService;
	}
}
