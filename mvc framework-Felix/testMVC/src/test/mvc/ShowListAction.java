package test.mvc;

import com.mvc.intefaces.Action;

public class ShowListAction implements Action{

	@Override
	public String execute() {
		
		return "success";
	}

}
