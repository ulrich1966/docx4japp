package de.juli.docx4j.controller;

import org.docx4j.wml.Tbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.model.Table;
import de.juli.docx4j.util.MarshallerUtil;

public class TableController {
	private static final Logger LOG = LoggerFactory.getLogger(TableController.class);
	private Table model;
	
	public Table createModel(Tbl table) {
		MarshallerUtil mu = new MarshallerUtil(org.docx4j.jaxb.Context.jc);
		LOG.info("{}", mu.marschallDocx(table));
		
		model = new Table(table);
		
		model.getTableRows()
			.forEach(a -> a.getTableCells()
					.forEach(b -> b.getParagraphs()
							.forEach(c -> c.getRuns()
									.forEach(d -> d.getTxts()
											.forEach(e -> LOG.info("{}", e.getValue()))))));
		
		//table.getContent().forEach(c -> LOG.info("{}", mu.marschallDocx(c)));
		//table.getContent().forEach(c -> iterateChild(c));
		
//		List<TblGridCol> cols = table.getTblGrid().getGridCol();
//
//		float[] columnWidths = new float[cols.size()];
//
//		for (int i = 0; i < columnWidths.length; i++) {
//			TblGridCol gridCol = cols.get(i);
//			columnWidths[i] = gridCol.getW().intValue();
//		}
//
//		
//		
//		System.out.println();
//		
//		model.getCols()
//			.forEach(a -> a.getParagraphs()
//					.forEach(b -> b.getRows()
//							.forEach(c ->  getNewPdfCell(c))));

//		try {
//			Float w = new Float(table.getTblPr().getTblW().getW().intValue());
//			PdfPTable pdfTab = new PdfPTable(cols.size());
//			pdfTab.setTotalWidth(w);
//			pdfTab.setWidths(columnWidths);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}

		return model;
	}

	/*
	 * 
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
			model.getCurrentCell().getCurrentParagraph().getCurrentRow().setTxt(txt);				
		}
	}
	 */
} // Class
