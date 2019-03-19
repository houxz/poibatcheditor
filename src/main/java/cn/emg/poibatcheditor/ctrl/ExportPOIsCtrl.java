package cn.emg.poibatcheditor.ctrl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.emg.poibatcheditor.common.ParamUtils;
import cn.emg.poibatcheditor.dao.POIModelDao;

@Controller
@RequestMapping("/exportPOIs.web")
public class ExportPOIsCtrl {
	
	private static final Logger logger = LoggerFactory.getLogger(ExportCtrl.class);
	
	@Autowired
	private POIModelDao poiModelDao;
	
	private static final String[] excelColumns = {"oid", "namec"};
	private static final Integer[] excelColumnWidth = {5000, 12000};
	
	@RequestMapping(method = RequestMethod.POST)
	private void exportPOIs(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("START");
		OutputStream out = null;
		HSSFWorkbook workBook = null;
		try {
			String columnsStr = ParamUtils.getParameter(request, "columns");
			Set<String> columns = new HashSet<String>();
			for (String column : columnsStr.split(",")) {
				if (column != null && !column.isEmpty() && !column.trim().isEmpty())
					columns.add(column);
			}
			String code = ParamUtils.getParameter(request, "code");
			List<Map<String, Object>> pois = poiModelDao.select(columns, code);
			if (pois == null || pois.isEmpty()) {
				logger.debug("BREAK");
				return;
			}
			
			if (pois !=null && !pois.isEmpty()) {
				workBook = new HSSFWorkbook();
				HSSFSheet sheet = workBook.createSheet();
				
				sheet.createFreezePane( 0, 1, 0, 1 );
				
				Integer rowNo = 0;
				{
					Row row0 = sheet.createRow(rowNo++);
					HSSFCellStyle style0 = workBook.createCellStyle();
					HSSFFont font0 = workBook.createFont();
					font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			        style0.setFont(font0);
			        style0.setAlignment(CellStyle.ALIGN_CENTER);
			        style0.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        style0.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			        style0.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        style0.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        style0.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        style0.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        
			        Integer columnNo = 0;
					for(; columnNo < excelColumns.length; columnNo++){
						sheet.setColumnWidth(columnNo, excelColumnWidth[columnNo]);
						
						Cell cell0 = row0.createCell(columnNo);
						cell0.setCellStyle(style0);
						cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
						String column = excelColumns[columnNo];
						cell0.setCellValue(column);
					}
					
					Iterator<String> column = columns.iterator();
					while (column.hasNext()) {
						Cell cell0 = row0.createCell(columnNo);
						sheet.setColumnWidth(columnNo, 5000);
						
						cell0.setCellStyle(style0);
						cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell0.setCellValue(column.next());
						
						columnNo++;
					}
					
				}
				
				HSSFCellStyle styleC = workBook.createCellStyle();
		        styleC.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		        styleC.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		        styleC.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        styleC.setBorderRight(HSSFCellStyle.BORDER_THIN);
		        styleC.setAlignment(CellStyle.ALIGN_CENTER);
				
		        for (Map<String, Object> poi : pois) {
		        	Integer columnNo = 0;
		        	Row row = sheet.createRow(rowNo++);
		        	
		        	for(; columnNo < excelColumns.length; columnNo++){
						sheet.setColumnWidth(columnNo, excelColumnWidth[columnNo]);
						
						Cell cell0 = row.createCell(columnNo);
						cell0.setCellStyle(styleC);
						cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
						String column = excelColumns[columnNo];
						
						if (poi.containsKey(column)) {
							cell0.setCellValue(poi.get(column).toString());
						} else {
							cell0.setCellValue("");
						}
						
					}
					
					Iterator<String> column = columns.iterator();
					while (column.hasNext()) {
						Cell cell0 = row.createCell(columnNo);
						sheet.setColumnWidth(columnNo, 3000);
						
						cell0.setCellStyle(styleC);
						cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
						String _column = column.next();
						
						if (poi.containsKey(_column)) {
							cell0.setCellValue(poi.get(_column).toString());
						} else {
							cell0.setCellValue("");
						}
						
						columnNo++;
					}
					
		        }
		        
		        response.setContentType("application/vnd.ms-excel");
				String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				response.setCharacterEncoding("UTF-8");
				out = response.getOutputStream();
				workBook.write(out);
				out.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(workBook != null){
				try{
					workBook.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		logger.debug("END");
	}
}
