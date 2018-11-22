package de.juli.docx4j.model;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.Tbl;

import com.lowagie.text.pdf.PdfPTable;

public class TableModel {
	private Tbl table;
	private PdfPTable pdfTab;
	private List<CellModel> cells;
	private CellModel currentCell;
	
	public TableModel(Tbl table) {
		this.table = table;
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
}
