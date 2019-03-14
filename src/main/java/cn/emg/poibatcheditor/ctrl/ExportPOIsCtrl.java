package cn.emg.poibatcheditor.ctrl;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;

import cn.emg.poibatcheditor.client.FielddataModelClient;
import cn.emg.poibatcheditor.client.POIModelClient;
import cn.emg.poibatcheditor.client.ProjectModelClient;
import cn.emg.poibatcheditor.client.TaskModelClient;
import cn.emg.poibatcheditor.common.ParamUtils;
import cn.emg.poibatcheditor.commonjar.POIAttrnameEnum;
import cn.emg.poibatcheditor.pojo.POIDoIndexWithError;
import cn.emg.poibatcheditor.pojo.POIForExportModel;
import cn.emg.poibatcheditor.pojo.ProjectModel;
import cn.emg.poibatcheditor.pojo.ShapeModel;
import cn.emg.poibatcheditor.pojo.TagDO;
import cn.emg.poibatcheditor.pojo.TaskModelWithFeatrueid;

@Controller
@RequestMapping("/exportPOIs.web")
public class ExportPOIsCtrl {
	
	private static final Logger logger = LoggerFactory.getLogger(ExportCtrl.class);
	
	private static final String[] excelColumns = {"项目ID","项目名称", "任务ID", "原始任务ID", "原始任务名称",
													"oid", "featcode", "sortcode", "namec", "tel",
													"address4", "address5", "address6", "address7", "address8",
													"lon", "lat", "制作人员", "校正人员"};
	private static final Integer[] excelColumnWidth = {3000, 14000, 3000, 7000, 7000,
														5000, 3000, 3000, 6000, 4000, 
														4000, 4000, 4000, 4000, 4000, 
														4000, 4000, 3000, 3000};
	
