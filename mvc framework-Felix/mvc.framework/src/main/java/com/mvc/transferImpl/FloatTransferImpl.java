package com.mvc.transferImpl;

import sun.misc.FloatingDecimal;

import com.mvc.intefaces.TransferType;

public class FloatTransferImpl implements TransferType {

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
		// TODO Auto-generated method stub
		return clazz.cast(Float.parseFloat(value.toString()));
	}

}
