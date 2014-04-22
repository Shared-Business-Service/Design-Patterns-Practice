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

import com.mvc.intefaces.Action;
import com.mvc.intefaces.Interceptor;
import com.mvc.intefaces.TransferType;
import com.mvc.intefaces.XMLParser;
import com.mvc.interceptor.InterceptorProxy;
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
import com.mvc.util.Utils;
import com.mvc.util.XMLParserBasic;
import com.mvc.util.XMLParserByOrder;
import com.mvc.util.XMLParserByOrder2;
import com.mvc.util.XMLParserByOrder2ForMVCConfig;
import com.mvc.util.XMLParserFactory;

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
						Action action=(Action)actionClass.newInstance();
						req.setAttribute("actionClass", actionClass);
						req.setAttribute("actionObject", action);
						//prepare parems
//						prepareParameters(req, actionClass, object);
						
						List<Interceptor> interceptors=new ArrayList<Interceptor>();
						interceptors.add(new ParameterConvertInterceptor());
						rigUpInterceptor(interceptors,setting.getInterceptors());
						String returnValue=null;
						//dynamic proxy
						InterceptorProxy proxy=new InterceptorProxy(interceptors, req, resp);
						action=(Action) proxy.proxy(action);
//						Method method=actionClass.getDeclaredMethod("execute");
						returnValue=action.execute();
						//recursion
//						Invoke invoke=new Invoke(interceptors);
//						String returnValue=invoke.invoke(invoke, req, resp);
						System.out.println("-------CoreServlet returnValue------------ "+returnValue);
						Map<String, Response> responseMap=setting.getResponseMap();
						Response response=responseMap.get(returnValue);
						System.out.println("responseMap.get(returnValue): "+response);
						if(response!=null&&response.getContent()!=null){
							System.out.println("Dispatcher: "+response.getContent()+" | actionClass.getSimpleName():"+actionClass.getSimpleName());
							req.setAttribute(actionClass.getSimpleName(), action);
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
		//bridge
//		MVCConfigStrategy strategy=new MVCConfigStrategy();
//		XMLParser parser=new XMLParserByOrder(strategy);
		
		XMLParserFactory factory=null;
		try {
			String factoryPath=Utils.getConfig().getProperty("XMLParserFactory");
			System.out.println("factoryPath==null: "+(factoryPath==null));
			if(factoryPath==null){
				factoryPath="com.mvc.util.XMLParserFactoryForMVCConfig";
			}
			factory = (XMLParserFactory) Class.forName(factoryPath).newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		//template
		XMLParserBasic parser2=factory.createXMLParserBasic();
		
		String projectPath = parser2.getClass().getResource("/").getPath();
		MVCConfig config=null;
		try {
//			parser.parseXML(projectPath + "resources.xml");
//			config=strategy.getConfig();
			config=(MVCConfig) parser2.parseXML(projectPath + "resources.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
	
	
	
	public void destroy() {
		super.destroy();
	}

	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}

	public static void main(String[] args) throws Exception {
		MVCConfigStrategy strategy=new MVCConfigStrategy();
		XMLParserByOrder parser=new XMLParserByOrder(strategy);
		String projectPath = parser.getClass().getResource("/").getPath();
		MVCConfig config=null;
//		try {
//			parser.parseXML(projectPath + "resources.xml");
//			config=strategy.getConfig();
//			System.out.println(config);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String string="login.action";
//		String[] actions=string.split("\\.");
//		System.out.println(actions.length);
//		Class clazz=Class.forName("com.mvc.service.CoreServlet");
//		System.out.println(clazz.getSimpleName());
		XMLParserBasic<MVCConfig> parser2=new XMLParserByOrder2ForMVCConfig();
		projectPath = parser2.getClass().getResource("/").getPath();
		config=parser2.parseXML(projectPath + "resources.xml");
		System.out.println(config);
	}
}
