package com.mvc.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.intefaces.Interceptor;

public class Invoke implements Interceptor {

	private List<Interceptor> interceptors;

	private int index = 0;
	
	public Invoke(List<Interceptor> interceptors) {
		super();
		this.interceptors = interceptors;
	}

	public String invoke(Interceptor invoke, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String result=null;
		if (index == interceptors.size()) {
			Class actionClass = (Class) request.getAttribute("actionClass");
			Object object = request.getAttribute("actionObject");
			Method method=actionClass.getDeclaredMethod("execute");
			result=(String)method.invoke(object);
			System.out.println("invoke result:"+result);
			return result;
		}
		Interceptor interceptor = interceptors.get(index);
		index++;
		result=interceptor.invoke(invoke, request, response);

		return result;
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}

}
