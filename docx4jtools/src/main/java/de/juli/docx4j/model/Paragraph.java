package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.P;
import org.docx4j.wml.PPr;

public class Paragraph {
	private org.docx4j.wml.P paragraph;
	private PPr pPr;
	private List<Run> runs = new ArrayList<>();

	public Paragraph(P paragraph) {
		super();
		this.paragraph = paragraph;
		this.pPr = paragraph.getPPr();
		paragraph.getContent().forEach(c -> runs.add(new Run((org.docx4j.wml.R) c)));
	}

	public org.docx4j.wml.P getParagraph() {
		return paragraph;
	}

	public PPr getpPr() {
		return pPr;
	}

	public List<Run> getRuns() {
		return runs;
	}
}
