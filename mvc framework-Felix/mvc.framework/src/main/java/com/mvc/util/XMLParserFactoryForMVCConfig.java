package com.mvc.util;

public class XMLParserFactoryForMVCConfig implements XMLParserFactory{

	@Override
	public XMLParserBasic createXMLParserBasic() {
		return new XMLParserByOrder2ForMVCConfig();
	}

}
