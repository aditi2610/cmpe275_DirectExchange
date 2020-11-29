package com.example.demo.Common;

import org.json.JSONObject;

public class CommonUtilities {
	public static String getErrorMessage(String header, String code, String message){
		JSONObject result = new JSONObject();
		JSONObject error = new JSONObject();	
		try{
			result.put(header, error);
			error.put("Error Code", code);
			error.put("Error Message", message);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result.toString();
	}
}
