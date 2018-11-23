package de.juli.docx4j.controller;

import java.util.List;

import org.docx4j.wml.Tbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docx4j.model.PdfTAbleData;
import de.juli.docx4j.model.Table;

public class TableController {
	private static final Logger LOG = LoggerFactory.getLogger(TableController.class);
	private Table model;
	
	public Table createModel(Tbl table) {
//		MarshallerUtil mu = new MarshallerUtil(org.docx4j.jaxb.Context.jc);
//		LOG.info("{}", mu.marschallDocx(table));
		
		model = new Table(table);
		
		List<String> runs = PdfTAbleData.getInstance().getRuns();
		LOG.info("count: {}", runs.size());
		runs.forEach(c -> LOG.info("{}", c));
		
		return model;
	}
} // Class
