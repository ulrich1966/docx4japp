package de.juli.docx4j.pdffactory;

import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.model.PdfTAbleData;
import de.juli.docx4j.model.Table;
import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.docx.Docx4JService;
import de.juli.docx4j.service.services.docx.DocxReadService;
import de.juli.docx4j.service.services.pdf.PdfCreateService;

public class ContentMapFactory {
	private static final Logger LOG = LoggerFactory.getLogger(ContentMapFactory.class);
	private static ContentMapFactory instance;
	private PdfCreateService pdfCreateService;
	private DocxReadService docxReadService;
	private Docx4JService docx4jService;
	private Path target;

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
		this.target = target;
		List<Object> header = this.docxReadService.readHeader();
		return target;
	}

	public void addPdfAttributs(Attribut attibuts) {
		pdfCreateService.addAttrib(attibuts);
	}

	private void addTable(org.docx4j.wml.Tbl tab) {
//		MarshallerUtil mu = new MarshallerUtil(org.docx4j.jaxb.Context.jc);
//		LOG.info("{}", mu.marschallDocx(table));
		
		Table table = new Table(tab);
		List<String> runs = PdfTAbleData.getInstance().getRuns();
		LOG.info("count: {}", runs.size());
		runs.forEach(c -> LOG.info("{}", c));
		

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
	
	public PdfCreateService getPdfCreateService() {
		return pdfCreateService;
	}

	public DocxReadService getDocxReadService() {
		return docxReadService;
	}
}
