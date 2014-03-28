package com.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Settings {

	private List<Setting> settingList=new ArrayList<Setting>();

	public List<Setting> getSettingList() {
		return settingList;
	}

	public void setSettingList(List<Setting> settingList) {
		this.settingList = settingList;
	}
	
}
