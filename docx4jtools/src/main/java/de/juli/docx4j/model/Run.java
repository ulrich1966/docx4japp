package de.juli.docx4j.model;

import java.util.List;

import org.docx4j.wml.R;
import org.docx4j.wml.RPr;

public class Run {
	private org.docx4j.wml.R run;
	private RPr rPr;
	private List<org.docx4j.wml.Text> txts;

	public Run(R run) {
		super();
		this.run = run;
		this.rPr = run.getRPr();
		run.getContent().forEach(c -> {
			if(c instanceof org.docx4j.wml.R) {
				txts.add((org.docx4j.wml.Text) c);
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
