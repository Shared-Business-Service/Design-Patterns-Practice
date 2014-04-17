package com.mvc.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.intefaces.Interceptor;
import com.mvc.service.Invoke;

public class InterceptorProxy implements InvocationHandler, Interceptor {

	private Object proxy;
	
	private List<Interceptor> interceptors;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;

	private int index = 0;
	
	private Object proxyTarget;
	
	private Method method;

	private Object[] args;
	
	
	public InterceptorProxy(List<Interceptor> interceptors,HttpServletRequest request,
			HttpServletResponse response) {
		super();
		this.interceptors = interceptors;
		this.request=request;
		this.response=response;
	}
	
	public Object proxy(Object target){
		proxy=target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		this.proxyTarget=proxy;
		this.method=method;
		this.args=args;
		String result = invoke(this, request, response);
		return result;
	}

	public String invoke(Interceptor invoke, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String result=null;
		if (index == interceptors.size()) {
			try {
				result = (String)method.invoke(proxy, args);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			System.out.println("invoke result:"+result);
			return result;
		}
		Interceptor interceptor = interceptors.get(index);
		index++;
		result=interceptor.invoke(invoke, request, response);

		return result;
	}

}
