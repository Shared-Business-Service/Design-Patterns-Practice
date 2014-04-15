package com.mvc.service;

import java.util.List;
import java.util.Map;

import com.mvc.model.MVCConfig;
import com.mvc.model.Response;
import com.mvc.model.Setting;
import com.mvc.model.Settings;
import com.mvc.model.Tag;
import com.mvc.util.XMLParserStrategy;

public class MVCConfigStrategy implements XMLParserStrategy {

	private MVCConfig config;
	
	@Override
	public void startDocument(Tag tag) {
//		System.out.println("startDocument");
		config=new MVCConfig();

	}

	@Override
	public void startElement(Tag tag) {
		String tagName=tag.getTagName();
		if("settings".equals(tagName)){
			Settings settings=new Settings();
			config.setSettings(settings);
		}else if("setting".equals(tagName)){
			Setting setting=new Setting();
			Map<String, String> attrMap=tag.getTagAttrMap();
			setting.setName(attrMap.get("name"));
			setting.setClassPath(attrMap.get("class"));
			config.getSettings().getSettingList().add(setting);
		}else if("response".equals(tagName)){
//			Response response=new Response();
//			Map<String, String> attrMap=tag.getTagAttrMap();
//			response.setName(attrMap.get("name"));
//			List<Setting> settingList=config.getSettings().getSettingList();
//			settingList.get(settingList.size()-1).getResponseList().add(response);
		}else if("interceptor".equals(tagName)){
			Map<String, String> attrMap=tag.getTagAttrMap();
			List<Setting> settingList=config.getSettings().getSettingList();
			settingList.get(settingList.size()-1).getInterceptors().add(attrMap.get("name"));
		}

	}

	@Override
	public void endDocument(Tag tag) {
//		System.out.println("endDocument");

	}

	@Override
	public void endElement(Tag tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elementContent(Tag tag) {
		String tagName=tag.getTagName();
//		System.out.println("--------"+tag.getTagContent()+"--------");
//		System.out.println("tag.getTagAttrMap().get(\"name\"): "+tag.getTagAttrMap().get("name"));
		if("response".equals(tagName)){
			List<Setting> settingList=config.getSettings().getSettingList();
			Map<String, Response> responseMap=settingList.get(settingList.size()-1).getResponseMap();
			Response response=new Response();
			response.setContent(tag.getTagContent());
			responseMap.put(tag.getTagAttrMap().get("name"), response);
		}

	}

	public MVCConfig getConfig() {
		return config;
	}

	public void setConfig(MVCConfig config) {
		this.config = config;
	}

}
