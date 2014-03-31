package com.mvc.transferImpl;

import com.mvc.intefaces.TransferType;

public class ByteTransferImpl implements TransferType {

	@Override
	public <T> T transfer(Class<T> clazz, Object value) {
		// TODO Auto-generated method stub
		Integer b=Integer.parseInt(value.toString(), 10);
		return clazz.cast(Byte.parseByte(value.toString()));
	}

}
