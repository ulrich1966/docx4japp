package de.juli.docx4j.controller;

import java.util.ArrayList;
import java.util.List;

import org.docx4j.wml.TblGridCol;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

import de.juli.docx4j.model.Table;

public class PdfTableData {
	private static PdfTableData instance;
	private List<String> runs = new ArrayList<>();
	private PdfPTable pdfTab;

	private PdfTableData() {
		super();
	}

	public static PdfTableData getInstance() {
		if (instance == null) {
			instance = new PdfTableData();
		}
		return instance;
	}

	public void produceTable(Table table) {
		int collsize = table.getColls().size();
		float[] columnWidths = new float[collsize];
		
		for (int i = 0; i < columnWidths.length; i++) {
			TblGridCol gridCol = table.getColls().get(i);
			columnWidths[i] = gridCol.getW().intValue();
		}
		
		try {
			Float w = new Float(table.getWidth());
			PdfPTable pdfTab = new PdfPTable(collsize);
			pdfTab.setTotalWidth(w);
			pdfTab.setWidths(columnWidths);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public List<String> getRuns() {
		return runs;
	}

	public void addRun(String string) {
		if (runs == null) {
			runs = new ArrayList<>();
		}
		this.runs.add(string);
	}

	public PdfPTable getPdfTab() {
		return pdfTab;
	}
}
