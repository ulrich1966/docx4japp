package de.juli.docx4j.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import de.juli.docx4j.model.Table;
import de.juli.docx4j.model.TableCell;
import de.juli.docx4j.model.TableParagraph;
import de.juli.docx4j.model.TableRow;
import de.juli.docx4j.model.TableRun;

public class PdfTableData {
	private static final Logger LOG = LoggerFactory.getLogger(PdfTableData.class);
	private AtomicInteger inc = new AtomicInteger(0);
//	private static PdfTableData instance;
	private List<TableRow> rows;
	private List<String> tempTxt;
	private PdfPTable pdfTab;

	public PdfTableData() {
		super();
	}

//	public static PdfTableData getInstance() {
//		if (instance == null) {
//			instance = new PdfTableData();
//		}
//		return instance;
//	}

	public PdfPTable produceTable(Table table) {
		int collsize = table.getColls().size();
		rows = table.getTableRows();

		float[] columnWidths = new float[collsize];

		for (int i = 0; i < columnWidths.length; i++) {
			TblGridCol gridCol = table.getColls().get(i);
			columnWidths[i] = gridCol.getW().intValue();
		}
		

		try {
			Float totalWidth = new Float(table.getWidth());
			pdfTab = new PdfPTable(collsize);
			pdfTab.setWidths(columnWidths);
			
			LOG.info("\t{}", columnWidths);
			LOG.info("\t{}", totalWidth);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		tempTxt = new ArrayList<>();

		rows.forEach(r -> {
			List<TableCell> tc = r.getTableCells();
			tc.forEach(p -> {
				List<TableParagraph> tp = p.getParagraphs();
				tp.forEach(rr -> {
					List<TableRun> tr = rr.getRuns();
					tr.forEach(txt -> {
						List<Text> tl = txt.getTxts();
						tl.forEach(i -> {
							tempTxt.add(i.getValue());
							pdfTab.addCell(new PdfPCell(new Paragraph(i.getValue())));
						});
					});
				});
			});
		});

		return pdfTab;
	}

	public PdfPTable getPdfTab() {
		return pdfTab;
	}

	public AtomicInteger getInc() {
		return inc;
	}

	public List<TableRow> getRows() {
		return rows;
	}

	public List<String> getTempTxt() {
		return tempTxt;
	}

	public void setPdfTab(PdfPTable pdfTab) {
		this.pdfTab = pdfTab;
	}
}
