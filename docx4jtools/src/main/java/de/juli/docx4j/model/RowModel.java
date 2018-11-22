package de.juli.docx4j.model;

import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

public class RowModel {
	private R row;
	private RPr rPr;
	private Text txt;
	
	public RowModel(R row) {
		super();
		this.row = row;
		this.rPr = row.getRPr();
	}

	public R getRow() {
		return row;
	}

	public void setRow(R row) {
		this.row = row;
	}

	public RPr getrPr() {
		return rPr;
	}

	public void setrPr(RPr rPr) {
		this.rPr = rPr;
	}

	public Text getTxt() {
		return txt;
	}

	public void setTxt(Text txt) {
		this.txt = txt;
	}
}
