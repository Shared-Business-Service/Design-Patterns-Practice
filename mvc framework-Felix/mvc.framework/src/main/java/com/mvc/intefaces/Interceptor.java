package com.mvc.intefaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.service.Invoke;

public interface Interceptor {
	
	public String invoke(Interceptor invoke, HttpServletRequest request, HttpServletResponse response )throws Exception;
	
}













