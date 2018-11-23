package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.util.MarshallerUtil;

public class TableRun extends Model {
	private static final Logger LOG = LoggerFactory.getLogger(TableRun.class);
	
	private org.docx4j.wml.R run;
	private RPr rPr;
	private List<org.docx4j.wml.Text> txts = new ArrayList<>();

	public TableRun(R run) {
		super();
		this.run = run;
		this.rPr = run.getRPr();
	
		run.getContent().forEach(c -> {
			javax.xml.bind.JAXBElement<?> jaxb = null;
			try {
				jaxb = (JAXBElement<?>) c;
				if(jaxb.getValue() instanceof org.docx4j.wml.Text) {
					org.docx4j.wml.Text txt = (Text) jaxb.getValue();
					pdfTableData.addRun(txt.getValue());
				}
			} catch (java.lang.ClassCastException e) {
				LOG.error("{}", e.getMessage());
			}
		});	
	}

	public org.docx4j.wml.R getRun() {
		return run;
	}

	public RPr getrPr() {
		return rPr;
	}

	public List<org.docx4j.wml.Text> getTxts() {
		return txts;
	}	
}
