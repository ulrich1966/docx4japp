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
	private List<CellModel> cells;
	private CellModel currentCell;
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
	
	public List<CellModel> getCols() {
		return cells;
	}
	
	public void addCol(CellModel cell) {
		if(cells == null) {
			this.cells = new ArrayList<>();			
		}
		this.cells.add(cell);
		this.currentCell = cell;
	}

	public PdfPTable getPdfTab() {
		return pdfTab;
	}

	public void setPdfTab(PdfPTable pdfTab) {
		this.pdfTab = pdfTab;
	}

	public CellModel getCurrentCell() {
		return currentCell;
	}

	public List<CellModel> getCells() {
		return cells;
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
