package de.juli.docx4j.controller;

import de.juli.docx4j.model.Table;

public class PdfParagraphData {
	private static PdfParagraphData instance;
	
	private PdfParagraphData() {
		super();
	}

	public static PdfParagraphData getInstance() {
		if (instance == null) {
			instance = new PdfParagraphData();
		}
		return instance;
	}

	public void produceParagraph(Table table) {
	}
}
