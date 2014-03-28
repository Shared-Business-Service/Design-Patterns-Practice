package com.mvc.model;

import java.util.Map;

public class Tag {

	private String tagName;
	private Map<String, String> tagAttrMap;
	private String tagContent;
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Map<String, String> getTagAttrMap() {
		return tagAttrMap;
	}
	public void setTagAttrMap(Map<String, String> tagAttrMap) {
		this.tagAttrMap = tagAttrMap;
	}
	public String getTagContent() {
		return tagContent;
	}
	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}
	@Override
	public String toString() {
		return "Tag [tagName=" + tagName + ", tagAttrMap=" + tagAttrMap
				+ ", tagContent=" + tagContent + "]";
	}
	
	
}
