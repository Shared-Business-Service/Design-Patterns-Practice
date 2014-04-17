package com.mvc.util;

import java.util.List;
import java.util.Map;

import com.mvc.model.MVCConfig;
import com.mvc.model.Response;
import com.mvc.model.Setting;
import com.mvc.model.Settings;
import com.mvc.model.Tag;

public class XMLParserByOrder2ForMVCConfig extends XMLParserByOrder2<MVCConfig>{
	
	public void startDocument(Tag tag) {
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

		}else if("interceptor".equals(tagName)){
			Map<String, String> attrMap=tag.getTagAttrMap();
			List<Setting> settingList=config.getSettings().getSettingList();
			settingList.get(settingList.size()-1).getInterceptors().add(attrMap.get("name"));
		}

	}

	@Override
	public void endDocument(Tag tag) {

	}

	@Override
	public void endElement(Tag tag) {

	}

	@Override
	public void elementContent(Tag tag) {
		String tagName=tag.getTagName();
		if("response".equals(tagName)){
			List<Setting> settingList=config.getSettings().getSettingList();
			Map<String, Response> responseMap=settingList.get(settingList.size()-1).getResponseMap();
			Response response=new Response();
			response.setContent(tag.getTagContent());
			responseMap.put(tag.getTagAttrMap().get("name"), response);
		}

	}

}
