package test.mvc;

import com.mvc.intefaces.Action;

public class LoginAction implements Action{

	private String username;
	
	private String password;
	
	public String execute(){
		System.out.println("---------LoginAction---------");
		System.out.println("username: "+username);
		System.out.println("password: "+password);
		return "success";
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
