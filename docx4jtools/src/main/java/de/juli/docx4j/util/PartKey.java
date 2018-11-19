package de.juli.docx4j.util;

import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.parts.PartName;

public enum PartKey {
	FONT_TABLE("/word/fontTable.xml"),
	CORE("/docProps/core.xml"),
	DOCUMENT("/word/document.xml"),
	HEADER1("/word/header1.xml"),
	FOOTER1("/word/footer1.xml"),
	SETTINGS("/word/settings.xml"),
	APP("/docProps/app.xml"),
	styles("/word/styles.xml");

	private String key = "";

	PartKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public static PartName getPartName(PartKey key) {
		try {
			return new PartName(key.getKey());
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
}
