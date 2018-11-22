package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;

public class TableCell {
	private org.docx4j.wml.Tc cell;
	private TcPr tcPr;
	private List<TableParagraph> paragraphs = new ArrayList<>();

	public TableCell(Tc cell) {
		super();
		this.cell = cell;
		this.tcPr = cell.getTcPr();
		cell.getContent().forEach(c -> paragraphs.add(new TableParagraph((org.docx4j.wml.P) c)));
	}

	public org.docx4j.wml.Tc getCell() {
		return cell;
	}

	public TcPr getTcPr() {
		return tcPr;
	}

	public List<TableParagraph> getParagraphs() {
		return paragraphs;
	}
}
