package test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.intefaces.Interceptor;
import com.mvc.service.Invoke;

public class TestForIntercepter implements Interceptor {

	@Override
	public String invoke(Interceptor invoke, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("TestForInterceptor: before invoke");
		String result=invoke.invoke(invoke, request, response);
		System.out.println("TestForInterceptor: after invoke");
		return result;
	}

}
