package com.mvc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.mvc.exception.XMLParserException;
import com.mvc.model.Tag;

public abstract class XMLParserByOrder2 <T> extends XMLParserBasic<T>{
	private static final String XML_WRONG_HEAD = "wrong head of xml";
	private static final String XML_WRONG_FORMAT = "wrong format of xml";

	private String parseXMLHead(String pathname) throws XMLParserException,
			IOException {
		String xmlString = readFileAsString(pathname).trim();
		xmlString = trimBlank(xmlString);
//		System.out.println(xmlString);
		if (xmlString.startsWith("<?")) {
			int index = xmlString.indexOf("?>");
			if (index == -1) {
				XMLParserException exception = new XMLParserException(
						XML_WRONG_FORMAT);
				throw exception;
			}
			String xmlHead = xmlString.substring(2, index);
			xmlHead = xmlHead.replaceAll("\\s*=\\s*", "=");
			String[] attrs = xmlHead.split("\\s");
			if (!"xml".equalsIgnoreCase(attrs[0])) {
				XMLParserException exception = new XMLParserException(
						XML_WRONG_HEAD);
				throw exception;
			}
			for (String string : attrs) {
				if (string.contains("version")) {
					String[] strings = string.split("=");
					if (!"\"1.0\"".equals(strings[1])) {
						XMLParserException exception = new XMLParserException(
								XML_WRONG_FORMAT);
						throw exception;
					}
					;
				} else if (string.contains("encoding")) {
					String[] strings = string.split("=");
					String encoding = strings[1].substring(1,
							strings[1].length() - 1);
					xmlString = readFileAsString(pathname, encoding);
				}
			}
			xmlString = xmlString.substring(index + 2);
		}
		return xmlString;
	}

	public XMLParserByOrder2() {
		super();
	}

	private String trimBlank(String xmlString) {
		xmlString = xmlString.replaceAll("\\s*<", "<").replaceAll(">\\s*", ">");
		return xmlString;
	}

	private T parseXMLBody(String xmlString) {
		xmlString = trimBlank(xmlString);
//		System.out.println(xmlString);
		int length = xmlString.length();
		boolean startDocumentFlag = true;
		boolean startElementFlag=false;
		Tag tag=null;
		for (int i = 0; i < length; i++) {
			char c = xmlString.charAt(i);
			if (c == '<') {
				int index=0;
				for(int j=i;j<length;j++){
					if(xmlString.charAt(j)=='>'){
						index=j;
						break;
					}
				}
				if(xmlString.charAt(i+1)!='/'){
					tag=pickOutTag(xmlString, i+1, index);
					if (startDocumentFlag) {
						startDocument(tag);
						startDocumentFlag=false;
					}
					startElement(tag);
					startElementFlag=true;
				}else {
					tag=pickOutTag(xmlString, i+2, index);
					endElement(tag);
					if(xmlString.lastIndexOf("/")==i+1){
						endDocument(tag);
					}
					startElementFlag=false;
				}
			}
			if(c=='>'){
				if(i!=length-1&&startElementFlag){
					if(xmlString.charAt(i+1)!='<'){
						int index=0;
						for(int j=i;j<length;j++){
							if(xmlString.charAt(j)=='<'){
								index=j;
								break;
							}
						}
						String content=xmlString.substring(i+1,index);
						tag.setTagContent(content);
						elementContent(tag);
					}else {
						if(xmlString.charAt(i+2)=='/'){
							tag.setTagContent(null);
							elementContent(tag);
						}
					}
				}
			}
		}
		return config;
	}
	
	private Tag pickOutTag(String xmlString,int begin,int end){
		String tagString=xmlString.substring(begin,end).replaceAll("\\s*=\\s*", "=");
		String[] tagAttrs=tagString.split("\\s");
		Tag tag=new Tag();
		tag.setTagName(tagAttrs[0]);
		if (tagAttrs.length>1) {
			Map<String, String> attrMap=new HashMap<String, String>();
			for(int i=1;i<tagAttrs.length;i++){
				String attrCouple=tagAttrs[i];
				String attrName=attrCouple.split("=")[0];
				String attrValue=attrCouple.split("=")[1].replaceAll("\"", "");
				attrMap.put(attrName, attrValue);
			}
			tag.setTagAttrMap(attrMap);
		}
		return tag;
	}

	public String readFileAsString(String pathname) throws IOException {
		return readFileAsString(pathname, "UTF-8");
	}

	public String readFileAsString(String pathname, String encode)
			throws IOException {
		File file = new File(pathname);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), encode));
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		return stringBuffer.toString();
	}
	
	public T parseXML(String pathname) throws IOException,
		XMLParserException {
		String xmlString = parseXMLHead(pathname);
		return parseXMLBody(xmlString);
		//System.out.println(xmlString);
	}
	
	protected T config;
	
	protected abstract void elementContent(Tag tag);
	
	protected abstract void endDocument(Tag tag);

	protected abstract void endElement(Tag tag);

	protected abstract void startElement(Tag tag);

	protected abstract void startDocument(Tag tag);
}
