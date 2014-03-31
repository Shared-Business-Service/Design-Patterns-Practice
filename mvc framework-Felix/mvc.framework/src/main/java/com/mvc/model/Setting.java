package com.mvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setting {

	private String name;
	
	private String classPath;

	private Map<String, Response> responseMap=new HashMap<String, Response>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public Map<String, Response> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, Response> responseMap) {
		this.responseMap = responseMap;
	}

	@Override
	public String toString() {
		return "Setting [name=" + name + ", classPath=" + classPath
				+ ", responseMap=" + responseMap + "]";
	}


}
