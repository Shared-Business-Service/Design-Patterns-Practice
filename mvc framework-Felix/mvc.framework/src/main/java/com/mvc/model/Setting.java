package com.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Setting {

	private List<Response> responseList=new ArrayList<Response>();
	
	private String name;
	
	private String classPath;

	public List<Response> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<Response> responseList) {
		this.responseList = responseList;
	}

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

	@Override
	public String toString() {
		return "Setting [responseList=" + responseList + ", name=" + name
				+ ", classPath=" + classPath + "]";
	}

}
