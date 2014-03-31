package com.mvc.transferImpl;

import com.mvc.intefaces.TransferType;

public class CharTransferImpl implements TransferType {

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
		
		return clazz.cast(value.toString().charAt(0));
	}

}
