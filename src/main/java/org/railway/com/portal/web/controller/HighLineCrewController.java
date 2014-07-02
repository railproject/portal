package org.railway.com.portal.web.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.shiro.SecurityUtils;
import org.railway.com.portal.common.constants.StaticCodeType;
import org.railway.com.portal.common.utils.DateUtil;
import org.railway.com.portal.common.utils.StringUtil;
import org.railway.com.portal.entity.HighLineCrewInfo;
import org.railway.com.portal.entity.QueryResult;
import org.railway.com.portal.service.HighLineCrewService;
import org.railway.com.portal.service.ShiroRealm;
import org.railway.com.portal.service.dto.PagingResult;
import org.railway.com.portal.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Maps;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

/**
 * Created by speeder on 2014/6/27.
 */
@RestController
@RequestMapping(value = "/crew/highline")
public class HighLineCrewController {

    private static Log logger = LogFactory.getLog(HighLineCrewController.class);

    @Autowired
    private HighLineCrewService highLineCrewService;

    @RequestMapping(value = "getHighLineCrew", method = RequestMethod.POST)
    public Result getHighLineCrew(@RequestBody Map<String,Object> reqMap) {
        logger.debug("getHighLineCrew:::::::");
        Result result = new Result(); 
        try{
        	Map<String, Object> params = Maps.newHashMap();
            params.put("crewHighlineId", StringUtil.objToStr(reqMap.get("crewHighLineId")));
            HighLineCrewInfo highLineCrewInfo = highLineCrewService.findHighLineCrew(params);	
            result.setData(highLineCrewInfo);
        }catch(Exception e){
        	logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());
        }
        
        return result;
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Result getHighLineCrewList(@RequestBody Map<String,Object> reqMap) {
        logger.debug("getHighLineCrewList:::::::");
        Result result = new Result(); 
        try{
        
        	String crewDate = StringUtil.objToStr(reqMap.get("crewDate"));
        	//将时间格式：yyyy-MM-dd转换成yyyyMMdd
        	crewDate = DateUtil.getFormateDayShort(crewDate);
        	String crewType = StringUtil.objToStr(reqMap.get("crewType"));
        	
             List<HighLineCrewInfo> list = highLineCrewService.findList(crewDate,crewType);
             result.setData(list);
        }catch(Exception e){
        	logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());
        }
       
