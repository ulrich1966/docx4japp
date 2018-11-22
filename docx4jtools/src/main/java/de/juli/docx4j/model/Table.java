package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblPr;

import com.lowagie.text.pdf.PdfPTable;

public class Table {
	private Tbl table;
	private PdfPTable pdfTab;
	private TblPr tblPr;
	private TblGrid tblGrid;
	private List<TableRow> tableRows = new ArrayList<>();
	
	public Table(Tbl table) {
		this.table = table;
		tblPr = table.getTblPr();
		tblGrid = table.getTblGrid();
		table.getContent().forEach(c -> tableRows.add(new TableRow((org.docx4j.wml.Tr) c)));
	}

	public Tbl getTable() {
		return table;
	}
	public void setTable(Tbl table) {
		this.table = table;
	}

	public PdfPTable getPdfTab() {
		return pdfTab;
	}

	public TblPr getTblPr() {
		return tblPr;
	}

	public TblGrid getTblGrid() {
		return tblGrid;
	}

	public List<TableRow> getTableRows() {
		return tableRows;
	}
}
