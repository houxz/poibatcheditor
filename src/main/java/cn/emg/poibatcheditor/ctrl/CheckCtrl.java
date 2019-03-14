package cn.emg.poibatcheditor.ctrl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPoint;

import cn.emg.poibatcheditor.client.ErrorModelClient;
import cn.emg.poibatcheditor.client.FielddataModelClient;
import cn.emg.poibatcheditor.client.POIModelClient;
import cn.emg.poibatcheditor.client.ProcessModelClient;
import cn.emg.poibatcheditor.client.ProjectModelClient;
import cn.emg.poibatcheditor.client.TaskModelClient;
import cn.emg.poibatcheditor.common.ActionEnum;
import cn.emg.poibatcheditor.common.CommonConstants;
import cn.emg.poibatcheditor.common.ParamUtils;
import cn.emg.poibatcheditor.common.RequestModule;
import cn.emg.poibatcheditor.common.ResultModel;
import cn.emg.poibatcheditor.commonjar.ErrorStateEnum;
import cn.emg.poibatcheditor.commonjar.GeoErrorTypeEnum;
import cn.emg.poibatcheditor.commonjar.GradeEnum;
import cn.emg.poibatcheditor.commonjar.ModifyStateEnum;
import cn.emg.poibatcheditor.commonjar.POIAttrnameEnum;
import cn.emg.poibatcheditor.commonjar.POIChangeTypeEnum;
import cn.emg.poibatcheditor.commonjar.ParamLegalTypeEnum;
import cn.emg.poibatcheditor.commonjar.ProptyErrorTypeEnum;
import cn.emg.poibatcheditor.commonjar.PstateEnum;
import cn.emg.poibatcheditor.commonjar.QIDEnum;
import cn.emg.poibatcheditor.commonjar.RoleTypeEnum;
import cn.emg.poibatcheditor.commonjar.SystemType;
import cn.emg.poibatcheditor.commonjar.TaskTypeEnum;
import cn.emg.poibatcheditor.pojo.AttachModel;
import cn.emg.poibatcheditor.pojo.EmployeeModel;
import cn.emg.poibatcheditor.pojo.ErrorModel;
import cn.emg.poibatcheditor.pojo.POIDo;
import cn.emg.poibatcheditor.pojo.POIDoIndexWithError;
import cn.emg.poibatcheditor.pojo.ProcessModel;
import cn.emg.poibatcheditor.pojo.ProjectModel;
import cn.emg.poibatcheditor.pojo.TagDO;
import cn.emg.poibatcheditor.pojo.TaskAttachLinkPOIModel;
import cn.emg.poibatcheditor.pojo.TaskLinkAllModel;
import cn.emg.poibatcheditor.pojo.TaskLinkAttachModel;
import cn.emg.poibatcheditor.pojo.TaskLinkErrorModel;
import cn.emg.poibatcheditor.pojo.TaskModel;

