package test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.model.User;

import com.mvc.intefaces.Interceptor;

public class LoginCheckIntercepter implements Interceptor {

	@Override
	public String invoke(Interceptor arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
		HttpSession session  = arg1.getSession();
		User user=(User) session.getAttribute("user");
		String result="NoLogin";
		System.out.println("-------------------"+user+"-------------------------");
		if(user!=null){
			System.out.println("user.getUsername(): "+user.getUsername());
			System.out.println("user.getPassword(): "+user.getPassword());
			result=arg0.invoke(arg0, arg1, arg2);
		}
		
		return result;
	}

}
