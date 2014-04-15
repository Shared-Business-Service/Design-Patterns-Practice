package com.mvc.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.intefaces.Interceptor;
import com.mvc.intefaces.TransferType;
import com.mvc.interceptor.ParameterConvertInterceptor;
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

	private static MVCConfig config;
	
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//parse uri
		String uri=req.getRequestURI();
		System.out.println("uri: "+uri);
		int index=uri.lastIndexOf("/");
		String actionString=uri.substring(index+1);
		System.out.println(actionString);
		String[] actions=actionString.split("\\.");
//		System.out.println(actions.length);
		String reqAction=actions[0];
		
		//parse xml document
		if(config==null){
			config=prepareMvcConfig();
		}
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
						req.setAttribute("actionClass", actionClass);
						req.setAttribute("actionObject", object);
						//prepare parems
//						prepareParameters(req, actionClass, object);
						
//						Method method=actionClass.getDeclaredMethod("execute");
//						String returnValue=(String)method.invoke(object);
						List<Interceptor> interceptors=new ArrayList<Interceptor>();
						interceptors.add(new ParameterConvertInterceptor());
						rigUpInterceptor(interceptors,setting.getInterceptors());
						Invoke invoke=new Invoke(interceptors);
						String returnValue=invoke.invoke(invoke, req, resp);
						System.out.println("-------CoreServlet returnValue------------ "+returnValue);
						Map<String, Response> responseMap=setting.getResponseMap();
						Response response=responseMap.get(returnValue);
						System.out.println("responseMap.get(returnValue): "+response);
						if(response!=null&&response.getContent()!=null){
							System.out.println("Dispatcher: "+response.getContent());
							req.setAttribute(actionClass.getSimpleName(), object);
							req.getRequestDispatcher(response.getContent()).forward(req, resp);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void rigUpInterceptor(List<Interceptor> interceptors,List<String> strings) throws Exception{
		for(String string:strings){
			Class interceptorClass=Class.forName(string);
			Object interceptor=interceptorClass.newInstance();
			interceptors.add((Interceptor)interceptor);
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

	public static void main(String[] args) throws Exception {
		MVCConfigStrategy strategy=new MVCConfigStrategy();
		XMLParser parser=new XMLParser(strategy);
		String projectPath = parser.getClass().getResource("/").getPath();
		MVCConfig config=null;
		try {
			parser.parseXML(projectPath + "resources.xml");
			config=strategy.getConfig();
			System.out.println(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String string="login.action";
		String[] actions=string.split("\\.");
		System.out.println(actions.length);
//		Class clazz=Class.forName("com.mvc.service.CoreServlet");
//		System.out.println(clazz.getSimpleName());
	}
}
