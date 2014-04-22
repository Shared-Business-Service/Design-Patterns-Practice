package test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.model.User;
import test.mvc.LoginAction;

import com.mvc.intefaces.Interceptor;

public class LoginLogIntercepter implements Interceptor {

	@Override
	public String invoke(Interceptor arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
		String result=arg0.invoke(arg0, arg1, arg2);
		if("success".equals(result)){
			HttpSession session=arg1.getSession();
			User user=new User();
			LoginAction action=(LoginAction) arg1.getAttribute("actionObject");
			user.setUsername(action.getUsername());
			user.setPassword(action.getPassword());
			session.setAttribute("user", user);
		}
		return result;
	}

}
