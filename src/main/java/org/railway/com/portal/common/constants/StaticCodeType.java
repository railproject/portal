package org.railway.com.portal.common.constants;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 * 定义返回报文中常用的返回码
 * @author join
 *
 */
public enum StaticCodeType {
	SYSTEM_SUCCESS				("0","成功"),
	SYSTEM_ERROR				("M01","系统错误"),
	SYSTEM_DATA_FORMAT_ERROR	("M02","不是合法的JSON数据"),
	SYSTEM_PARAM_LOST			("M03","缺少必要的请求参数"),
	SYSTEM_PARAM_TYPE_ERROR		("M04","请求参数类型错误"),
	SYSTEM_DATA_ISNULL  		("M05","获取数据为空");
	
	
	private String code;
	private String description;
	
	
	private static final Map<String, StaticCodeType> DICT = new HashMap<String, StaticCodeType>();
	private static final Map<String, StaticCodeType> DICT2 = new HashMap<String, StaticCodeType>();
	
	
	static {
		for (StaticCodeType item : values()) {
			DICT.put(item.code, item);
			DICT2.put(item.description, item);
			
		}
	}
	
	private StaticCodeType(String code,String description){
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}
	
	public String getDescription(){
		return description;
	}
	
	public static StaticCodeType parseCode(String code){
		try {
			
			StaticCodeType found = DICT.get(code);
			Validate.notNull(found, "The value of the role is null");
			return found;

		} catch (NullPointerException ex) {
			
			return null;
		}
		
	}
	
	public static StaticCodeType parseDescription(String description){
		try {
			StaticCodeType found = DICT2.get(description);
			Validate.notNull(found, "The value of the role is null");
			return found;

		} catch (NullPointerException ex) {
			
			return null;
		}
		
	}
}
