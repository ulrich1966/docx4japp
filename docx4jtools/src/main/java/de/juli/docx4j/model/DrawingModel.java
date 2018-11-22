package de.juli.docx4j.model;

import org.docx4j.wml.Drawing;

public class DrawingModel {
	private Drawing drawing;
	
	public DrawingModel(Drawing drawing) {
		super();
		this.drawing = drawing;
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}
}
