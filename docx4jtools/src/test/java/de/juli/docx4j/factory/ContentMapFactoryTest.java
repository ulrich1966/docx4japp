package de.juli.docx4j.factory;

import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.ServiceTest;
import de.juli.docx4j.pdffactory.ContentMapFactory;
import de.juli.docx4j.service.model.Attribut;
import de.juli.docx4j.service.services.FieldPasteService;
import de.juli.docx4j.util.Executer;
import de.juli.docx4j.util.TestDaten;

public class ContentMapFactoryTest extends ServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(ContentMapFactoryTest.class);
	private boolean execPdf = true;
	
	@Test
	public void test() {	
		try {
			// LOG.debug("{}", ${file});
			String[] files = new String[] {"ansch.docx", "converttxt.docx", "headerread.docx", "table.docx"};

			Path target = DOC_ROOT.resolve("test_pdf.pdf");
			Path docx = DOC_ROOT.resolve(files[3]);
			ContentMapFactory factory = ContentMapFactory.getInstance(docx);
			Assert.assertNotNull("DocxReadService missing", factory.getDocxReadService());
			Assert.assertNotNull("PdfCreateService missing", factory.getPdfCreateService());
			factory.addPdfAttributs(makeAtt());
			Path pdf = factory.createPdf(target);
			
			if(execPdf) openProcess(Executer.PDF_EXECUTER, pdf.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		LOG.info("DONE!");
		
	}

	private Attribut makeAtt() {
		Attribut attribut = new Attribut();
		attribut.setAuthor("Uli");
		attribut.setCreator("Uli");
		attribut.setSubject("Pdf creation");
		attribut.setTitle("Pdf creation");
		return attribut;
	}

	private Path fillFields(Path source) throws Exception {
		Map<String, String> data = TestDaten.genareate();
		Path target = DOC_ROOT.resolve(String.format("anschreiben_%s.docx", data.get("name")));
		FieldPasteService pasteService = new FieldPasteService(source);
		pasteService.paste(data, target);
		return target;
	}

}