        return result;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result addHighLineCrewInfo(@RequestBody HighLineCrewInfo highLineCrewInfo) {
        logger.debug("addHighLineCrewInfo:::::::");
        Result result = new Result(); 
        try{
        	ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();
			 highLineCrewInfo.setCrewHighlineId(UUID.randomUUID().toString());
			 String crewDate = DateUtil.getFormateDayShort(highLineCrewInfo.getCrewDate());
			 highLineCrewInfo.setCrewDate(crewDate);
			 //所属局简称
			 highLineCrewInfo.setCrewBureau(user.getBureauShortName());
			 highLineCrewInfo.setRecordPeople(user.getName());
			 highLineCrewInfo.setRecordPeopleOrg(user.getDeptName());
			 highLineCrewService.addCrew(highLineCrewInfo);
        }catch(Exception e){
        	logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());
        }
        return result;
        
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result updateHighLineCrewInfo(@RequestBody HighLineCrewInfo highLineCrewInfo) {
        logger.debug("updateHighLineCrewInfo:::::::" + highLineCrewInfo.getCrewDate() + "|" + highLineCrewInfo.getCrewCross());
        Result result = new Result(); 
        try{
        	ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();
        	//所属局简称
        	highLineCrewInfo.setCrewBureau(user.getBureauShortName());
        	highLineCrewInfo.setRecordPeople(user.getName());
        	highLineCrewInfo.setRecordPeopleOrg(user.getDeptName());
        	String crewDate = DateUtil.getFormateDayShort(highLineCrewInfo.getCrewDate());
            highLineCrewInfo.setCrewDate(crewDate);
        	highLineCrewService.update(highLineCrewInfo);
        }catch(Exception e){
        	logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());
        }
        return result;

    }

    /**
     * 批量删除highline_crew表中数据
     * @param reqMap
     * @return
     */
    @RequestMapping(value = "deleteHighLineCrewInfo", method = RequestMethod.DELETE)
    public Result deleteHighLineCrewInfo(@RequestBody Map<String,Object> reqMap ) {
        logger.debug("deleteHighLineCrewInfo:::::::");
        Result result = new Result(); 
        try{
            String crewHighLineId = StringUtil.objToStr(reqMap.get("crewHighLineId"));
            logger.debug("crewHighLineId==" + crewHighLineId);
            highLineCrewService.delete(crewHighLineId);
        }catch(Exception e){
        	logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());
        }
        return result;
    }
    
    /**
     * 更新字段submitType字段的值为1
     * @param reqMap
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "updateSubmitType", method = RequestMethod.POST)
    public Result updateSubmitType(@RequestBody Map<String,Object> reqMap){
    	Result result = new Result(); 
    	try{
    		logger.debug("updateSubmitType~~~~~reqMap="+reqMap);
    		String crewDate = StringUtil.objToStr(reqMap.get("crewDate"));
    		crewDate = DateUtil.getFormateDayShort(crewDate);
    		String crewType =  StringUtil.objToStr(reqMap.get("crewType"));
    		highLineCrewService.updateSubmitType(crewDate,crewType);
    	}catch(Exception e){
    		logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());		
    	}
    	return result;
    }
    /**
	 * 获取运行线信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRunLineListForRunDate", method = RequestMethod.POST)
	public Result getRunLineListForRunDate(@RequestBody Map<String,Object> reqMap){
		Result result = new Result(); 
	    try{
	    	logger.debug("getRunLineListForRunDate~~~~~reqMap="+reqMap);
	    	String runDate = StringUtil.objToStr(reqMap.get("runDate"));
	    	//格式化时间
	    	runDate = DateUtil.getFormateDayShort(runDate);
	    	String trainNbr =  StringUtil.objToStr(reqMap.get("trainNbr"));
	    	String rownumstart =  StringUtil.objToStr(reqMap.get("rownumstart"));
	    	String rownumend =  StringUtil.objToStr(reqMap.get("rownumend"));
	    	QueryResult queryResult = highLineCrewService.getRunLineListForRunDate(runDate, "".equals(trainNbr)?null:trainNbr, rownumstart, rownumend);
	    	PagingResult page = new PagingResult(queryResult.getTotal(), queryResult.getRows());
	    	result.setData(page);
	    }catch(Exception e){
			logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
	
		return result;
	}
	
	
	/**
	 * 根据日期获取乘务计划列表信息
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getHighlineCrewListForRunDate", method = RequestMethod.POST)
	public Result getHighlineCrewListForRunDate(@RequestBody Map<String,Object> reqMap){
		Result result = new Result(); 
	    try{
	    	logger.debug("getHighlineCrewListForRunDate~~~~~reqMap="+reqMap);
	    	String crewDate = StringUtil.objToStr(reqMap.get("crewDate"));
	    	//格式化时间
	    	crewDate = DateUtil.getFormateDayShort(crewDate);
	    	String crewType = StringUtil.objToStr(reqMap.get("crewType"));
	    	String rownumstart =  StringUtil.objToStr(reqMap.get("rownumstart"));
	    	String rownumend =  StringUtil.objToStr(reqMap.get("rownumend"));
	    	String trainNbr =  StringUtil.objToStr(reqMap.get("trainNbr"));
	    	QueryResult queryResult = highLineCrewService.getHighlineCrewListForRunDate(crewDate,crewType, trainNbr,rownumstart, rownumend);
	    	PagingResult page = new PagingResult(queryResult.getTotal(), queryResult.getRows());
	    	result.setData(page);
	    }catch(Exception e){
			logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
	
		return result;
	}
	
	
	
	
	
	

	/**
	 * 导出excel
	 * @param request
	 * @param response
     * @author denglj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exportExcel/{crewType}/{crewDate}/{trainNbr}", method = RequestMethod.GET)
	public void exportExcel(@PathVariable("crewType") String crewType,@PathVariable("crewDate") String crewDate,@PathVariable("trainNbr") String trainNbr, HttpServletRequest request, HttpServletResponse response){
		try {
			String name = "";//乘务类型（1车长、2司机、3机械师）
			if("1".equals(crewType)) {
				name = "车长";
			} else if("2".equals(crewType)) {
				name = "司机";
			} else if("3".equals(crewType)) {
				name = "机械师";
			}

	    	//格式化时间
			String _crewDate = DateUtil.getFormateDayShort(crewDate);
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
			HSSFSheet sheet = workBook.createSheet(name+"乘务信息_"+_crewDate);// 创建一个工作薄对象
			
			
			sheet.setColumnWidth((short)0, (short)2000);
			sheet.setColumnWidth((short)1, (short)7000);
			sheet.setColumnWidth((short)2, (short)4000);
			sheet.setColumnWidth((short)3, (short)7000);
			sheet.setColumnWidth((short)4, (short)4000);
			sheet.setColumnWidth((short)5, (short)4500);
			sheet.setColumnWidth((short)6, (short)4000);
			sheet.setColumnWidth((short)7, (short)4000);
			sheet.setColumnWidth((short)8, (short)4500);
			sheet.setColumnWidth((short)9, (short)4000);
			sheet.setColumnWidth((short)10, (short)8000);
			
			
			HSSFCellStyle style = workBook.createCellStyle();
			style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边边框
			style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//顶部边框粗线
			style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边边框
			HSSFDataFormat format = workBook.createDataFormat();
			style.setDataFormat(format.getFormat("@"));//表文为普通文本
			style.setWrapText(true);
			// 设置表文字体
			HSSFFont tableFont = workBook.createFont();
			tableFont.setFontHeightInPoints((short) 12); // 设置字体大小
			tableFont.setFontName("宋体"); // 设置为黑体字
			style.setFont(tableFont);
			
			HSSFCellStyle dataStyle = workBook.createCellStyle();
			dataStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			dataStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边边框
			dataStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//顶部边框粗线
			dataStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边边框
			dataStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
			
			
			HSSFCellStyle styleTitle = workBook.createCellStyle();
			//设置字体
			HSSFFont font = workBook.createFont();//创建字体对象
			font.setFontHeightInPoints((short)12);//设置字体大小
			font.setFontName("黑体");//设置为黑体字
			styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中  
			styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
			styleTitle.setFont(font);//将字体加入到样式对象
			
			

			HSSFRow row0 = sheet.createRow(0);// 创建一个行对象
			row0.setHeightInPoints(23);
			// 标题
			HSSFCell titleCell = row0.createCell((short)0);
			titleCell.setCellStyle(styleTitle);
			titleCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			titleCell.setCellValue(crewDate+" "+name+"乘务计划");
			
			
			
			
			
			HSSFRow row1 = sheet.createRow(1);// 创建一个行对象
			row1.setHeightInPoints(23);
			// 序号
			HSSFCell indexCell = row1.createCell((short)0);
			indexCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			indexCell.setCellStyle(styleTitle);
			indexCell.setCellValue("序号");
			// 乘务交路
			HSSFCell crewCrossCell = row1.createCell((short)1);
			crewCrossCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			crewCrossCell.setCellStyle(styleTitle);
			crewCrossCell.setCellValue("乘务交路");
			// 车队组号
			HSSFCell crewGroupCell = row1.createCell((short)2);
			crewGroupCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			crewGroupCell.setCellStyle(styleTitle);
			crewGroupCell.setCellValue("车队组号");
			// 经由铁路线
			HSSFCell throughLineCell = row1.createCell((short)3);
			throughLineCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			throughLineCell.setCellStyle(styleTitle);
			throughLineCell.setCellValue("经由铁路线");
			// 司机1
			HSSFCell sj1Cell = row1.createCell((short)4);
			sj1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sj1Cell.setCellStyle(styleTitle);
			sj1Cell.setCellValue(name+"1");//乘务类型（1车长、2司机、3机械师）
			HSSFCell blankCell = row1.createCell((short)5);
			HSSFCell blankCell1 = row1.createCell((short)6);
			// 司机2
			HSSFCell sj2Cell = row1.createCell((short)7);
			sj2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sj2Cell.setCellStyle(styleTitle);
			sj2Cell.setCellValue(name+"2");//乘务类型（1车长、2司机、3机械师）
			HSSFCell blankCell8 = row1.createCell((short)8);
			HSSFCell blankCell9 = row1.createCell((short)9);
			
			// 备注
			HSSFCell noteCell = row1.createCell((short)10);
			noteCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			noteCell.setCellStyle(styleTitle);
			noteCell.setCellValue("备注");
			
			
			

			HSSFRow row2 = sheet.createRow(2);// 创建一个行对象
			row2.setHeightInPoints(23);
			HSSFCell blankRowCell0 = row2.createCell((short)0);
			HSSFCell blankRowCell1 = row2.createCell((short)1);
			HSSFCell blankRowCell2 = row2.createCell((short)2);
			HSSFCell blankRowCell3 = row2.createCell((short)3);
			//姓名1
			HSSFCell name1Cell = row2.createCell((short)4);
			name1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			name1Cell.setCellStyle(styleTitle);
			name1Cell.setCellValue("姓名");
			//电话1
			HSSFCell tel1Cell = row2.createCell((short)5);
			tel1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			tel1Cell.setCellStyle(styleTitle);
			tel1Cell.setCellValue("电话");
			//政治面貌1
			HSSFCell identity1Cell = row2.createCell((short)6);
			identity1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			identity1Cell.setCellStyle(styleTitle);
			identity1Cell.setCellValue("政治面貌");
			//姓名2
			HSSFCell name2Cell = row2.createCell((short)7);
			name2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			name2Cell.setCellStyle(styleTitle);
			name2Cell.setCellValue("姓名");
			//电话2
			HSSFCell tel2Cell = row2.createCell((short)8);
			tel2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			tel2Cell.setCellStyle(styleTitle);
			tel2Cell.setCellValue("电话");
			//政治面貌2
			HSSFCell identity2Cell = row2.createCell((short)9);
			identity2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			identity2Cell.setCellStyle(styleTitle);
			identity2Cell.setCellValue("政治面貌");
			HSSFCell blankRowCell10 = row2.createCell((short)10);

			
			//查询乘务上报信息
			List<HighLineCrewInfo> list = highLineCrewService.findList(_crewDate, crewType);
			//循环生成列表
			if(list!=null && list.size() > 0) {
				for (int i=0;i<list.size();i++) {
					HighLineCrewInfo obj = list.get(i);
					HSSFRow rowX = sheet.createRow(3+i);// 创建一个行对象
					rowX.setHeightInPoints(23);
					
					// 序号
					HSSFCell indexCellFor = rowX.createCell((short)0);
					indexCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					indexCellFor.setCellStyle(styleTitle);
					indexCellFor.setCellValue(i+1);

					// 乘务交路
					HSSFCell crewCrossCellFor = rowX.createCell((short)1);
					crewCrossCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					crewCrossCellFor.setCellStyle(styleTitle);
					crewCrossCellFor.setCellValue(obj.getCrewCross());
					// 车队组号
					HSSFCell crewGroupCellFor = rowX.createCell((short)2);
					crewGroupCellFor.setCellStyle(styleTitle);
					crewGroupCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					crewGroupCellFor.setCellValue(obj.getCrewGroup());
					// 经由铁路线
					HSSFCell throughLineCellFor = rowX.createCell((short)3);
					throughLineCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					throughLineCellFor.setCellStyle(styleTitle);
					throughLineCellFor.setCellValue(obj.getThroughLine());
					//姓名1
					HSSFCell name1CellFor = rowX.createCell((short)4);
					name1CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					name1CellFor.setCellStyle(styleTitle);
					name1CellFor.setCellValue(obj.getName1());
					//电话1
					HSSFCell tel1CellFor = rowX.createCell((short)5);
					tel1CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					tel1CellFor.setCellStyle(styleTitle);
					tel1CellFor.setCellValue(obj.getTel1());
					//政治面貌1
					HSSFCell identity1CellFor = rowX.createCell((short)6);
					identity1CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					identity1CellFor.setCellStyle(styleTitle);
					identity1CellFor.setCellValue(obj.getIdentity1());
					//姓名2
					HSSFCell name2CellFor = rowX.createCell((short)7);
					name2CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					name2CellFor.setCellStyle(styleTitle);
					name2CellFor.setCellValue(obj.getName2());
					//电话2
					HSSFCell tel2CellFor = rowX.createCell((short)8);
					tel2CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					tel2CellFor.setCellStyle(styleTitle);
					tel2CellFor.setCellValue(obj.getTel2());
					//政治面貌2
					HSSFCell identity2CellFor = rowX.createCell((short)9);
					identity2CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					identity2CellFor.setCellStyle(styleTitle);
					identity2CellFor.setCellValue(obj.getIdentity2());
					//备注
					HSSFCell noteCellFor = rowX.createCell((short)10);
					noteCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					noteCellFor.setCellStyle(styleTitle);
					noteCellFor.setCellValue(obj.getNote());
				}
			}
			
			
			//行列合并	四个参数分别是：起始行，起始列，结束行，结束列
			sheet.addMergedRegion(new Region((short)0, (short)0, (short)0, (short)10)); //标题  合并1行11列
			sheet.addMergedRegion(new Region((short)1, (short)0, (short)2, (short)0)); //序号  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)1, (short)2, (short)1)); //乘务交路  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)2, (short)2, (short)2)); //车队组号  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)3, (short)2, (short)3)); //经由铁路线  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)4, (short)1, (short)6)); //司机1  合并1行3列
			sheet.addMergedRegion(new Region((short)1, (short)7, (short)1, (short)9)); //司机2  合并1行3列
			sheet.addMergedRegion(new Region((short)1, (short)10, (short)2, (short)10)); //备注  合并2行1列
			
			
			
			String filename = this.encodeFilename(name+"乘务信息_"+_crewDate+".xls", request);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream = null;
			try {
				ouputStream = response.getOutputStream();
				workBook.write(ouputStream);
//				ouputStream.flush();
				ouputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ouputStream!=null) {
					ouputStream.close();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	
	
	
	
	
	
    /** 
     * 设置下载文件中文件的名称 
     *  
     * @param filename 
     * @param request 
     * @author denglj
     * @return 
     */  
    private String encodeFilename(String filename, HttpServletRequest request) {  
      /** 
       * 获取客户端浏览器和操作系统信息 
       * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar) 
       * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6 
       */  
      String agent = request.getHeader("USER-AGENT");  
      try {  
        if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {  
          String newFileName = URLEncoder.encode(filename, "UTF-8");  
          newFileName = StringUtils.replace(newFileName, "+", "%20");  
          if (newFileName.length() > 150) {  
            newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");  
            newFileName = StringUtils.replace(newFileName, " ", "%20");  
          }  
          return newFileName;  
        }  
        if ((agent != null) && (-1 != agent.indexOf("Mozilla")))  
          return MimeUtility.encodeText(filename, "UTF-8", "B");  
    
        return filename;  
      } catch (Exception ex) {
    	  ex.printStackTrace();
        return filename;  
      }  
    }
	
	
    
    
    /**
     * 导入乘务信息excel
     * @param request
     * @param response
     * @author denglj
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result importExcel(HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		Map<String, Object> rmap = new HashMap<String, Object>();
		//创建 POI文件系统对象
		POIFSFileSystem ts = null;
		try {
			int successRec = 0;	//成功记录数
			int errorRec = 0;	//失败记录数

			ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String crewType = request.getParameter("crewType");
			String crewDate = DateUtil.getFormateDayShort(request.getParameter("crewDate"));
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				// 上传文件 
				MultipartFile mf = entity.getValue();
				//创建文件输入流对象
				ts = new POIFSFileSystem(mf.getInputStream());
				//获取文档对象
				HSSFWorkbook wb = new HSSFWorkbook(ts);
				//获取工作薄
				HSSFSheet sheet = wb.getSheetAt(0);
				//声明行对象
				HSSFRow rowFirst = sheet.getRow(3);
				if(rowFirst==null || rowFirst.getCell((short)10)==null){
					result.setCode("101");
					result.setMessage("请按模板导入");
					return result;
				}
				
				
				//目前界面只支持上传一个文件，故此写法没问题
				//删除该日期内 所有上报类型
				highLineCrewService.deleteHighlineCrewForCrewDate(crewDate, crewType, user.getName());
				
				
				//通过循环获取每一行	0\1\2行为标题
				for (int i = 3; sheet.getRow(i)!=null; i++) {
					try {
						//得到行
						HSSFRow row = sheet.getRow(i);
						HighLineCrewInfo addObj = new HighLineCrewInfo();
						addObj.setCrewDate(crewDate);
						addObj.setCrewType(crewType);//乘务类型（1车长、2司机、3机械师）
						addObj.setCrewCross(String.valueOf(row.getCell((short)1)));//乘务交路
						addObj.setCrewGroup(String.valueOf(row.getCell((short)2)));//车队组号
						addObj.setThroughLine(String.valueOf(row.getCell((short)3)));//经由铁路线
						addObj.setName1(String.valueOf(row.getCell((short)4)));//乘务员1姓名
						addObj.setTel1(String.valueOf(row.getCell((short)5)));//乘务员1电话
						addObj.setIdentity1(String.valueOf(row.getCell((short)6)));//乘务员1身份（党员、群众等）
						addObj.setName2(String.valueOf(row.getCell((short)7)));//乘务员2姓名
						addObj.setTel2(String.valueOf(row.getCell((short)8)));//乘务员2电话
						addObj.setIdentity2(String.valueOf(row.getCell((short)9)));//乘务员2身份（党员、群众等）
						addObj.setNote(String.valueOf(row.getCell((short)10)));//备注
						this.addHighLineCrewFromExcel(addObj);
						
						successRec++;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						errorRec ++ ;
					}
					
					
				}
				
				
				
			}
			
			rmap.put("successRec", successRec);
			rmap.put("errorRec", errorRec);
			result.setData(rmap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setCode("401");
			result.setData(rmap);
			result.setMessage("导入失败");
		}
		return result;
	}
    
    
    /**
	 * 获取表highline_crew中record_people_org的值列表
	 * @param reqMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRecordPeopleOrgList", method = RequestMethod.GET)
	public Result getRecordPeopleOrgList(){
		Result result = new Result(); 
	    try{
	    	List<String> list = highLineCrewService.getRecordPeopleOrgList();
	    	logger.debug("list=="+list);
	    	result.setData(list);
	    }catch(Exception e){
			logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
	
		return result;
	}
    
    

	 /**
	  * 对highline_crew进行条件分页查询
	 * @param reqMap
	 * 主要有这些字段：
	 * crewStartDate;crewEndDate;crewType;
       crewBureau;recordPeopleOrg;trainNbr;name;rownumstart;rownumend
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getHighlineCrewBaseInfoForPage", method = RequestMethod.POST)
	public Result getHighlineCrewBaseInfoForPage(@RequestBody Map<String,Object> reqMap){
		Result result = new Result(); 
	    try{
	    	logger.debug("getHighlineCrewBaseInfoForPag~~~reqMap==" + reqMap);
	    	String crewStartDate = StringUtil.objToStr(reqMap.get("crewStartDate"));
	    	String crewEndDate = StringUtil.objToStr(reqMap.get("crewEndDate"));
	    	if(crewStartDate != null && !"".equals(crewStartDate)){
	    		crewStartDate = DateUtil.getFormateDayShort(crewStartDate);
	    		reqMap.put("crewStartDate", crewStartDate);
	    	}
	    	if(crewEndDate != null && !"".equals(crewEndDate)){
	    		crewEndDate = DateUtil.getFormateDayShort(crewEndDate);
	    		reqMap.put("crewEndDate", crewEndDate);
	    	}
	    	QueryResult<HighLineCrewInfo> queryResult = highLineCrewService.getHighlineCrewBaseInfoForPage(reqMap);
	    	PagingResult page = new PagingResult(queryResult.getTotal(), queryResult.getRows());
	    	result.setData(page);
	    }catch(Exception e){
			logger.error(e);
			result.setCode(StaticCodeType.SYSTEM_ERROR.getCode());
			result.setMessage(StaticCodeType.SYSTEM_ERROR.getDescription());	
		}
	
		return result;
	}
	

    /**
     * 保存乘务信息
     * 被导入Excel方法调用
     * @param highLineCrewInfo
     * @author denglj
     */
    private void addHighLineCrewFromExcel(HighLineCrewInfo highLineCrewInfo) {
		ShiroRealm.ShiroUser user = (ShiroRealm.ShiroUser)SecurityUtils.getSubject().getPrincipal();
		highLineCrewInfo.setCrewHighlineId(UUID.randomUUID().toString());
		//所属局简称
		highLineCrewInfo.setCrewBureau(user.getBureauShortName());
		highLineCrewInfo.setRecordPeople(user.getName());
		highLineCrewInfo.setRecordPeopleOrg(user.getDeptName());
		highLineCrewInfo.setSubmitType(0);
		highLineCrewService.addCrew(highLineCrewInfo);
    }
    
    
    
    
    
	/**
	 * 导出全部乘务计划信息excel
	 * @param request
	 * @param response
     * @author denglj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exportAllCrewTypeExcel", method = RequestMethod.GET)
	public void exportAllCrewTypeExcel(@PathParam("crewType") String crewType,@PathParam("trainNbr") String trainNbr,
			@PathParam("crewStartDate") String crewStartDate,@PathParam("crewEndDate") String crewEndDate,@PathParam("crewBureau") String crewBureau,
			@PathParam("recordPeopleOrg") String recordPeopleOrg,@PathParam("name") String name,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String dateTitleName = "";
			Map<String,Object> reqMap = new HashMap<String,Object>();
			reqMap.put("crewType", crewType);
			if (trainNbr!=null && !"".equals(trainNbr.trim())) {
				reqMap.put("crewCross", trainNbr.trim());
			}
			if (crewStartDate!=null && !"".equals(crewStartDate.trim())) {
				dateTitleName += crewStartDate;
				reqMap.put("crewStartDate", DateUtil.getFormateDayShort(crewStartDate.trim()));
			}
			if (crewEndDate!=null && !"".equals(crewEndDate.trim())) {
				reqMap.put("crewEndDate", DateUtil.getFormateDayShort(crewEndDate.trim()));
				if (!crewStartDate.equals(crewEndDate)) {
					dateTitleName += "~"+crewEndDate;
				}
			}
			reqMap.put("crewBureau", crewBureau);
			reqMap.put("recordPeopleOrg", recordPeopleOrg);
			if (name!=null && !"".equals(name.trim())) {
				reqMap.put("name", name.trim());
			}
			
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
			HSSFSheet sheet = workBook.createSheet("乘务信息_"+dateTitleName);// 创建一个工作薄对象
			
			
			sheet.setColumnWidth((short)0, (short)2000);
			sheet.setColumnWidth((short)1, (short)7000);
			sheet.setColumnWidth((short)2, (short)4000);
			sheet.setColumnWidth((short)3, (short)7000);
			sheet.setColumnWidth((short)4, (short)4000);
			sheet.setColumnWidth((short)5, (short)7000);
			sheet.setColumnWidth((short)6, (short)4000);
			sheet.setColumnWidth((short)7, (short)4000);
			sheet.setColumnWidth((short)8, (short)4000);
			sheet.setColumnWidth((short)9, (short)4000);
			sheet.setColumnWidth((short)10, (short)4000);
			sheet.setColumnWidth((short)11, (short)4000);
			sheet.setColumnWidth((short)12, (short)4000);
			sheet.setColumnWidth((short)13, (short)8000);
			sheet.setColumnWidth((short)14, (short)10000);
			
			
			HSSFCellStyle style = workBook.createCellStyle();
			style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边边框
			style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//顶部边框粗线
			style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边边框
			HSSFDataFormat format = workBook.createDataFormat();
			style.setDataFormat(format.getFormat("@"));//表文为普通文本
			style.setWrapText(true);
			// 设置表文字体
			HSSFFont tableFont = workBook.createFont();
			tableFont.setFontHeightInPoints((short) 12); // 设置字体大小
			tableFont.setFontName("宋体"); // 设置为黑体字
			style.setFont(tableFont);
			
			HSSFCellStyle dataStyle = workBook.createCellStyle();
			dataStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			dataStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边边框
			dataStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);//顶部边框粗线
			dataStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边边框
			dataStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
			
			
			HSSFCellStyle styleTitle = workBook.createCellStyle();
			//设置字体
			HSSFFont font = workBook.createFont();//创建字体对象
			font.setFontHeightInPoints((short)12);//设置字体大小
			font.setFontName("黑体");//设置为黑体字
			styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中  
			styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
			styleTitle.setFont(font);//将字体加入到样式对象
			
			

			HSSFRow row0 = sheet.createRow(0);// 创建一个行对象
			row0.setHeightInPoints(23);
			// 标题
			HSSFCell titleCell = row0.createCell((short)0);
			titleCell.setCellStyle(styleTitle);
			titleCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			titleCell.setCellValue(dateTitleName+"乘务计划");
			
			
			
			
			
			HSSFRow row1 = sheet.createRow(1);// 创建一个行对象
			row1.setHeightInPoints(23);
			// 序号
			HSSFCell indexCell = row1.createCell((short)0);
			indexCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			indexCell.setCellStyle(styleTitle);
			indexCell.setCellValue("序号");
			// 乘务类型
			HSSFCell crewTypeCell = row1.createCell((short)1);
			crewTypeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			crewTypeCell.setCellStyle(styleTitle);
			crewTypeCell.setCellValue("乘务类型");
			// 日期
			HSSFCell crewDateCell = row1.createCell((short)2);
			crewDateCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			crewDateCell.setCellStyle(styleTitle);
			crewDateCell.setCellValue("日期");
			// 乘务交路
			HSSFCell crewCrossCell = row1.createCell((short)3);
			crewCrossCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			crewCrossCell.setCellStyle(styleTitle);
			crewCrossCell.setCellValue("乘务交路");
			// 车队组号
			HSSFCell crewGroupCell = row1.createCell((short)4);
			crewGroupCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			crewGroupCell.setCellStyle(styleTitle);
			crewGroupCell.setCellValue("乘务组编号");
			// 经由铁路线
			HSSFCell throughLineCell = row1.createCell((short)5);
			throughLineCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			throughLineCell.setCellStyle(styleTitle);
			throughLineCell.setCellValue("经由铁路线");
			// 司机1
			HSSFCell sj1Cell = row1.createCell((short)6);
			sj1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sj1Cell.setCellStyle(styleTitle);
			sj1Cell.setCellValue("乘务员1");//乘务类型（1车长、2司机、3机械师）
			HSSFCell blankCell = row1.createCell((short)7);
			HSSFCell blankCell1 = row1.createCell((short)8);
			// 司机2
			HSSFCell sj2Cell = row1.createCell((short)9);
			sj2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			sj2Cell.setCellStyle(styleTitle);
			sj2Cell.setCellValue("乘务员2");//乘务类型（1车长、2司机、3机械师）
			HSSFCell blankCell8 = row1.createCell((short)10);
			HSSFCell blankCell9 = row1.createCell((short)11);

			// 路局
			HSSFCell crewBureauCell = row1.createCell((short)12);
			crewBureauCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			crewBureauCell.setCellStyle(styleTitle);
			crewBureauCell.setCellValue("路局");
			// 填报部门
			HSSFCell recordPeopleOrgCell = row1.createCell((short)13);
			recordPeopleOrgCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			recordPeopleOrgCell.setCellStyle(styleTitle);
			recordPeopleOrgCell.setCellValue("填报部门");
			// 备注
			HSSFCell noteCell = row1.createCell((short)14);
			noteCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			noteCell.setCellStyle(styleTitle);
			noteCell.setCellValue("备注");
			
			
			

			HSSFRow row2 = sheet.createRow(2);// 创建一个行对象
			row2.setHeightInPoints(23);
			HSSFCell blankRowCell0 = row2.createCell((short)0);
			HSSFCell blankRowCell1 = row2.createCell((short)1);
			HSSFCell blankRowCell2 = row2.createCell((short)2);
			HSSFCell blankRowCell3 = row2.createCell((short)3);
			HSSFCell blankRowCell4 = row2.createCell((short)4);
			HSSFCell blankRowCell5 = row2.createCell((short)5);
			//姓名1
			HSSFCell name1Cell = row2.createCell((short)6);
			name1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			name1Cell.setCellStyle(styleTitle);
			name1Cell.setCellValue("姓名");
			//电话1
			HSSFCell tel1Cell = row2.createCell((short)7);
			tel1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			tel1Cell.setCellStyle(styleTitle);
			tel1Cell.setCellValue("电话");
			//政治面貌1
			HSSFCell identity1Cell = row2.createCell((short)8);
			identity1Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			identity1Cell.setCellStyle(styleTitle);
			identity1Cell.setCellValue("政治面貌");
			//姓名2
			HSSFCell name2Cell = row2.createCell((short)9);
			name2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			name2Cell.setCellStyle(styleTitle);
			name2Cell.setCellValue("姓名");
			//电话2
			HSSFCell tel2Cell = row2.createCell((short)10);
			tel2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			tel2Cell.setCellStyle(styleTitle);
			tel2Cell.setCellValue("电话");
			//政治面貌2
			HSSFCell identity2Cell = row2.createCell((short)11);
			identity2Cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			identity2Cell.setCellStyle(styleTitle);
			identity2Cell.setCellValue("政治面貌");
			HSSFCell blankRowCell10 = row2.createCell((short)12);
			HSSFCell blankRowCell12 = row2.createCell((short)13);
			HSSFCell blankRowCell13 = row2.createCell((short)14);

			
			//查询乘务上报信息
			List<HighLineCrewInfo> list = highLineCrewService.getHighlineCrewBaseInfo(reqMap);
			//循环生成列表
			if(list!=null && list.size() > 0) {
				for (int i=0;i<list.size();i++) {
					HighLineCrewInfo obj = list.get(i);
					HSSFRow rowX = sheet.createRow(3+i);// 创建一个行对象
					rowX.setHeightInPoints(23);
					
					// 序号
					HSSFCell indexCellFor = rowX.createCell((short)0);
					indexCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					indexCellFor.setCellStyle(styleTitle);
					indexCellFor.setCellValue(i+1);

					// 乘务类型	1车长、2司机、3机械师）
					HSSFCell cerwTypeCellFor = rowX.createCell((short)1);
					cerwTypeCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					cerwTypeCellFor.setCellStyle(styleTitle);
					if ("1".equals(obj.getCrewType())) {
						cerwTypeCellFor.setCellValue("车长");
					}else if ("2".equals(obj.getCrewType())) {
						cerwTypeCellFor.setCellValue("司机");
					}else if ("3".equals(obj.getCrewType())) {
						cerwTypeCellFor.setCellValue("机械师");
					}
					// 日期
					HSSFCell crewDateCellFor = rowX.createCell((short)2);
					crewDateCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					crewDateCellFor.setCellStyle(styleTitle);
					crewDateCellFor.setCellValue(obj.getCrewDate());

					// 乘务交路
					HSSFCell crewCrossCellFor = rowX.createCell((short)3);
					crewCrossCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					crewCrossCellFor.setCellStyle(styleTitle);
					crewCrossCellFor.setCellValue(obj.getCrewCross());
					// 车队组号
					HSSFCell crewGroupCellFor = rowX.createCell((short)4);
					crewGroupCellFor.setCellStyle(styleTitle);
					crewGroupCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					crewGroupCellFor.setCellValue(obj.getCrewGroup());
					// 经由铁路线
					HSSFCell throughLineCellFor = rowX.createCell((short)5);
					throughLineCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					throughLineCellFor.setCellStyle(styleTitle);
					throughLineCellFor.setCellValue(obj.getThroughLine());
					//姓名1
					HSSFCell name1CellFor = rowX.createCell((short)6);
					name1CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					name1CellFor.setCellStyle(styleTitle);
					name1CellFor.setCellValue(obj.getName1());
					//电话1
					HSSFCell tel1CellFor = rowX.createCell((short)7);
					tel1CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					tel1CellFor.setCellStyle(styleTitle);
					tel1CellFor.setCellValue(obj.getTel1());
					//政治面貌1
					HSSFCell identity1CellFor = rowX.createCell((short)8);
					identity1CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					identity1CellFor.setCellStyle(styleTitle);
					identity1CellFor.setCellValue(obj.getIdentity1());
					//姓名2
					HSSFCell name2CellFor = rowX.createCell((short)9);
					name2CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					name2CellFor.setCellStyle(styleTitle);
					name2CellFor.setCellValue(obj.getName2());
					//电话2
					HSSFCell tel2CellFor = rowX.createCell((short)10);
					tel2CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					tel2CellFor.setCellStyle(styleTitle);
					tel2CellFor.setCellValue(obj.getTel2());
					//政治面貌2
					HSSFCell identity2CellFor = rowX.createCell((short)11);
					identity2CellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					identity2CellFor.setCellStyle(styleTitle);
					identity2CellFor.setCellValue(obj.getIdentity2());

					// 填报路局
					HSSFCell crewBureauCellFor = rowX.createCell((short)12);
					crewBureauCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					crewBureauCellFor.setCellStyle(styleTitle);
					crewBureauCellFor.setCellValue(obj.getCrewBureau());
					// 填报部门
					HSSFCell recordPeopleOrgCellFor = rowX.createCell((short)13);
					recordPeopleOrgCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					recordPeopleOrgCellFor.setCellStyle(styleTitle);
					recordPeopleOrgCellFor.setCellValue(obj.getRecordPeopleOrg());
					
					//备注
					HSSFCell noteCellFor = rowX.createCell((short)14);
					noteCellFor.setCellType(HSSFCell.CELL_TYPE_STRING);
					noteCellFor.setCellStyle(styleTitle);
					noteCellFor.setCellValue(obj.getNote());
				}
			}
			
			
			//行列合并	四个参数分别是：起始行，起始列，结束行，结束列
			sheet.addMergedRegion(new Region((short)0, (short)0, (short)0, (short)14)); //标题  合并1行11列
			sheet.addMergedRegion(new Region((short)1, (short)0, (short)2, (short)0)); //序号  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)1, (short)2, (short)1)); //乘务类型  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)2, (short)2, (short)2)); //日期  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)3, (short)2, (short)3)); //乘务交路  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)4, (short)2, (short)4)); //车队组号  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)5, (short)2, (short)5)); //经由铁路线  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)6, (short)1, (short)8)); //司机1  合并1行3列
			sheet.addMergedRegion(new Region((short)1, (short)9, (short)1, (short)11)); //司机2  合并1行3列
			sheet.addMergedRegion(new Region((short)1, (short)12, (short)2, (short)12)); //填报路局  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)13, (short)2, (short)13)); //填报部门  合并2行1列
			sheet.addMergedRegion(new Region((short)1, (short)14, (short)2, (short)14)); //备注  合并2行1列
			
			
			
			String filename = this.encodeFilename("乘务信息_"+dateTitleName+".xls", request);
			response.setHeader("Content-disposition", "attachment;filename=" + filename);
			OutputStream ouputStream = null;
			try {
				ouputStream = response.getOutputStream();
				workBook.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ouputStream!=null) {
					ouputStream.close();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	
}
