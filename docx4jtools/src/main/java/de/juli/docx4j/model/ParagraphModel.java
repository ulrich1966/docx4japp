package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.P;

public class ParagraphModel {
	private P paragraph;
	private List<RowModel> rows;
	private RowModel currentRow;
	
	public ParagraphModel(P paragraph) {
		super();
		this.paragraph = paragraph;
	}

	public P getParagraph() {
		return paragraph;
	}

	public void setParagraph(P paragraph) {
		this.paragraph = paragraph;
	}

	public List<RowModel> getRows() {
		return rows;
	}

	public void addRow(RowModel row) {
		if(rows == null) {
			rows = new ArrayList<>();
		}
		this.rows.add(row);
		this.currentRow = row;
	}

	public RowModel getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(RowModel currentRow) {
		this.currentRow = currentRow;
	}
}	
