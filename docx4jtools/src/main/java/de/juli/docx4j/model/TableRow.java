package de.juli.docx4j.model;

import java.util.List;

import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;

public class TableRow {
	private org.docx4j.wml.Tr row;
	private List<TableCell> tableCells;
	private TrPr trPr;

	public TableRow(Tr row) {
		super();
		this.row = row;
		this.trPr = row.getTrPr();
		row.getContent().forEach(c -> new TableCell((org.docx4j.wml.Tc) c));;
	}

	public org.docx4j.wml.Tr getRow() {
		return row;
	}

	public List<TableCell> getTableCells() {
		return tableCells;
	}

	public TrPr getTrPr() {
		return trPr;
	}
}
