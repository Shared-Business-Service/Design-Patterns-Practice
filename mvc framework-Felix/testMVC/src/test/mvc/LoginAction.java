package test.mvc;

import javax.servlet.http.HttpSession;

import com.mvc.intefaces.Action;

public class LoginAction implements Action{

	private String username;
	
	private String password;
	
	public String execute(){
		System.out.println("---------LoginAction---------");
		System.out.println("username: "+username);
		System.out.println("password: "+password);
		String result=null;
		if("felix".equals(username)&&"123".equals(password)){
			result="success";
		}else {
			result="failed";
		}
		return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
