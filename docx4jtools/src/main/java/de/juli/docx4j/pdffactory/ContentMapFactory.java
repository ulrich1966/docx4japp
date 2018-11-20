package de.juli.docx4j.pdffactory;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.org.apache.poi.poifs.property.Child;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;

import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.docx.Docx4JService;
import de.juli.docx4j.service.services.docx.DocxReadService;

public class ContentMapFactory {
	private static final Logger LOG = LoggerFactory.getLogger(ContentMapFactory.class);
	private AtomicInteger counter = new AtomicInteger(0);
	private static ContentMapFactory instance;
	private PdfCreateService pdfCreateService;
	private DocxReadService docxReadService;
	private Docx4JService docx4jService;
	private ArrayList<Object> list;
	private List<TblGridCol> gridCol;

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
		List<Object> header = this.docxReadService.readHeader();
		List<String> headerXml = header.stream().map(c -> marshall(c)).collect(Collectors.toList());
		headerXml.forEach(c -> LOG.info("{}", c));
		
		//header.stream().map(c -> iterateChild(c)).collect(Collectors.toList());
		
		//list.forEach(c -> LOG.info("{}", c));
		// List<Object> body = this.docxReadService.readBody();
		// body.forEach(c -> LOG.info("{}", c.getClass()));

		return target;
	}

	public String marshall() throws Docx4JException {
		String docx = docxReadService.marschallDocx();
		LOG.debug("{}", docx);
		return docx;
	}

	public String marshall(Object obj) {
		String docx = null;
		StringBuilder sb = new StringBuilder();
		try {
			docx = docxReadService.marschallDocx(obj, org.docx4j.jaxb.Context.jc);
			sb.append("\n\n------------------------------------------------------------------------------------------------\n");
			sb.append(String.format("\nHEADER NR: %s INHALT: %s\n", counter.intValue(), obj.getClass()));
			sb.append(String.format("\n%s\n", docx));
		} catch (Docx4JException e) {
			e.printStackTrace();
		}			
		return sb.toString();
	}

	public Object unmarshall(String docx) throws Docx4JException, FileNotFoundException, JAXBException {
		Object obj = docxReadService.unmarschallDocx(docx);
		LOG.info("{}", obj);
		return obj;
	}

	public void addPdfAttributs(Attribut attibuts) throws FileNotFoundException {
		pdfCreateService.addAttrib(attibuts);
	}
	
	private String addElementToDoc(Object value) {
		if (value instanceof org.docx4j.wml.P) {
			org.docx4j.wml.P element = (P) value;
			addParagraph(element);
		}
		return null;
	}
	
	private void addParagraph(P element) {
		Document document = pdfCreateService.getDocument();
		Chunk chunk = new Chunk("");
		Paragraph paragraph = new Paragraph(chunk);
		pdfCreateService.addElement(paragraph);
		
	}

	private void addTable(org.docx4j.wml.Tbl tab){
		List<TblGridCol> cols = tab.getTblGrid().getGridCol();		
		Float w = new Float(tab.getTblPr().getTblW().getW().intValue());
		
		PdfPTable table = new PdfPTable(cols.size());
		table.setTotalWidth(w);
		
		Paragraph paragraph = new Paragraph();
		paragraph.add(table);
		
		for (TblGridCol col : cols) {
		}
		
	}

	
	
	
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
	
	
	public PdfCreateService getPdfCreateService() {
		return pdfCreateService;
	}

	public DocxReadService getDocxReadService() {
		return docxReadService;
	}
}