	@Autowired
	private ProjectModelClient projectModelClient;
	@Autowired
	private TaskModelClient taskModelClient;
	@Autowired
	private POIModelClient poiModelClient;
	@Autowired
	private FielddataModelClient fielddataModelClient;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	private void exportPOIs(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("START");
		OutputStream out = null;
		HSSFWorkbook workBook = null;
		try {
			String processName = ParamUtils.getParameter(request, "processname");
			Long taskid = ParamUtils.getLongParameter(request, "taskid", -1L);
			String timeStart = ParamUtils.getParameter(request, "timeStart");
			String timeEnd = ParamUtils.getParameter(request, "timeEnd");
			
			// 参数合法性判断
			if ((processName == null || processName.isEmpty()) && taskid.compareTo(0L) < 0) {
				logger.debug("BREAK");
				return;
			}
			
			Set<Long> projectIDs = new HashSet<Long>();
			List<ProjectModel> projects = new ArrayList<ProjectModel>();
			if (processName != null && !processName.isEmpty()) {
				projects = (List<ProjectModel>) projectModelClient.selectProjectsByProcessName(processName);
				for (ProjectModel project : projects) {
					projectIDs.add(project.getId());
				}
			}
			
			List<TaskModelWithFeatrueid> taskWithFeatrueids = (List<TaskModelWithFeatrueid>) taskModelClient.selectPOIIDsForExport(new ArrayList<Long>(projectIDs), taskid, timeStart, timeEnd);
			Set<Long> poiIDs = new HashSet<Long>();
			Set<Long> shapeIDs = new HashSet<Long>();
			Set<Integer> userIDs = new HashSet<Integer>();
			for(TaskModelWithFeatrueid taskWithFeatrueid : taskWithFeatrueids) {
				poiIDs.add(taskWithFeatrueid.getFeatureid());
				shapeIDs.add(taskWithFeatrueid.getShapeid());
				userIDs.add(taskWithFeatrueid.getEditid());
				userIDs.add(taskWithFeatrueid.getCheckid());
				Long projectID = taskWithFeatrueid.getProjectid();
				if (!projectIDs.contains(projectID)) {
					ProjectModel project = projectModelClient.selectProjectByID(projectID);
					projectIDs.add(projectID);
					projects.add(project);
				}
				for (ProjectModel project : projects) {
					if (taskWithFeatrueid.getProjectid().equals(project.getId())) {
						taskWithFeatrueid.setProcessid(project.getProcessid());
						taskWithFeatrueid.setProcessname(project.getName());
						break;
					}
				}
			}
			
			List<ShapeModel> shapes = (List<ShapeModel>) fielddataModelClient.selectShapeByIDS(shapeIDs);
			List<POIDoIndexWithError> pois = (List<POIDoIndexWithError>) poiModelClient.selectPOIByIDS(poiIDs);
			List<POIForExportModel> list = new ArrayList<POIForExportModel>();
			for (POIDoIndexWithError poi : pois) {
				if (poi.isDel())
					continue;
				
				Long oid = poi.getId();
				POIForExportModel poiForExport = new POIForExportModel();
				poiForExport.setOid(oid);
				poiForExport.setFeatcode(poi.getFeatcode());
				poiForExport.setSortcode(poi.getSortcode());
				poiForExport.setNamec(poi.getNamec());
				
				// MULTIPOINT ((109.342186 22.815948), (109.342186 22.815948))
				String geo = poi.getGeo();
				WKTReader wktReader = new WKTReader(); 
				Geometry geometry = wktReader.read(geo);
				Double lon = geometry.getInteriorPoint().getX(), lat = geometry.getInteriorPoint().getY();
				poiForExport.setLon(lon);
				poiForExport.setLat(lat);
				
				String tel = new String(), address4 = new String(), address5 = new String(), address6 = new String(), address7 = new String(), address8 = new String();
				for (TagDO tag : poi.getTags()) {
					if (tag.getK().equals(POIAttrnameEnum.tel.toString())) {
						tel = tag.getV();
					} else if(tag.getK().equals(POIAttrnameEnum.address4.toString())) {
						address4 = tag.getV();
					} else if(tag.getK().equals(POIAttrnameEnum.address5.toString())) {
						address5 = tag.getV();
					} else if(tag.getK().equals(POIAttrnameEnum.address6.toString())) {
						address6 = tag.getV();
					} else if(tag.getK().equals(POIAttrnameEnum.address7.toString())) {
						address7 = tag.getV();
					} else if(tag.getK().equals(POIAttrnameEnum.address8.toString())) {
						address8 = tag.getV();
					}
				}
				poiForExport.setTel(tel);
				poiForExport.setAddress4(address4);
				poiForExport.setAddress5(address5);
				poiForExport.setAddress6(address6);
				poiForExport.setAddress7(address7);
				poiForExport.setAddress8(address8);
				for(TaskModelWithFeatrueid taskWithFeatrueid : taskWithFeatrueids) {
					if (oid.equals(taskWithFeatrueid.getFeatureid())) {
						poiForExport.setEditname(taskWithFeatrueid.getEditid().toString());
						poiForExport.setCheckname(taskWithFeatrueid.getCheckid().toString());
						poiForExport.setProcessid(taskWithFeatrueid.getProcessid());
						poiForExport.setProcessname(taskWithFeatrueid.getProcessname());
						poiForExport.setTaskid(taskWithFeatrueid.getId());
						for (ShapeModel shape : shapes) {
							if (taskWithFeatrueid.getShapeid().equals(shape.getId())) {
								poiForExport.setSrctaskid(shape.getSrctaskid());
								poiForExport.setSrctaskname(shape.getSrctaskname());
								break;
							}
						}
						break;
					}
				}
				
				list.add(poiForExport);
			}
			
			if (!list.isEmpty()) {
				workBook = new HSSFWorkbook();
				HSSFSheet sheet = workBook.createSheet();
				
				sheet.createFreezePane( 0, 1, 0, 1 );//冻结第一行
				
				int rowNo = 0;
				// 第一行
				{
					Row row0 = sheet.createRow(rowNo++);
					HSSFCellStyle style0 = workBook.createCellStyle();
					HSSFFont font0 = workBook.createFont();
					font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
			        style0.setFont(font0);
			        style0.setAlignment(CellStyle.ALIGN_CENTER);//居中
			        style0.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//背景色
			        style0.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//背景色
			        style0.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			        style0.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			        style0.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			        style0.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			        
					for(Integer i = 0; i < excelColumns.length; i++){
						sheet.setColumnWidth(i, excelColumnWidth[i]);
						
						Cell cell0 = row0.createCell(i);
						cell0.setCellStyle(style0);
						cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
						String column = excelColumns[i];
						cell0.setCellValue(column);
					}
				}
				
				HSSFCellStyle styleC = workBook.createCellStyle();
		        styleC.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		        styleC.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		        styleC.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		        styleC.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		        styleC.setAlignment(CellStyle.ALIGN_CENTER);
				
		        for (POIForExportModel poi : list) {
		        	Row row = sheet.createRow(rowNo++);
		        	
		        	Field[] fs = poi.getClass().getDeclaredFields();
		        	for (Integer i = 0; i < fs.length; i++) {
		        		Cell cell = row.createCell(i);
			        	
		        		Field f = fs[i];
		        		f.setAccessible(true);
	        			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		cell.setCellValue(f.get(poi).toString());
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
