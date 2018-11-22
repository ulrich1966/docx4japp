package de.juli.docx4j.pdffactory;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBElement;

import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Paragraph;

import de.juli.docx4j.controller.TableController;
import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.docx.Docx4JService;
import de.juli.docx4j.service.services.docx.DocxReadService;
import de.juli.docx4j.util.MarshallerUtil;

public class ContentMapFactory {
	private static final Logger LOG = LoggerFactory.getLogger(ContentMapFactory.class);
	private static ContentMapFactory instance;
	private PdfCreateService pdfCreateService;
	private DocxReadService docxReadService;
	private Docx4JService docx4jService;
	private ArrayList<Object> list;
	private Path target;
	private MarshallerUtil marshaller = new MarshallerUtil(org.docx4j.jaxb.Context.jc);

	private ContentMapFactory(Path source) throws Exception {
		super();
		docx4jService = new Docx4JService(source);
		pdfCreateService = new PdfCreateService();
		docxReadService = new DocxReadService(docx4jService);
	}

	public static ContentMapFactory getInstance(Path target) throws Exception {
		if (instance == null) {
			instance = new ContentMapFactory(target);
		}
		return instance;
	}

	public Path createPdf(Path target) throws Exception {
		this.target = target;
		List<Object> header = this.docxReadService.readHeader();
		List<org.jvnet.jaxb2_commons.ppp.Child> childs = header.stream().map(c -> addElementToDoc(c)).collect(Collectors.toList());

		// List<String> headerXml = header.stream().map(c ->
		// marshall(c)).collect(Collectors.toList());
		// headerXml.forEach(c -> LOG.info("{}", c));
		// header.stream().map(c ->
		// iterateChild(c)).collect(Collectors.toList());
		// list.forEach(c -> LOG.info("{}", c));
		// List<Object> body = this.docxReadService.readBody();
		// body.forEach(c -> LOG.info("{}", c.getClass()));

		return target;
	}

	public void addPdfAttributs(Attribut attibuts) throws FileNotFoundException {
		pdfCreateService.setAttibut(attibuts);
	}

	private org.jvnet.jaxb2_commons.ppp.Child addElementToDoc(Object value) {
		org.jvnet.jaxb2_commons.ppp.Child child = null;
		//LOG.debug(marshall(value));

		if (value instanceof org.docx4j.wml.P) {
			org.docx4j.wml.P element = (P) value;
			addParagraph(element);
			child = element;
		}

		if (value instanceof javax.xml.bind.JAXBElement) {
			javax.xml.bind.JAXBElement<?> jaxb = (JAXBElement<?>) value;
			if (jaxb.getValue() instanceof org.docx4j.wml.Tbl) {
				org.docx4j.wml.Tbl element = (Tbl) jaxb.getValue();
				addTable(element);
				child = element;
			}
		}

//		String xpath = "//w:p";
//		List<Object> list = documentPart.getJAXBNodesViaXPath(xpath, false);

		return child;
	}

	private void addParagraph(P element) {
		Chunk chunk = new Chunk("");
		Paragraph paragraph = new Paragraph(chunk);
		pdfCreateService.addElement(paragraph);
	}

	private void addTable(org.docx4j.wml.Tbl tab) {
		TableController controller = new TableController();
		controller.createModel(tab);	


		// PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
		// cell1.setBorderColor(BaseColor.BLUE);
		// cell1.setPaddingLeft(10);
		// cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

		try {
			pdfCreateService.create(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	private String iterateChild(Object value) {
		LOG.debug("{} : {}", value.getClass(), value);
		String txt = null;

		if (value instanceof org.docx4j.wml.R) {
			org.docx4j.wml.R element = (R) value;
			List<Object> content = element.getContent();
			LOG.debug("\t\t- {} -", element);
			if (content != null && content.size() >= 1) {
				content.forEach(c -> iterateChild(c));
			} else {
				LOG.info("\t\tno more elements");
			}
		}

		if (value instanceof javax.xml.bind.JAXBElement) {
			javax.xml.bind.JAXBElement<?> jaxb = (JAXBElement<?>) value;
			LOG.info("Value of jaxb: {}", jaxb.getValue());

			if (jaxb.getValue() instanceof org.docx4j.wml.Tbl) {
				org.docx4j.wml.Tbl element = (Tbl) jaxb.getValue();
				LOG.debug("\t\t- {} -", element);

				addTable(element);

				List<Object> content = element.getContent();
				if (content != null && content.size() >= 1) {
					content.forEach(c -> iterateChild(c));
				} else {
					LOG.info("\t\tno more elements");
				}
			}

			if (jaxb.getValue() instanceof org.docx4j.wml.Tc) {
				org.docx4j.wml.Tc element = (Tc) jaxb.getValue();
				LOG.debug("\t\t- {} -", element);
				List<Object> content = element.getContent();
				if (content != null && content.size() >= 1) {
					content.forEach(c -> iterateChild(c));
				} else {
					LOG.info("\t\tno more elements");
				}
			}

			if (jaxb.getValue() instanceof org.docx4j.wml.Text) {
				org.docx4j.wml.Text element = (Text) jaxb.getValue();
				LOG.debug("\t\t- {} -", element);
				txt = element.getValue();
				String space = element.getSpace();
				LOG.debug("\t\t{}\t{}", txt, space);
				list.add(txt);
			}
		}

		if (value instanceof org.docx4j.wml.Tr) {
			org.docx4j.wml.Tr element = (Tr) value;
			LOG.debug("\t\t- {} -", element);
			List<Object> content = element.getContent();
			if (content != null && content.size() >= 1) {
				content.forEach(c -> iterateChild(c));
			} else {
				LOG.info("\t\tno more elements");
			}
		}

		return txt;
	}
	 */

	public PdfCreateService getPdfCreateService() {
		return pdfCreateService;
	}

	public DocxReadService getDocxReadService() {
		return docxReadService;
	}
}
