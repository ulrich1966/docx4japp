package de.juli.docx4j.controller;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

import de.juli.docx4j.model.CellModel;
import de.juli.docx4j.model.ParagraphModel;
import de.juli.docx4j.model.RowModel;
import de.juli.docx4j.model.TableModel;
import de.juli.docx4j.util.MarshallerUtil;

public class TableController {
	private static final Logger LOG = LoggerFactory.getLogger(TableController.class);
	private MarshallerUtil marshallerUtil = new MarshallerUtil(org.docx4j.jaxb.Context.jc);
	private TableModel model;
	
	public TableModel createModel(Tbl table) {
		model = new TableModel(table);
		List<TblGridCol> cols = table.getTblGrid().getGridCol();

		float[] columnWidths = new float[cols.size()];

		for (int i = 0; i < columnWidths.length; i++) {
			TblGridCol gridCol = cols.get(i);
			columnWidths[i] = gridCol.getW().intValue();
		}

		iterateChild(table);
		
		System.out.println();
		
		model.getCols()
			.forEach(a -> a.getParagraphs()
					.forEach(b -> b.getRows()
							.forEach(c ->  getNewPdfCell(c))));

		try {
			Float w = new Float(table.getTblPr().getTblW().getW().intValue());
			PdfPTable pdfTab = new PdfPTable(cols.size());
			pdfTab.setTotalWidth(w);
			pdfTab.setWidths(columnWidths);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return model;
	}

	private Object getNewPdfCell(RowModel rowModel) {
		if(rowModel.getTxt() != null && !rowModel.getTxt().getValue().isEmpty()) {
			LOG.info("{}", rowModel.getTxt().getValue());
		}
		return null;
	}

	private void iterateChild(Object value) {
		if(value instanceof javax.xml.bind.JAXBElement) {
			javax.xml.bind.JAXBElement<?> elem = (JAXBElement<?>) value;
			iterateChild(elem.getValue());
		}
		
		if (value instanceof org.docx4j.wml.Tbl) {
			org.docx4j.wml.Tbl element = (Tbl) value;
			List<Object> content = element.getContent();
			if (content != null && content.size() >= 1) {
				content.forEach(c -> iterateChild(c));
			} else {
				LOG.info("\t\tno more elements");
			}
		}

		if (value instanceof org.docx4j.wml.Tr) {
			org.docx4j.wml.Tr element = (Tr) value;
			List<Object> content = element.getContent();
			if (content != null && content.size() >= 1) {
				content.forEach(c -> iterateChild(c));
			} else {
				LOG.info("\t\tno more elements");
			}
		}
		
		if (value instanceof org.docx4j.wml.Tc) {
			org.docx4j.wml.Tc element = (Tc) value;
			List<Object> content = element.getContent();
			model.addCol(new CellModel(element));
			if (content != null && content.size() >= 1) {
				content.forEach(c -> iterateChild(c));
			} else {
				LOG.info("\t\tno more elements");
			}
		}

		if (value instanceof org.docx4j.wml.P) {
			org.docx4j.wml.P element = (P) value;
			List<Object> content = element.getContent();
			model.getCurrentCell().addRow(new ParagraphModel(element));
			if (content != null && content.size() >= 1) {
				content.forEach(c -> iterateChild(c));
			} else {
				LOG.info("\t\tno more elements");
			}
		}

		if (value instanceof org.docx4j.wml.R) {
			org.docx4j.wml.R element = (R) value;
			List<Object> content = element.getContent();
			model.getCurrentCell().getCurrentParagraph().addRow(new RowModel(element));
			if (content != null && content.size() >= 1) {
				content.forEach(c -> iterateChild(c));
			} else {
				LOG.info("\t\tno more elements");
			}
		}
		
		if (value instanceof org.docx4j.wml.Text) {
			org.docx4j.wml.Text txt = (Text) value;
			if(txt != null && !txt.getValue().isEmpty()) {
				model.getCurrentCell().getCurrentParagraph().getCurrentRow().setTxt(txt);				
			}
		}
	}
} // Class