import java.util.HashMap;
import java.util.Map;

import com.mvc.intefaces.TransferType;
import com.mvc.service.TransferTypeManager;
import com.mvc.transferImpl.BooleanTransferImpl;
import com.mvc.transferImpl.ByteTransferImpl;
import com.mvc.transferImpl.CharTransferImpl;
import com.mvc.transferImpl.DoubleTransferImpl;
import com.mvc.transferImpl.FloatTransferImpl;
import com.mvc.transferImpl.IntegerTransterImpl;
import com.mvc.transferImpl.LongTransferImpl;
import com.mvc.transferImpl.ShortTransferImpl;
import com.mvc.transferImpl.StringTransferImpl;


public class test {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(Integer.class==Integer.class);
		TransferTypeManager manager=new TransferTypeManager();
		System.out.println(manager.TransferType(Character.class, "sb"));
		
//		System.out.println(Integer.TYPE.equals(int.class));
//		System.out.println(Integer.TYPE.equals(Integer.class));
	}

}
