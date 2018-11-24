package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;

public class TableRow extends Model {
	private org.docx4j.wml.Tr row;
	private List<TableCell> tableCells = new ArrayList<>();
	private TrPr trPr;

	public TableRow(Tr row) {
		super();
		this.row = row;
		this.trPr = row.getTrPr();
		row.getContent().forEach(c -> {
			if(c instanceof javax.xml.bind.JAXBElement) {
				javax.xml.bind.JAXBElement<?> elem = (JAXBElement<?>) c;
				tableCells.add(new TableCell((org.docx4j.wml.Tc) elem.getValue()));	
			}
		});
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
