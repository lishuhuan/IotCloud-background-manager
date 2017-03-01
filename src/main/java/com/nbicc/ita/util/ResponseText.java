package com.nbicc.ita.util;

import com.alibaba.fastjson.JSONObject;

public class ResponseText {
	
	public static JSONObject response(int code,String error,Object data){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result_code", code);
		jsonObject.put("error_msg", error);
		jsonObject.put("data", data);
		return jsonObject;
	}

}
