package de.juli.docx4j.model;

import de.juli.docx4j.util.MarshallerUtil;

public abstract class Model {
	protected MarshallerUtil mu = new MarshallerUtil(org.docx4j.jaxb.Context.jc);
	protected PdfTAbleData pdfTableData = PdfTAbleData.getInstance();
}
