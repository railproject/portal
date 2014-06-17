package org.railway.com.portal.web.dto;

import org.railway.com.portal.common.constants.StaticCodeType;

public class Result {

	private String code;

	private String message;

	private Object data;

	public Result(){
		this.code = StaticCodeType.SYSTEM_SUCCESS.getCode();
		this.message = StaticCodeType.SYSTEM_SUCCESS.getDescription();
	}
	public Result(String code,String message){
		this.code = code;
		this.message = message;
	}
	public Result(String code,String message,Object data){
		this(code,message);
		this.data = data;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	} 
	
	
}
