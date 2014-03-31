package com.mvc.transferImpl;

import com.mvc.intefaces.TransferType;

public class ShortTransferImpl implements TransferType {

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
		return clazz.cast(Short.parseShort(value.toString()));
	}

}