@Controller
@RequestMapping("/check.web")
public class CheckCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(CheckCtrl.class);
	
	private static final Double lngPerMile = 9.767042619141113E-6;
	
	@Autowired
	private ProjectModelClient projectModelClient;
	@Autowired
	private ProcessModelClient processModelClient;
	@Autowired
	private TaskModelClient taskModelClient;
	@Autowired
	private FielddataModelClient fielddataModelClient;
	@Autowired
	private POIModelClient poiModelClient;
	@Autowired
	private ErrorModelClient errorModelClient;

	@RequestMapping(method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public String openLader(Model model, HttpSession session, HttpServletRequest request) {
		logger.debug("OPENLADER");
		try {
			Integer userid = ParamUtils.getIntAttribute(session, CommonConstants.SESSION_USER_ID, -1);
			
			model.addAttribute("geoErrorTypes", GeoErrorTypeEnum.toJsonStr());
			model.addAttribute("proptyErrorTypes", ProptyErrorTypeEnum.toJsonStr());
			model.addAttribute("modifyStates", ModifyStateEnum.toJsonStr());
			
			TaskModel task = new TaskModel();
			ProjectModel project = new ProjectModel();
			ProcessModel process = new ProcessModel();
			List<AttachModel> attaches = new ArrayList<AttachModel>();
			JSONObject shapeGeoJsonData = new JSONObject();
			Integer attachIndex = 9999;
			Integer attachDoing = PstateEnum.UNKNOWN.getValue();
			Set<Long> attachUndo = new HashSet<Long>();
			Integer firstAtttachWithErrorIndex = -1;
			
			RoleTypeEnum roleType = RoleTypeEnum.ROLE_CHECKER;
			List<ProjectModel> myProjects = (List<ProjectModel>) projectModelClient.selectMyProjects(SystemType.poi_GEN.getValue(), userid, roleType.getValue());
			if (myProjects != null && !myProjects.isEmpty()) {
				List<Long> myProjectIDs = new ArrayList<Long>();
				for (ProjectModel myProject : myProjects) {
					myProjectIDs.add(myProject.getId());
				}
				
				task = taskModelClient.selectMyNextCheckTask(myProjectIDs, TaskTypeEnum.GEN_WEB, userid);
				if (task != null && task.getId() != null) {
					Long projectid = task.getProjectid();
					if (projectid.compareTo(0L) > 0) {
						project = projectModelClient.selectProjectByID(projectid);
						
						Long processid = project.getProcessid();
						if (processid.compareTo(0L) > 0) {
							process = processModelClient.selectProcessByID(processid);
						}
					}
					
					Long taskID = task.getId();
					List<TaskLinkAllModel> taskLinkAlls = (List<TaskLinkAllModel>) taskModelClient.getTaskLinkAllModelsByTaskID(taskID);
					if (taskLinkAlls != null && !taskLinkAlls.isEmpty()) {
						Set<Long> attachIDs = new HashSet<Long>();
						Set<Long> shapeIDs = new HashSet<Long>();
						for (TaskLinkAllModel taskLinkAll : taskLinkAlls) {
							attachIDs.add(taskLinkAll.getAttachid());
							shapeIDs.add(taskLinkAll.getShapeid());
						}
						attaches = (List<AttachModel>) fielddataModelClient.selectAttachByIDS(attachIDs);
						Integer ind = 0;
						for (AttachModel attach : attaches) {
							Long attID = attach.getId();
							for (TaskLinkAllModel taskLinkAll: taskLinkAlls) {
								if (attID.equals(taskLinkAll.getAttachid())) {
									Integer pstate = taskLinkAll.getPstate();
									attach.setPstate(pstate);
									
									Long poiID = taskLinkAll.getPoiid();
									if (poiID != null && poiID.compareTo(0L) > 0) {
										POIDoIndexWithError poi = new POIDoIndexWithError();
										poi.setId(poiID);
										attach.addPoi(poi);
									}
									
									Long errorID = taskLinkAll.getErrorid();
									if (errorID != null && errorID.compareTo(0L) > 0) {
										ErrorModel error = new ErrorModel();
										Long errorType = taskLinkAll.getErrortype();
										Integer errorPstate = taskLinkAll.getErrorpstate();
										if (errorPstate != null && errorPstate.equals(PstateEnum.MODIFIEDWITHERROR.getValue())) {
											attachUndo.add(attID);
											if (firstAtttachWithErrorIndex.compareTo(0) < 0) {
												firstAtttachWithErrorIndex = ind;
											}
										}
										error.setId(errorID);
										error.setErrortype(errorType);
										error.setPstate(errorPstate);
										attach.addError(poiID, error);
									}
									
									if (pstate.equals(PstateEnum.CHECKING.getValue())) {
										attachDoing = ind;
										attachUndo.add(attID);
									} else if (pstate.equals(PstateEnum.EDITED.getValue())) {
										attachIndex = attachIndex.compareTo(ind) < 0 ? attachIndex : ind;
										attachUndo.add(attID);
									}
								}
							}
							ind++;
						}
						shapeGeoJsonData = fielddataModelClient.selectShapeGeoJsonData(shapeIDs);
					}
				}
			}
			model.addAttribute("task", task);
			model.addAttribute("project", project);
			model.addAttribute("process", process);
			model.addAttribute("attaches", attaches);
			model.addAttribute("attachesJson", JSONArray.toJSONString(attaches));
			model.addAttribute("shapeGeoJsonData", shapeGeoJsonData);
			if (attaches.size() > 0) {
				if (attachDoing.compareTo(0) >= 0) {
					attachIndex = attachDoing;
				} else {
					if (firstAtttachWithErrorIndex.compareTo(0) >= 0) {
						attachIndex = firstAtttachWithErrorIndex;
					} else {
						if (attachIndex.compareTo(attaches.size()) >= 0) {
							attachIndex = attaches.size()-1;
						}
					}
				}
			} else {
				attachIndex = 0;
			}
			model.addAttribute("attachIndex", attachIndex);
			model.addAttribute("curAttachID", attaches.size() > 0 ? attaches.get(attachIndex).getId() : 0);
			model.addAttribute("centerX", attaches.size() > 0 ? attaches.get(attachIndex).getX() : 0);
			model.addAttribute("centerY", attaches.size() > 0 ? attaches.get(attachIndex).getY() : 0);
			model.addAttribute("attachUndoJson", JSONArray.toJSONString(attachUndo));
			model.addAttribute("attachUndoCount", attachUndo.size());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("OPENLADER OVER");
		return "check";
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
			case getPOIbyAttach:
				result = getPOIbyAttach(module);
				break;
			case submitCheckTask:
				result = submitCheckTask(module);
				break;
			case addPOI:
				result = addPOI(module);
				break;
			case setAttachDoing:
				result = setAttachDoing(module);
				break;
			case setAttachDone:
				result = setAttachDone(module);
				break;
			case attachChange:
				result = attachChange(module);
				break;
			case saveError:
				result = saveError(module);
				break;
			case refuseError:
				result = refuseError(module);
				break;
			case closeError:
				result = closeError(module);
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
	private ResultModel addPOI(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			String namec = module.getParameter("namec");
			Long featcode = module.getLongParameter("featcode", 0L);
			String sortcode = module.getParameter("sortcode");
			String address4 = module.getParameter("address4");
			String address5 = module.getParameter("address5");
			String address6 = module.getParameter("address6");
			String address7 = module.getParameter("address7");
			String address8 = module.getParameter("address8");
			String tel = module.getParameter("tel");
			Long enamec = module.getLongParameter("enamec", -1L);
			Long efeatcode = module.getLongParameter("efeatcode", -1L);
			Long esortcode = module.getLongParameter("esortcode", -1L);
			Long etaddress4 = module.getLongParameter("etaddress4", -1L);
			Long etaddress5 = module.getLongParameter("etaddress5", -1L);
			Long etaddress6 = module.getLongParameter("etaddress6", -1L);
			Long etaddress7 = module.getLongParameter("etaddress7", -1L);
			Long etaddress8 = module.getLongParameter("etaddress8", -1L);
			Long ettel = module.getLongParameter("ettel", -1L);
			
			String type = module.getParameter("type");
			Double lng = module.getDoubleParameter("lng", 0.0);
			Double lat = module.getDoubleParameter("lat", 0.0);
			Double lng2 = module.getDoubleParameter("lng2", 0.0);
			Double lat2 = module.getDoubleParameter("lat2", 0.0);
			Long taskid = module.getLongParameter("taskid", -1L);
			Long attachid = module.getLongParameter("attachid", -1L);
			Integer index = module.getIntParameter("index", 0);
			Integer editid = module.getIntParameter("editid", 0);
			Integer userid = module.getUserInfo().getId();
			
			// 参数合法性判断
			if (namec.isEmpty()) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMNULL.getMsg("namec"));
				return result;
			}
			
			POIDoIndexWithError poiDo = new POIDoIndexWithError();
			poiDo.setNamec(namec);
			poiDo.setAttachid(attachid);
			poiDo.setIndex(index);
			if (featcode.compareTo(0L) > 0) {
				poiDo.setFeatcode(featcode);
			}
			poiDo.setSortcode(sortcode);
			poiDo.setConfirmUId(editid.longValue());
			poiDo.setManualCheckUId(userid.longValue());
			poiDo.setuId(userid.longValue());
			
			// 一个照片包含多个POI，需要基于上下关联的照片进行偏移
			if (index.compareTo(0) > 0) {
				// 当前任务线只有一张照片时，无法基于上下关联照片偏移
				if (type.equals("self")) {
					// 补充当前任务线只有一张照片时的特殊处理
					GeometryFactory geometryFactory = new GeometryFactory();
					Double _lng = lng + 0.5*index*lngPerMile;
					BigDecimal _lngbd = new BigDecimal(_lng.toString());
					Coordinate coord = new Coordinate(_lngbd.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue(), lat);
					com.vividsolutions.jts.geom.Point[] points = {geometryFactory.createPoint(coord), geometryFactory.createPoint(coord)};
					MultiPoint multiPoint = geometryFactory.createMultiPoint(points);
					poiDo.setGeo(multiPoint.toText());
				} else {
					Point point1 = new Point(lng, lat);
					Point point2 = new Point(lng2, lat2);
					
					// 当两个照片的位置过近时，直接向东偏移
					if (lng.equals(lng2) && lat.equals(lat2)) {
						GeometryFactory geometryFactory = new GeometryFactory();
						Double _lng = lng + 0.5*index*lngPerMile;
						BigDecimal _lngbd = new BigDecimal(_lng.toString());
						Coordinate coord = new Coordinate(_lngbd.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue(), lat);
						com.vividsolutions.jts.geom.Point[] points = {geometryFactory.createPoint(coord), geometryFactory.createPoint(coord)};
						MultiPoint multiPoint = geometryFactory.createMultiPoint(points);
						poiDo.setGeo(multiPoint.toText());
					} else {
						Double distance = GeometryEngine.geodesicDistanceOnWGS84(point1, point2);
						
						// 默认向下一个方向偏移0.5米
						GeometryFactory geometryFactory = new GeometryFactory();
						Double _lng = lng + 0.5*index*((type.equalsIgnoreCase("forward") ? 1 : -1)*(lng2 - lng))/distance;
						Double _lat = lat + 0.5*index*((type.equalsIgnoreCase("forward") ? 1 : -1)*(lat2 - lat))/distance;
						BigDecimal _lngbd = new BigDecimal(_lng.toString());
						BigDecimal _latbd = new BigDecimal(_lat.toString());
						Coordinate coord = new Coordinate(_lngbd.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue(), _latbd.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue());
						com.vividsolutions.jts.geom.Point[] points = {geometryFactory.createPoint(coord), geometryFactory.createPoint(coord)};
						MultiPoint multiPoint = geometryFactory.createMultiPoint(points);
						poiDo.setGeo(multiPoint.toText());
					}
				}
			// 一个照片只包含1个POI，该POI几何位置与照片一致
			} else {
				GeometryFactory geometryFactory = new GeometryFactory();
				Coordinate coord = new Coordinate(lng, lat);
				com.vividsolutions.jts.geom.Point[] points = {geometryFactory.createPoint(coord), geometryFactory.createPoint(coord)};
				MultiPoint multiPoint = geometryFactory.createMultiPoint(points);
				poiDo.setGeo(multiPoint.toText());
			}
			
			poiDo.setGrade(GradeEnum.general.toString());
			
			List<ErrorModel> errors = new ArrayList<ErrorModel>();
			List<TagDO> tags = new ArrayList<TagDO>();
			if (address4 != null && !address4.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setK(POIAttrnameEnum.address4.toString());
				tag.setV(address4);
				tags.add(tag);
			}
			if (address5 != null && !address5.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setK(POIAttrnameEnum.address5.toString());
				tag.setV(address5);
				tags.add(tag);
			}
			if (address6 != null && !address6.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setK(POIAttrnameEnum.address6.toString());
				tag.setV(address6);
				tags.add(tag);
			}
			if (address7 != null && !address7.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setK(POIAttrnameEnum.address7.toString());
				tag.setV(address7);
				tags.add(tag);
			}
			if (address8 != null && !address8.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setK(POIAttrnameEnum.address8.toString());
				tag.setV(address8);
				tags.add(tag);
			}
			if (tel != null && !tel.isEmpty()) {
				TagDO tagTel = new TagDO();
				tagTel.setK("tel");
				tagTel.setV(tel);
				tags.add(tagTel);
			}
			poiDo.setTags(tags);
			Long newID = poiModelClient.insertPOIs(poiDo);
			if (newID != null && newID.compareTo(0L) > 0) {
				poiModelClient.insertPOIPerformance(newID, POIChangeTypeEnum.create, poiDo.getOwner(), poiDo.getEditVer(), poiDo.getuId(), attachid);
				
				TaskAttachLinkPOIModel record = new TaskAttachLinkPOIModel();
				record.setTaskid(taskid);
				record.setAttachid(attachid);
				record.setFeatureid(newID);
				record.setIndex(index);
				record.setLayerid(2);
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				record.setUpdatetime(sf.format(new Date()));
				taskModelClient.addTaskAttachLinkPOIModel(record);
				
				{
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname("POIERRORSTATE");
					error.setEditvalue("");
					error.setCheckvalue("");
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(GeoErrorTypeEnum.ERROR_TYPE_MISS_NEW2.getValue());
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				{
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname("namec");
					error.setEditvalue("");
					error.setCheckvalue(namec);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(enamec);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (featcode != null && featcode.compareTo(0L) > 0 && efeatcode.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname("featcode");
					error.setEditvalue("");
					error.setCheckvalue(featcode.toString());
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(efeatcode);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (sortcode != null && !sortcode.isEmpty() && esortcode.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname("sortcode");
					error.setEditvalue("");
					error.setCheckvalue(sortcode);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(esortcode);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (address4 != null && !address4.isEmpty() && etaddress4.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname(POIAttrnameEnum.address4.toString());
					error.setEditvalue("");
					error.setCheckvalue(address4);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(etaddress4);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (address5 != null && !address5.isEmpty() && etaddress5.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname(POIAttrnameEnum.address5.toString());
					error.setEditvalue("");
					error.setCheckvalue(address5);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(etaddress5);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (address6 != null && !address6.isEmpty() && etaddress6.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname(POIAttrnameEnum.address6.toString());
					error.setEditvalue("");
					error.setCheckvalue(address6);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(etaddress6);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (address7 != null && !address7.isEmpty() && etaddress7.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname(POIAttrnameEnum.address7.toString());
					error.setEditvalue("");
					error.setCheckvalue(address7);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(etaddress7);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (address8 != null && !address8.isEmpty() && etaddress8.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname(POIAttrnameEnum.address8.toString());
					error.setEditvalue("");
					error.setCheckvalue(address8);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(etaddress8);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				if (tel != null && !tel.isEmpty() && ettel.compareTo(0L) > 0) {
					ErrorModel error = new ErrorModel();
					error.setTaskid(taskid);
					error.setFeatureid(newID);
					error.setLayerid(2);
					error.setFieldname(POIAttrnameEnum.tel.toString());
					error.setEditvalue("");
					error.setCheckvalue(tel);
					error.setQid(QIDEnum.QID_MS_ERROR.getValue());
					error.setErrortype(ettel);
					error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
					error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
					error.setCheckroleid(userid);
					error.setChangeroleid(editid);
					errors.add(error);
				}
				List<ErrorModel> newErrors = (List<ErrorModel>) errorModelClient.addErrorModels(errors);
				if (newErrors != null && !newErrors.isEmpty()) {
					List<TaskLinkErrorModel> taskLinkErrors = new ArrayList<TaskLinkErrorModel>();
					for(ErrorModel newError : newErrors) {
						TaskLinkErrorModel taskLinkError = new TaskLinkErrorModel();
						taskLinkError.setTaskid(taskid);
						taskLinkError.setErrorid(newError.getId());
						taskLinkError.setLayerid(2);
						taskLinkError.setErrortype(newError.getErrortype());
						newError.setPstate(PstateEnum.UNDO.getValue());
						taskLinkError.setPstate(PstateEnum.UNDO.getValue());
						taskLinkError.setFeatureid(newError.getFeatureid());
						
						taskLinkErrors.add(taskLinkError);
					}
					
					taskModelClient.addTaskLinkErrorModels(taskLinkErrors);
					
					poiDo.setErrors(new HashSet<ErrorModel>(newErrors));
				}
				
				result.setResult(1);
				result.put("newpoi", poiDo);
			} else {
				result.setResult(0);
				result.setResultMsg("新增POI失败");
			}
			
			result.put("newid", newID);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private ResultModel getPOIbyAttach(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Integer userid = module.getUserInfo().getId();
			Long attachid = module.getLongParameter("attachid", -1L);
			
			// 参数合法性判断
			if (attachid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("attachid", attachid));
				logger.debug("BREAK");
				return result;
			}
			
			List<TaskAttachLinkPOIModel> list = (List<TaskAttachLinkPOIModel>) taskModelClient.getTaskAttachLinkPOIModelsByAttachID(new HashSet<Long>() {
				private static final long serialVersionUID = -1750750793683511343L;
			{
				add(attachid);
			}});
			
			List<POIDoIndexWithError> pois = new ArrayList<POIDoIndexWithError>();
			if (list != null && !list.isEmpty()) {
				Set<Long> poiIDs = new HashSet<Long>();
				for (TaskAttachLinkPOIModel l : list) {
					poiIDs.add(l.getFeatureid());
				}
				
				if (!poiIDs.isEmpty()) {
					pois = (List<POIDoIndexWithError>) poiModelClient.selectPOIByIDS(poiIDs);
					List<ErrorModel> errors = (List<ErrorModel>) errorModelClient.getErrorModelsBypoiIDs(poiIDs);
					List<TaskLinkErrorModel> taskLinkErrors = (List<TaskLinkErrorModel>) taskModelClient.getTaskLinkErrorModelsBypoiIDs(poiIDs);
					for (ErrorModel error : errors) {
						for(TaskLinkErrorModel taskLinkError : taskLinkErrors) {
							if (error.getId().equals(taskLinkError.getErrorid())) {
								error.setPstate(taskLinkError.getPstate());
							}
						}
						
					}
					List<POIDo> poisForUpdate = new ArrayList<POIDo>();
					for (POIDoIndexWithError poi : pois) {
						if (poi.getManualCheckUId() == null || poi.getManualCheckUId().compareTo(0L) <= 0) {
							poi.setManualCheckUId(userid.longValue());
							poi.setuId(userid.longValue());
							poisForUpdate.add(poi);
						}
						for (TaskAttachLinkPOIModel l : list) {
							if (poi.getId().equals(l.getFeatureid())) {
								poi.setIndex(l.getIndex());
								break;
							}
						}
						for (ErrorModel error : errors) {
							if (poi.getId().equals(error.getFeatureid())) {
								poi.addError(error);
							}
						}
					}
					if (poisForUpdate != null && !poisForUpdate.isEmpty()) {
						poiModelClient.updatePOIs(userid.longValue(), poisForUpdate);
						
						for (POIDo poiForUpdate : poisForUpdate) {
							poiModelClient.insertPOIPerformance(poiForUpdate.getId(), POIChangeTypeEnum.tag_change, 
									poiForUpdate.getOwner(), poiForUpdate.getEditVer(), poiForUpdate.getuId(), attachid);
						}
					}
				}
			}
			result.setResult(1);
			result.put("pois", pois);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}

	private ResultModel setAttachDoing(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskid = module.getLongParameter("taskid", -1L);
			Long shapeid = module.getLongParameter("shapeid", -1L);
			Long attachid = module.getLongParameter("attachid", -1L);
			
			// 参数合法性判断
			if (attachid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("attachid", attachid));
				logger.debug("BREAK");
				return result;
			}
			
			TaskLinkAttachModel record = new TaskLinkAttachModel();
			record.setTaskid(taskid);
			record.setShapeid(shapeid);
			record.setAttachid(attachid);
			record.setPstate(PstateEnum.CHECKING.getValue());
			if (taskModelClient.setAttachDoneByAttachID(record ).compareTo(0L) > 0)
				result.setResult(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	private ResultModel attachChange(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskid = module.getLongParameter("taskid", -1L);
			Long shapeid = module.getLongParameter("shapeid", -1L);
			Long attachIDF = module.getLongParameter("attachIDF", -1L);
			Long attachIDT = module.getLongParameter("attachIDT", -1L);
			
			// 参数合法性判断
			if (attachIDF.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("attachIDF", attachIDF));
				logger.debug("BREAK");
				return result;
			}
			if (attachIDT.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("attachIDT", attachIDT));
				logger.debug("BREAK");
				return result;
			}
			
			if (taskModelClient.attachChange(taskid, shapeid, attachIDF, attachIDT, PstateEnum.CHECKED.getValue(), PstateEnum.CHECKING.getValue()).compareTo(0L) > 0)
				result.setResult(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private ResultModel setAttachDone(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskid = module.getLongParameter("taskid", -1L);
			Long shapeid = module.getLongParameter("shapeid", -1L);
			Long attachid = module.getLongParameter("attachid", -1L);
			
			// 参数合法性判断
			if (attachid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("attachid", attachid));
				logger.debug("BREAK");
				return result;
			}
			
			PstateEnum pstate = PstateEnum.CHECKEDWITHOUTERROR;
			
			List<TaskAttachLinkPOIModel> list = (List<TaskAttachLinkPOIModel>) taskModelClient.getTaskAttachLinkPOIModelsByAttachID(new HashSet<Long>() {
				private static final long serialVersionUID = -1750750793683511343L;
			{
				add(attachid);
			}});
			if (list != null && !list.isEmpty()) {
				Set<Long> poiIDs = new HashSet<Long>();
				for (TaskAttachLinkPOIModel l : list) {
					poiIDs.add(l.getFeatureid());
				}
				
				if (!poiIDs.isEmpty()) {
					List<ErrorModel> errors = (List<ErrorModel>) errorModelClient.getErrorModelsBypoiIDs(poiIDs);
					if (errors != null && !errors.isEmpty()) {
						for (ErrorModel error : errors) {
							if (error.getErrorstate().equals(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue()) ||
								error.getErrorstate().equals(ErrorStateEnum.ERROR_STATE_UNKNOW.getValue())) {
								pstate = PstateEnum.CHECKEDWITHERROR;
								break;
							}
						}
					}
				}
			}
			
			TaskLinkAttachModel record = new TaskLinkAttachModel();
			record.setTaskid(taskid);
			record.setShapeid(shapeid);
			record.setAttachid(attachid);
			record.setPstate(pstate.getValue());
			if (taskModelClient.setAttachDoneByAttachID(record).compareTo(0L) > 0) {
				result.setResult(1);
				result.put("pstate", pstate.getValue());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	private ResultModel submitCheckTask(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskid = module.getLongParameter("taskid", -1L);
			Long projectid = module.getLongParameter("projectid", -1L);
			Integer tasktype = TaskTypeEnum.GEN_WEB.getValue();
			Integer checkid = module.getUserInfo().getId();
			
			// 参数合法性判断
			if (taskid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("taskid", taskid));
				logger.debug("BREAK");
				return result;
			}
			
			if (errorModelClient.getUndoErrorModelsByTaskID(taskid).isEmpty()) {
				if (taskModelClient.completeCheckTask(taskid, projectid, tasktype, checkid).compareTo(0L) > 0) {
					logger.debug("Check task( %s ) complete without error. ", taskid);
					result.setResult(1);
				} else {
					result.setResult(0);
					result.setResultMsg("任务提交异常");
				}
			} else {
				if (taskModelClient.submitCheckTask(taskid, projectid, tasktype, checkid).compareTo(0L) > 0) {
					logger.debug("Check task( %s ) submit with errors. ", taskid);
					result.setResult(1);
				} else {
					result.setResult(0);
					result.setResultMsg("任务提交异常");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	private ResultModel saveError(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskID = module.getLongParameter("taskid", -1L);
			Long poiID = module.getLongParameter("poiid", -1L);
			Long errorID = module.getLongParameter("errorid", -1L);
			String fieldname = module.getParameter("fieldname");
			String editvalue = module.getParameter("editvalue");
			String checkvalue = module.getParameter("checkvalue");
			Long errorType = module.getLongParameter("errorType", -1L);
			Integer userid = module.getUserInfo().getId();
			Integer editid = module.getIntParameter("editid", -1);
			
			// TODO: 参数合法性判断
			
			if (errorID.compareTo(0L) > 0) {
				if (errorModelClient.saveErrorModel(errorID, errorType, checkvalue).compareTo(0L) > 0) {
					result.put("errorid", errorID);
					result.setResult(1);
				} else {
					result.setResult(0);
					result.setResultMsg("保存错误异常");
				}
			} else {
				ErrorModel error = new ErrorModel();
				error.setTaskid(taskID);
				error.setFeatureid(poiID);
				error.setLayerid(2);
				error.setFieldname(fieldname);
				error.setEditvalue(editvalue);
				error.setCheckvalue(checkvalue);
				error.setQid(QIDEnum.QID_MS_ERROR.getValue());
				error.setErrortype(errorType);
				error.setModifystate(ModifyStateEnum.ACCEPT_UNKNOW.getValue());
				error.setErrorstate(ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
				error.setCheckroleid(userid);
				error.setChangeroleid(editid);
				ErrorModel newError = errorModelClient.addErrorModel(error);
				if (newError != null && newError.getId().compareTo(0L) > 0) {
					errorID = newError.getId();
					TaskLinkErrorModel taskLinkError = new TaskLinkErrorModel();
					taskLinkError.setTaskid(taskID);
					taskLinkError.setErrorid(errorID);
					taskLinkError.setLayerid(2);
					taskLinkError.setErrortype(errorType);
					taskLinkError.setPstate(PstateEnum.UNDO.getValue());
					taskLinkError.setFeatureid(poiID);
					taskModelClient.addTaskLinkErrorModel(taskLinkError);
					result.put("errorid", errorID);
					result.setResult(1);
				} else {
					result.setResult(0);
					result.setResultMsg("新增错误异常");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private ResultModel closeError(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long attachID = module.getLongParameter("attachid", -1L);
			Long errorID = module.getLongParameter("errorid", -1L);
			Long taskID = module.getLongParameter("taskid", -1L);
			Long poiID = module.getLongParameter("poiid", -1L);
			Long errorType = module.getLongParameter("errortype", -1L);
			Long editVer = module.getLongParameter("editVer", -1L);
			Integer owner = module.getIntParameter("owner", 0);
			
			// 参数合法性判断
			if (errorID.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("errorID", errorID));
				logger.debug("BREAK");
				return result;
			}
			
			// 校正在关闭漏新增的几何错误时，需要一并将POI删除
			if (GeoErrorTypeEnum.ERROR_TYPE_MISS_NEW2.getValue().equals(errorType)) {
				List<Long> poiIDs = new ArrayList<Long>();
				poiIDs.add(poiID);
				if (poiModelClient.deletePOIs(poiIDs).compareTo(0L) >= 0) {
					// 关闭相关错误
					List<TaskLinkErrorModel> taskLinkErrors = (List<TaskLinkErrorModel>) taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskID, null, poiID, PstateEnum.CHECKEDWITHOUTERROR.getValue());
					Set<Long> errorIDs = new HashSet<Long>(); 
					for (TaskLinkErrorModel taskLinkError: taskLinkErrors) {
						errorIDs.add(taskLinkError.getErrorid());
					}
					errorModelClient.closeErrorModel(new ArrayList<Long>(errorIDs));
					
					// 删除POI关联
					taskModelClient.delTaskAttachLinkPOIModelsByAttachIDAndPoiID(attachID, poiID);
					
					// 新增POI删除履历
					poiModelClient.insertPOIPerformance(poiID, POIChangeTypeEnum.remove, owner, editVer, module.getUserInfo().getId().longValue(), attachID);
					
					result.setResult(1);
				}
			} else {
				if (errorModelClient.closeErrorModel(new ArrayList<Long>() {
					private static final long serialVersionUID = -2687941111421059018L;
				{
					add(errorID);
				}}).compareTo(0L) > 0) {
					taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskID, errorID, poiID, PstateEnum.CHECKEDWITHOUTERROR.getValue());
					result.setResult(1);
				} else {
					result.setResult(0);
					result.setResultMsg("保存错误异常");
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	private ResultModel refuseError(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long errorID = module.getLongParameter("errorid", -1L);
			Long taskID = module.getLongParameter("taskid", -1L);
			Long poiID = module.getLongParameter("poiid", -1L);
			
			// 参数合法性判断
			if (errorID.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("errorID", errorID));
				logger.debug("BREAK");
				return result;
			}
			
			if (errorModelClient.refuseErrorModel(errorID).compareTo(0L) > 0) {
				taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskID, errorID, poiID, PstateEnum.CHECKEDWITHERROR.getValue());
				result.setResult(1);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
}
