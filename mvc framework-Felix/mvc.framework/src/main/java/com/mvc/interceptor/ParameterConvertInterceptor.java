package com.mvc.interceptor;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.intefaces.Interceptor;
import com.mvc.service.Invoke;
import com.mvc.service.TransferTypeManager;

public class ParameterConvertInterceptor implements Interceptor{

	@Override
	public String invoke(Invoke invoke, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Class actionClass = (Class) request.getAttribute("actionClass");
		Object object = request.getAttribute("actionObject");
		prepareParameters(request, actionClass, object);
		System.out.println("ParameterConvertInterceptor: before invoke");
		String result=invoke.invoke(invoke, request, response);
		System.out.println("ParameterConvertInterceptor: after invoke");
		return result;
	}
	private void prepareParameters(HttpServletRequest req,Class actionClass,Object object){
		System.out.println("------------prepareParameters()-------------");
		Map<String, String[]> parameterMap=req.getParameterMap();
		TransferTypeManager manager=new TransferTypeManager();
		Iterator<Entry<String, String[]>> iterator=parameterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> iEntry=iterator.next();
			String paramName=iEntry.getKey();
			String[] paramValues=iEntry.getValue();
			System.out.println("paramName: "+paramName);
			System.out.println("paramValues: "+paramValues[0]);
			try {
				Field field=actionClass.getDeclaredField(paramName);
				field.setAccessible(true);
				Class fieldClass=field.getType();
				field.set(object, manager.TransferType(fieldClass, paramValues[0]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
