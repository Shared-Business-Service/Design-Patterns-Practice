package com.mvc.transferImpl;

import sun.misc.FloatingDecimal;

import com.mvc.intefaces.TransferType;

public class DoubleTransferImpl implements TransferType{

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
		return clazz.cast(Double.parseDouble(value.toString()));
	}

}
