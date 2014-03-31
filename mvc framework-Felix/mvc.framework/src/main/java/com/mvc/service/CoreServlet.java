package com.mvc.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.intefaces.TransferType;
import com.mvc.model.MVCConfig;
import com.mvc.model.Response;
import com.mvc.model.Setting;
import com.mvc.model.Settings;
import com.mvc.transferImpl.BooleanTransferImpl;
import com.mvc.transferImpl.ByteTransferImpl;
import com.mvc.transferImpl.CharTransferImpl;
import com.mvc.transferImpl.DoubleTransferImpl;
import com.mvc.transferImpl.FloatTransferImpl;
import com.mvc.transferImpl.IntegerTransterImpl;
import com.mvc.transferImpl.LongTransferImpl;
import com.mvc.transferImpl.ShortTransferImpl;
import com.mvc.transferImpl.StringTransferImpl;
import com.mvc.util.XMLParser;

public class CoreServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1923531759315166142L;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//parse uri
		String uri=req.getRequestURI();
		int index=uri.lastIndexOf("/");
		String actionString=uri.substring(index);
		String[] actions=actionString.split(".");
		String reqAction=actions[0];
		
		//parse xml document
		MVCConfig config=prepareMvcConfig();
		//
		Settings settings=config.getSettings();
		if(settings!=null){
			List<Setting> settingList=settings.getSettingList();
			for(Setting setting:settingList){
				String settingName=setting.getName();
				if(settingName.equals(reqAction)){
					String classPath=setting.getClassPath();
					try {
						Class actionClass=Class.forName(classPath);
						Object object=actionClass.newInstance();
						//prepare parems
						prepareParameters(req, actionClass, object);
						
						Method method=actionClass.getDeclaredMethod("execute");
						String returnValue=(String)method.invoke(object);
						Map<String, Response> responseMap=setting.getResponseMap();
						Response response=responseMap.get(returnValue);
						if(response!=null&&response.getContent()!=null){
							req.setAttribute("action", object);
							req.getRequestDispatcher(response.getContent());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private MVCConfig prepareMvcConfig(){
		MVCConfigStrategy strategy=new MVCConfigStrategy();
		XMLParser parser=new XMLParser(strategy);
		String projectPath = parser.getClass().getResource("/").getPath();
		MVCConfig config=null;
		try {
			parser.parseXML(projectPath + "resources.xml");
			config=strategy.getConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
	
	private void prepareParameters(HttpServletRequest req,Class actionClass,Object object){
		Map<String, String[]> parameterMap=req.getParameterMap();
		TransferTypeManager manager=new TransferTypeManager();
		Iterator<Entry<String, String[]>> iterator=parameterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> iEntry=iterator.next();
			String paramName=iEntry.getKey();
			String[] paramValues=iEntry.getValue();
			try {
				Field field=actionClass.getField(paramName);
				field.setAccessible(true);
				Class fieldClass=field.getType();
				field.set(object, manager.TransferType(fieldClass, paramValues[0]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	public static void main(String[] args) {
		MVCConfigStrategy strategy=new MVCConfigStrategy();
		XMLParser parser=new XMLParser(strategy);
		String projectPath = parser.getClass().getResource("/").getPath();
		try {
			parser.parseXML(projectPath + "resources.xml");
			MVCConfig config=strategy.getConfig();
			System.out.println(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
