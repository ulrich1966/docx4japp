package de.juli.docx4j.service.services.docx;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.CTRel;
import org.docx4j.wml.Document;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.SectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.service.ReadService;
import de.juli.docx4j.service.Service;
import de.juli.docx4j.util.PartKey;

public class DocxReadService extends Service implements ReadService {
	private static final Logger LOG = LoggerFactory.getLogger(DocxReadService.class);
	private String marshalString;
	private Part header;
	private Part footer;
	private Map<String, Document> contentMap = new HashMap<>();


	public DocxReadService(Path path) throws Exception {
		super(path);
	}

	@Override
	public List<Object> read() throws Exception {
		List<Object> content = super.docxService.getJaxbElement().getContent();
		Body body = super.docxService.getBody();
		SectPr sectPr = body.getSectPr();
		
		LOG.info("{}", body.getClass());
		org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart header = (HeaderPart) super.docxService.getParts().get(PartKey.getPartName(PartKey.HEADER1));
		List<Object> hc = header.getJaxbElement().getContent();
		
		hc.forEach(c -> LOG.info("{} {}", c, c.getClass()));
		
		//contentMap.put("body", body);
		return content;
	}

	public String marschallDocx(Document jaxbElement) throws Docx4JException {
		return XmlUtils.marshaltoString(jaxbElement, true, true);
	}

	public Document unmarschallDocx(String value) throws Docx4JException, FileNotFoundException, JAXBException {
		InputStream is = new ByteArrayInputStream(value.getBytes());
		JAXBContext context = org.docx4j.jaxb.Context.jc;
		Object obj = XmlUtils.unmarshal(is, context);
		if(obj instanceof Document) {
			return (Document) obj;			
		}
		return null;
	}

	public void play() throws Docx4JException {
		Object target = XmlUtils.unwrap(source);
		LOG.info("{}", target);
	}

	public Map<String, StringBuilder> docxText() throws XPathBinderAssociationIsPartialException, JAXBException {
		Map<String, StringBuilder> map = new HashMap<>();
		map.put("head", super.docxService.parseHeaderText());
		map.put("body", super.docxService.parseBodyText());
		map.put("footer", super.docxService.parseFooterText());
		return map;
	}

	public List<Part> getHeaders() {
		List<Part> parts = new ArrayList<>();
		super.docxService.findHeaderParts().forEach((k, v) -> parts.add(v));
		return parts;
	}

	public List<Part> getFooters() {
		List<Part> parts = new ArrayList<>();
		super.docxService.findFooterParts().forEach((k, v) -> parts.add(v));
		return parts;
	}
}
