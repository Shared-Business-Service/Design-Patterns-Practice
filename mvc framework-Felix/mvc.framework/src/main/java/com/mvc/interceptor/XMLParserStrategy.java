package com.mvc.interceptor;

import com.mvc.model.Tag;

public interface XMLParserStrategy {

	public void startDocument(Tag tag);
	
	public void startElement(Tag tag);
	
	public void endDocument(Tag tag);
	
	public void endElement(Tag tag);
	
	public void elementContent(Tag tag);
	
}
