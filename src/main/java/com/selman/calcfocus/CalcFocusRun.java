package com.selman.calcfocus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import com.selman.calcfocus.factory.CalcFocusRequestBuilder;
import com.selman.calcfocus.response.CalculateResponse;
import com.selman.calcfocus.service.CalculateRequestService;

import edit.common.EDITDate;

public class CalcFocusRun {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		HashMap<String, Object> map = null;
	      try
	      {
	         FileInputStream fis = new FileInputStream("c:\\venus_map.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         map = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return;
	      }
	      
	      CalcFocusRequestBuilder builder = new CalcFocusRequestBuilder(null, map);
	      CalculateRequestService service = new CalculateRequestService();
	      //Object response = service.calcFocusRequest(builder.getReq());
	      

	}

}
