package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.Tc;

public class CellModel {
	private Tc col;
	private List<ParagraphModel> paragraphs;
	private ParagraphModel currentParagraph;
		
	public CellModel(Tc col) {
		super();
		this.col = col;
	}

	public Tc getCol() {
		return col;
	}
	
	public void setCol(Tc col) {
		this.col = col;
	}

	public void addRow(ParagraphModel paragraph) {
		if(this.paragraphs == null) {
			this.paragraphs = new ArrayList<>();
		}
		this.paragraphs.add(paragraph);
		this.currentParagraph = paragraph;
	}

	public ParagraphModel getCurrentParagraph() {
		return currentParagraph;
	}

	public void setCurrentParagraph(ParagraphModel currentParagraph) {
		this.currentParagraph = currentParagraph;
	}

	public List<ParagraphModel> getParagraphs() {
		return paragraphs;
	}
}
