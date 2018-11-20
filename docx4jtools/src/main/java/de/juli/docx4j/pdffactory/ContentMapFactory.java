package de.juli.docx4j.pdffactory;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.service.services.docx.Docx4JService;
import de.juli.docx4j.service.services.docx.DocxReadService;

public class ContentMapFactory {
	private static final Logger LOG = LoggerFactory.getLogger(ContentMapFactory.class);
	private static ContentMapFactory instance;
	private PdfCreateService pdfCreateService;
	private DocxReadService docxReadService;
	private Docx4JService docx4jService;

	
	private ContentMapFactory(Path source) throws Exception {
		super();
		docx4jService = new Docx4JService(source);
		pdfCreateService = new PdfCreateService(docx4jService);
		docxReadService = new DocxReadService(docx4jService);
	}
	
	public static ContentMapFactory getInstance(Path target) throws Exception {
		if(instance == null) {
			instance = new ContentMapFactory(target);
		}
		return instance;
	}
	
	public Path createPdf(Path target) throws Exception {
		this.docxReadService.read();
		return target;
	}
	
	public String xmlLogOut() throws Docx4JException {
		String docx = docxReadService.marschallDocx();
		LOG.info("{}", docx);
		return docx;
	}

	public Object objectGen(String docx) throws Docx4JException, FileNotFoundException, JAXBException {
		Object obj = docxReadService.unmarschallDocx(docx);
		LOG.info("{}", obj);
		return obj;
	}

	public PdfCreateService getPdfCreateService() {
		return pdfCreateService;
	}

	public DocxReadService getDocxReadService() {
		return docxReadService;
	}
}
