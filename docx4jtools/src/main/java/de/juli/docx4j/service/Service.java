package de.juli.docx4j.service;

import java.nio.file.Path;

import de.juli.docx4j.service.services.docx.Docx4JService;

public abstract class Service {

	protected Path source;
	protected Docx4JService docxService;

	public Service(Path path) throws Exception{
		super();
		if(null != path){
			this.source = path;
			this.docxService =  Docx4JService.getInstance(path);			
		}
	}

	public Path getSource() {
		return source;
	}

	public void setSource(Path source) {
		this.source = source;
	}

	public Docx4JService getService() {
		return docxService;
	}

	public void setService(Docx4JService service) {
		this.docxService = service;
	}
	
}
