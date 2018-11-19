package de.juli.docx4j.service.services.docx;

import java.io.ByteArrayInputStream;
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
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Document;
import org.docx4j.wml.SectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.service.services.ReadService;
import de.juli.docx4j.util.PartKey;

public class DocxReadService implements ReadService {
	private static final Logger LOG = LoggerFactory.getLogger(DocxReadService.class);
	private String marshalString;
	private Part header;
	private Part footer;
	private Map<String, Document> contentMap = new HashMap<>();
	private Docx4JService docx4jService;


	public DocxReadService(Path source) throws Exception {
		docx4jService = new Docx4JService(source);
	}

	@Override
	public Object read() throws Exception {
		List<Object> content = docx4jService.getJaxbElement().getContent();
		Body body = docx4jService.getBody();
		SectPr sectPr = body.getSectPr();

		LOG.info("{}", body.getClass());
		org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart header = (HeaderPart) docx4jService.getParts().get(PartKey.getPartName(PartKey.HEADER1));
		List<Object> hc = header.getJaxbElement().getContent();
		
		hc.forEach(c -> LOG.info("{} {}", c, c.getClass()));
		return content;
		//contentMap.put("body", body);
	}

	public String marschallDocx() throws Docx4JException {
		return XmlUtils.marshaltoString(docx4jService.getJaxbElement(), true, true);
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

	public Map<String, StringBuilder> docxText() throws XPathBinderAssociationIsPartialException, JAXBException {
		Map<String, StringBuilder> map = new HashMap<>();
		map.put("head", docx4jService.parseHeaderText());
		map.put("body", docx4jService.parseBodyText());
		map.put("footer", docx4jService.parseFooterText());
		return map;
	}

	public List<Part> getHeaders() {
		List<Part> parts = new ArrayList<>();
		docx4jService.findHeaderParts().forEach((k, v) -> parts.add(v));
		return parts;
	}

	public List<Part> getFooters() {
		List<Part> parts = new ArrayList<>();
		docx4jService.findFooterParts().forEach((k, v) -> parts.add(v));
		return parts;
	}
}
