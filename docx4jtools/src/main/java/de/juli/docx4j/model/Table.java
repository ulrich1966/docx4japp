package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;

import com.lowagie.text.pdf.PdfPTable;

public class Table extends Model {
	private Tbl table;
	private PdfPTable pdfTab;
	private TblPr tblPr;
	private TblGrid tblGrid;
	private List<TblGridCol> colls;
	private int width;
	private List<TableRow> tableRows = new ArrayList<>();
	
	public Table(Tbl table) {
		this.table = table;
		this.tblPr = table.getTblPr();
		this.tblGrid = table.getTblGrid();
		this.colls = this.tblGrid.getGridCol();
		this.width = table.getTblPr().getTblW().getW().intValue();
		table.getContent().forEach(c -> tableRows.add(new TableRow((org.docx4j.wml.Tr) c)));
		pdfTableData.produceTable(this);
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

	public List<TblGridCol> getColls() {
		return colls;
	}

	public int getWidth() {
		return width;
	}
}
