package de.juli.docx4j.service.services.docx;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.Document;

import de.juli.docx4j.service.services.ReadService;
import de.juli.docx4j.util.PartKey;

public class DocxReadService implements ReadService {
	//private static final Logger LOG = LoggerFactory.getLogger(DocxReadService.class);
	private Docx4JService docx4jService;
	private Parts parts;


	public DocxReadService(Docx4JService docx4jService) throws Exception {
		this.docx4jService = docx4jService; 
	}

	@Override
	public Object read() throws Exception {
		this.parts = docx4jService.getParts();
		
//		List<Object> content = docx4jService.getJaxbElement().getContent();
//		SectPr sectPr = body.getSectPr();
//
//		LOG.info("{}", body.getClass());
//		List<Object> hc = header.getJaxbElement().getContent();
//		
//		hc.forEach(c -> LOG.info("{} -> {}", c, c.getClass()));
//		return content;
		//contentMap.put("body", body);
		return this.parts;
	}

	public List<Object> readHeader() throws Exception {
		if(parts == null) {
			read();
		}
		org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart header = (HeaderPart) docx4jService.getParts().get(PartKey.getPartName(PartKey.HEADER1));
		return header.getContent();
	}

	public List<Object> readBody() throws Exception {
		if(parts == null) {
			read();
		}
		Body body = docx4jService.getBody();
		// same as: docx4jService.getJaxbElement()
		return body.getContent();
	}

	public String marschallDocx() throws Docx4JException {
		return XmlUtils.marshaltoString(docx4jService.getJaxbElement(), true, true);
	}

	public String marschallDocx(Object obj, JAXBContext context) throws Docx4JException {
		return XmlUtils.marshaltoString(obj, context);
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
