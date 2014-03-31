package com.mvc.transferImpl;

import com.mvc.intefaces.TransferType;

public class BooleanTransferImpl implements TransferType{

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
			return clazz.cast(Boolean.parseBoolean(value.toString()));
	}

}
