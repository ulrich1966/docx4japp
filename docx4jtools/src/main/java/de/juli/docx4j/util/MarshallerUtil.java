package de.juli.docx4j.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.wml.Document;

public class MarshallerUtil {
	private AtomicInteger counter = new AtomicInteger(0);
	private JAXBContext context;

	public MarshallerUtil(JAXBContext jc) {
		this.context = jc;
	}

	public String marschallDocx(Object obj) {
		return marschallDocx(obj, this.context);
	}
	
	public String marschallDocx(Object obj, JAXBContext context) {
		String docx = null;
		StringBuilder sb = new StringBuilder();
		docx = XmlUtils.marshaltoString(obj, context);
		sb.append("\n\n------------------------------------------------------------------------------------------------\n");
		sb.append(String.format("\nHEADER NR: %s INHALT: %s\n", counter.intValue(), obj.getClass()));
		sb.append(String.format("\n%s\n", docx));
		return sb.toString();
	}

	public Object unmarshall(String value) throws JAXBException {
		InputStream is = new ByteArrayInputStream(value.getBytes());
		JAXBContext context = org.docx4j.jaxb.Context.jc;
		Object obj = XmlUtils.unmarshal(is, context);
		if(obj instanceof Document) {
			return (Document) obj;			
		}
		return obj;
	}

}
