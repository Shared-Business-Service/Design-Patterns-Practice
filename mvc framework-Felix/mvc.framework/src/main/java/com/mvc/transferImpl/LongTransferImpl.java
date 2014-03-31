package com.mvc.transferImpl;

import com.mvc.intefaces.TransferType;

public class LongTransferImpl implements TransferType {

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
		return clazz.cast(Long.parseLong(value.toString()));
	}

}
