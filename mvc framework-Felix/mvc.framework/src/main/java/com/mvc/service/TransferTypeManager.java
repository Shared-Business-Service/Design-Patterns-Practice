package com.mvc.service;

import java.util.HashMap;
import java.util.Map;

import com.mvc.intefaces.TransferType;
import com.mvc.transferImpl.BooleanTransferImpl;
import com.mvc.transferImpl.ByteTransferImpl;
import com.mvc.transferImpl.CharTransferImpl;
import com.mvc.transferImpl.DoubleTransferImpl;
import com.mvc.transferImpl.FloatTransferImpl;
import com.mvc.transferImpl.IntegerTransterImpl;
import com.mvc.transferImpl.LongTransferImpl;
import com.mvc.transferImpl.ShortTransferImpl;
import com.mvc.transferImpl.StringTransferImpl;

public class TransferTypeManager {
	private  Map<Class<?>, TransferType> transferMap=new HashMap<Class<?>, TransferType>();
	
	{
		transferMap.put(Integer.class, new IntegerTransterImpl());
		transferMap.put(Boolean.class, new BooleanTransferImpl());
		transferMap.put(Byte.class, new ByteTransferImpl());
		transferMap.put(Character.class, new CharTransferImpl());
		transferMap.put(Double.class, new DoubleTransferImpl());
		transferMap.put(Float.class, new FloatTransferImpl());
		transferMap.put(Long.class, new LongTransferImpl());
		transferMap.put(Short.class, new ShortTransferImpl());
		transferMap.put(String.class, new StringTransferImpl());
	}
	
	public <T>T TransferType(Class<T> clazz, Object value){
		TransferType transferType=transferMap.get(clazz);
		return transferType.transfer(clazz, value);
	}
}









