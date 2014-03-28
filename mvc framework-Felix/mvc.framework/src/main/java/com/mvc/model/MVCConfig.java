package com.mvc.model;

public class MVCConfig {

	private Settings settings;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	@Override
	public String toString() {
		return "MVCConfig [settings=" + settings + "]";
	}
	
}
