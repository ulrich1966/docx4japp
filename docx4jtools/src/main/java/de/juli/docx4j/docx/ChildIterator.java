package de.juli.docx4j.docx;

import java.util.List;

import org.docx4j.wml.P;
import org.jvnet.jaxb2_commons.ppp.Child;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChildIterator {
	private static final Logger LOG = LoggerFactory.getLogger(ChildIterator.class);

	public void iterateOverChild(Child child) {
		if (child instanceof org.docx4j.wml.P) {
			org.docx4j.wml.P element = (P) child;
			List<Object> content = element.getContent();
			if(content != null && content.size() >=1) {
				content.forEach(c-> iterateOverChild((Child) c));
			} else {
				LOG.info("no more elements");				
			}
		}
	}
}
