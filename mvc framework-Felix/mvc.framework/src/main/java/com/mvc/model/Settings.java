package com.mvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {

	private List<Setting> settingList=new ArrayList<Setting>();
	
	private Map<String, Setting> settingMap=new HashMap<String, Setting>();

	public List<Setting> getSettingList() {
		return settingList;
	}

	public void setSettingList(List<Setting> settingList) {
		this.settingList = settingList;
	}

	public Map<String, Setting> getSettingMap() {
		return settingMap;
	}

	public void setSettingMap(Map<String, Setting> settingMap) {
		this.settingMap = settingMap;
	}
	
}
