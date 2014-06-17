package org.railway.com.portal.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ExcelUtil<T> {
	private LinkedHashMap<String, String> propertyMapping;
	private HSSFSheet sheet;
	protected Class<T> entityClass;
	private Map<String, Short> map;
	private PropertyDescriptor[] propertyDescriptors;
	private DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	private Map<String, Map<String, String>> valueMapping;
	
	public ExcelUtil(LinkedHashMap<String, String> propertyMapping, HSSFSheet sheet, Class<T> c){
		this.propertyMapping = propertyMapping;
		this.map = new HashMap<String, Short>();
		Short i = 0;
		for(String e : propertyMapping.keySet()){
			this.map.put(e, i++);
			//System.out.println(e);
			//System.out.println("propertyMapping.keySet()"+ propertyMapping.keySet());
		}
		this.sheet = sheet;
		entityClass = c;
			try {
				BeanInfo beaninfo = Introspector.getBeanInfo(c);
				this.propertyDescriptors = beaninfo.getPropertyDescriptors();
			/*	int num_pro = propertyDescriptors.length;
				for(int p = 0 ; p < num_pro; p++){
					System.out.println("propertyDescriptors[" + p +"]=" + propertyDescriptors[p]);
				}*/
			} catch (java.beans.IntrospectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	
	public List<T> getEntities(int headIndex){
		List<T> list = new LinkedList<T>();
		int rows = sheet.getPhysicalNumberOfRows();
		HSSFRow headRow = null;
		if(headIndex == -1){
			headIndex = getHeadIndex();
			if(headIndex != -1){
				headRow = sheet.getRow(headIndex);
			}
		}
		//System.out.println("sheet.getRow(headIndex)="+sheet.getRow(headIndex));
		Short[] st = getStatus(headRow);
		for(int i = 3 + 1 ; i < rows; i++){
			list.add(getEntity(sheet.getRow(i), st));
		}
		return list;
	}
	
	private int getHeadIndex(){
		int rows = sheet.getPhysicalNumberOfRows();
		int like = 0;
		for(int i = 0; i < rows; i++){
			HSSFRow row = sheet.getRow(i);
			int cells = row.getPhysicalNumberOfCells();
			for(short j = 0; j < cells; j++){
				HSSFCell cell = row.getCell(j);
				//System.out.println("CellType:" + cell.getCellType());
				if(cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING && propertyMapping.values().contains(cell.getStringCellValue())){
					like++;
				}
				if(like > map.size()/2){
					return i;
				}			
			}
		}
		return -1;
	}
	
	private Short[] getStatus(HSSFRow row){
		row = null;
		Short[] status = new Short[map.size()];
		//System.out.println("map.size():" + map.size());
		if(row == null){
			for(String key : map.keySet()){
				status[map.get(key)] = map.get(key);
				//System.out.println("key:" + key);
				//System.out.println("map.get(key):" + map.get(key));
				/*for (int i = 0; i < status.length; i++){
					System.out.println("status["+i+"]="+status[i]);
				}*/
			}
		}
//		else{
//			int cells = row.getPhysicalNumberOfCells();
//			for(short j = 0; j < cells; j++){
//				//System.out.println("row.getCell[" + j + "]=" + row.getCell(j));
//				if(row.getCell(j) != null){
//					//System.out.println("row.getCell(" + j + ").getCellType=" + row.getCell(j).getCellType());
//					//System.out.println("HSSFCell.CELL_TYPE_STRING=" + HSSFCell.CELL_TYPE_STRING);
//					switch (row.getCell(j).getCellType()){
//					case HSSFCell.CELL_TYPE_STRING :
//						for(String key : propertyMapping.keySet()){
//							//System.out.println("propertyMapping.get(" + key + ")=" +propertyMapping.get(key));
//							//System.out.println("row.getCell("+j+").getStringCellValue()"+row.getCell(j).getStringCellValue());
//							if(propertyMapping.get(key).equals(row.getCell(j).getStringCellValue())){
//								status[map.get(key)] = j;
//								/*for (int i = 0; i < status.length; i++){
//									System.out.println("status["+i+"]="+status[i]);
//								}*/
//							}
//						}
//						break;
//					default:
//						break;		
//					}
//				}
//			}
//		}
		return status;
	}
	
	private T getEntity(HSSFRow row, Short[] status){ 
		T t = null;
			try {
				t = entityClass.newInstance();
				for(int i = 0; i < propertyDescriptors.length; i++){
					try{
						PropertyDescriptor pd = propertyDescriptors[i];
						String propertyName = pd.getName();
//						System.out.println(" pd.getName()="+ propertyName); 
						 
						if(propertyMapping.keySet().contains(propertyName)){
							Method method = pd.getWriteMethod();
//							System.out.println("===" + status[map.get(propertyName)]);
							HSSFCell cell = row.getCell(status[map.get(propertyName)]);
							Object value = null;
							if(cell != null){
								switch(cell.getCellType()){
								case HSSFCell.CELL_TYPE_NUMERIC : 
//									value = cell.getNumericCellValue();
									BigDecimal result = new BigDecimal(cell.getNumericCellValue());
									if(pd.getPropertyType() == Integer.class || pd.getPropertyType() == int.class){
										value = result.intValue();
									}else if(pd.getPropertyType() == Float.class || pd.getPropertyType() == float.class){
										value = result.floatValue();
									}else if(pd.getPropertyType() == Double.class || pd.getPropertyType() == double.class){
										value = cell.getNumericCellValue();
									}else if(pd.getPropertyType() == Long.class || pd.getPropertyType() == long.class){
										value = result.longValue();
									}else if(pd.getPropertyType() == Short.class || pd.getPropertyType() == short.class){
										value = result.shortValue();
									}else if(pd.getPropertyType() == String.class){
										value = result.toString();
									} 
									break;
								case HSSFCell.CELL_TYPE_STRING :
									String resultStr = cell.getStringCellValue() == null ? "" : cell.getStringCellValue().trim();
									if(pd.getPropertyType() == Integer.class || pd.getPropertyType() == int.class){
										value = Integer.parseInt(resultStr);
									}else if(pd.getPropertyType() == Float.class || pd.getPropertyType() == float.class){
										value = Float.parseFloat(resultStr);
									}else if(pd.getPropertyType() == Double.class || pd.getPropertyType() == double.class){
										value = Double.parseDouble(resultStr);
									}else if(pd.getPropertyType() == Long.class || pd.getPropertyType() == long.class){
										value = Long.parseLong(resultStr);
									}else if(pd.getPropertyType() == Short.class || pd.getPropertyType() == short.class){
										value = Short.parseShort(resultStr);
									}else if(pd.getPropertyType() == String.class){
										 value = resultStr;
									} 
									
									break;
								default :
									break;
								}	
							}
							String type = pd.getPropertyType().getName();
	//					    System.out.println("------type:" + type);
							if(type.endsWith("Date") && value != null){	 
									value = format.parseObject(String.valueOf(value));							
							}
							
							if(valueMapping != null && valueMapping.containsKey(propertyName)){
								Map<String, String> values = valueMapping.get(propertyName);
								if(values.containsValue(value)){
									for(String key : values.keySet()){
										if(value.equals(values.get(key))){
											value = key;
											break;
										}
									}
								}
							}		
//							System.out.println("value==" + value);
							//System.out.println(value.getClass().getName());  
							if(value != null){ 
								method.invoke(t, value);	
							} 
						}
					}catch(Exception e){
//						e.printStackTrace();
					}
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return t;				
	}

	public LinkedHashMap<String, String> getPropertyMapping() {
		return propertyMapping;
	}

	public void setPropertyMapping(LinkedHashMap<String, String> propertyMapping) {
		this.propertyMapping = propertyMapping;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Map<String, Short> getMap() {
		return map;
	}

	public void setMap(Map<String, Short> map) {
		this.map = map;
	}

	public PropertyDescriptor[] getPropertyDescriptors() {
		return propertyDescriptors;
	}

	public void setPropertyDescriptors(PropertyDescriptor[] propertyDescriptors) {
		this.propertyDescriptors = propertyDescriptors;
	}

	public DateFormat getFormat() {
		return format;
	}

	public void setFormat(DateFormat format) {
		this.format = format;
	}

	public Map<String, Map<String, String>> getValueMapping() {
		return valueMapping;
	}

	public void setValueMapping(Map<String, Map<String, String>> valueMapping) {
		this.valueMapping = valueMapping;
	}
	
}
