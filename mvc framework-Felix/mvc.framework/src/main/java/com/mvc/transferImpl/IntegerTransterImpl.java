package com.mvc.transferImpl;

import com.mvc.intefaces.TransferType;

public class IntegerTransterImpl implements TransferType{

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
		// TODO Auto-generated method stub
		return clazz.cast(Integer.parseInt(value.toString()));
	}

}
