package cn.emg.poibatcheditor.ctrl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;

import cn.emg.poibatcheditor.client.FielddataModelClient;
import cn.emg.poibatcheditor.client.POIModelClient;
import cn.emg.poibatcheditor.client.ProjectModelClient;
import cn.emg.poibatcheditor.client.TaskModelClient;
import cn.emg.poibatcheditor.common.ActionEnum;
import cn.emg.poibatcheditor.common.CommonConstants;
import cn.emg.poibatcheditor.common.ParamUtils;
import cn.emg.poibatcheditor.common.RequestModule;
import cn.emg.poibatcheditor.common.ResultModel;
import cn.emg.poibatcheditor.commonjar.POIAttrnameEnum;
import cn.emg.poibatcheditor.commonjar.ParamLegalTypeEnum;
import cn.emg.poibatcheditor.pojo.EmployeeModel;
import cn.emg.poibatcheditor.pojo.POIDoIndexWithError;
import cn.emg.poibatcheditor.pojo.POIForExportModel;
import cn.emg.poibatcheditor.pojo.ProjectModel;
import cn.emg.poibatcheditor.pojo.ShapeModel;
import cn.emg.poibatcheditor.pojo.TagDO;
import cn.emg.poibatcheditor.pojo.TaskModelWithFeatrueid;

@Controller
@RequestMapping("/export.web")
public class ExportCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(ExportCtrl.class);
	
	@Autowired
	private ProjectModelClient projectModelClient;
	@Autowired
	private TaskModelClient taskModelClient;
	@Autowired
	private POIModelClient poiModelClient;
	@Autowired
	private FielddataModelClient fielddataModelClient;
	
	@RequestMapping()
	public String openLader(Model model, HttpSession session, HttpServletRequest request) {
		logger.debug("OPENLADER");
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("OPENLADER OVER");
		return "export";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	private ModelAndView controller(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam("action") ActionEnum action,
			@RequestParam("params") RequestModule module) {
		
		Integer userid = ParamUtils.getIntAttribute(session, CommonConstants.SESSION_USER_ID, -1);
		EmployeeModel userInfo = new EmployeeModel();
		userInfo.setId(userid);
		module.setUserInfo(userInfo );
		
		ResultModel result = new ResultModel();
		try {
			switch (action) {
			case getPOIs:
				result = getPOIs(module);
				break;
			default:
				result.setResult(0);
				result.setResultMsg("未知请求：" + action);
				break;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}
		return new ModelAndView(new MappingJackson2JsonView()).addAllObjects(result);
	}
	
	@SuppressWarnings("unchecked")
	private ResultModel getPOIs(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			String processName = module.getParameter("processname");
			Long taskid = module.getLongParameter("taskid", -1L);
			String timeStart = module.getParameter("timestart");
			String timeEnd = module.getParameter("timeend");
			
			// 参数合法性判断
			if ((processName == null || processName.isEmpty()) && taskid.compareTo(0L) < 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMNULL.getMsg("processName、taskid"));
				logger.debug("BREAK");
				return result;
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
			result.setResult(1);
			result.setRows(list);
			result.setTotal(list.size());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
}
