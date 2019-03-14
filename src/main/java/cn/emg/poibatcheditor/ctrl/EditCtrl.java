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

import cn.emg.poibatcheditor.client.FielddataModelClient;
import cn.emg.poibatcheditor.client.POIModelClient;
import cn.emg.poibatcheditor.client.ProcessModelClient;
import cn.emg.poibatcheditor.client.ProjectModelClient;
import cn.emg.poibatcheditor.client.TaskModelClient;
import cn.emg.poibatcheditor.common.ActionEnum;
import cn.emg.poibatcheditor.common.CommonConstants;
import cn.emg.poibatcheditor.common.ParamUtils;
import cn.emg.poibatcheditor.commonjar.PstateEnum;
import cn.emg.poibatcheditor.common.RequestModule;
import cn.emg.poibatcheditor.common.ResultModel;
import cn.emg.poibatcheditor.commonjar.GradeEnum;
import cn.emg.poibatcheditor.commonjar.POIAttrnameEnum;
import cn.emg.poibatcheditor.commonjar.POIChangeTypeEnum;
import cn.emg.poibatcheditor.commonjar.ParamLegalTypeEnum;
import cn.emg.poibatcheditor.commonjar.RoleTypeEnum;
import cn.emg.poibatcheditor.commonjar.SystemType;
import cn.emg.poibatcheditor.commonjar.TaskTypeEnum;
import cn.emg.poibatcheditor.pojo.AttachModel;
import cn.emg.poibatcheditor.pojo.EmployeeModel;
import cn.emg.poibatcheditor.pojo.POIDo;
import cn.emg.poibatcheditor.pojo.POIDoIndexWithError;
import cn.emg.poibatcheditor.pojo.ProcessModel;
import cn.emg.poibatcheditor.pojo.ProjectModel;
import cn.emg.poibatcheditor.pojo.TagDO;
import cn.emg.poibatcheditor.pojo.TaskAttachLinkPOIModel;
import cn.emg.poibatcheditor.pojo.TaskLinkAllModel;
import cn.emg.poibatcheditor.pojo.TaskLinkAttachModel;
import cn.emg.poibatcheditor.pojo.TaskModel;

@Controller
@RequestMapping("/edit.web")
public class EditCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(EditCtrl.class);
	
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

	@RequestMapping(method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public String openLader(Model model, HttpSession session, HttpServletRequest request) {
		logger.debug("OPENLADER");
		try {
			Integer userid = ParamUtils.getIntAttribute(session, CommonConstants.SESSION_USER_ID, -1);
			
			TaskModel task = new TaskModel();
			ProjectModel project = new ProjectModel();
			ProcessModel process = new ProcessModel();
			List<AttachModel> attaches = new ArrayList<AttachModel>();
			JSONObject shapeGeoJsonData = new JSONObject();
			Integer attachIndex = 9999;
			Integer attachDoing = PstateEnum.UNKNOWN.getValue();
			Set<Long> attachUndo = new HashSet<Long>();
			
			RoleTypeEnum roleType = RoleTypeEnum.ROLE_WORKER;
			List<ProjectModel> myProjects = (List<ProjectModel>) projectModelClient.selectMyProjects(SystemType.poi_GEN.getValue(), userid, roleType.getValue());
			if (myProjects != null && !myProjects.isEmpty()) {
				List<Long> myProjectIDs = new ArrayList<Long>();
				for (ProjectModel myProject : myProjects) {
					myProjectIDs.add(myProject.getId());
				}
				
				task = taskModelClient.selectMyNextEditTask(myProjectIDs, TaskTypeEnum.GEN_WEB, userid);
				if (task != null  && task.getId() != null) {
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
									
									if (pstate.equals(PstateEnum.EDITING.getValue())) {
										attachDoing = ind;
										attachUndo.add(attID);
									} else if (pstate.equals(PstateEnum.UNDO.getValue())) {
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
					if (attachIndex.compareTo(attaches.size()) >= 0) {
						attachIndex = attaches.size()-1;
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
		return "edit";
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
			case submitEditTask:
				result = submitEditTask(module);
				break;
			case getPOIbyAttach:
				result = getPOIbyAttach(module);
				break;
			case addPOI:
				result = addPOI(module);
				break;
			case savePOI:
				result = savePOI(module);
				break;
			case deletePOI:
				result = deletePOI(module);
				break;
			case movePOI:
				result = movePOI(module);
				break;
			case setAttachDone:
				result = setAttachDone(module);
				break;
			case setAttachDoing:
				result = setAttachDoing(module);
				break;
			case attachChange:
				result = attachChange(module);
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

	private ResultModel submitEditTask(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskid = module.getLongParameter("taskid", -1L);
			Long projectid = module.getLongParameter("projectid", -1L);
			Integer tasktype = TaskTypeEnum.GEN_WEB.getValue();
			Integer editid = module.getUserInfo().getId();
			
			// 参数合法性判断
			if (taskid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("taskid", taskid));
				logger.debug("BREAK");
				return result;
			}
			
			if (taskModelClient.submitEditTask(taskid, projectid, tasktype, editid).compareTo(0L) > 0) {
				logger.debug("Edit task( %s ) submit. ", taskid);
				result.setResult(1);
			} else {
				result.setResult(0);
				result.setResultMsg("任务提交异常");
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
					List<POIDo> poisForUpdate = new ArrayList<POIDo>();
					for (POIDoIndexWithError poi : pois) {
						if (poi.getConfirmUId() == null || poi.getConfirmUId().compareTo(0L) <= 0) {
							poi.setConfirmUId(userid.longValue());
							poi.setuId(userid.longValue());
							poisForUpdate.add(poi);
						}
						for (TaskAttachLinkPOIModel l : list) {
							if (poi.getId().equals(l.getFeatureid())) {
								poi.setAttachid(attachid);
								poi.setIndex(l.getIndex());
								break;
							}
						}
					}
					if (poisForUpdate != null && !poisForUpdate.isEmpty()) {
						poiModelClient.updatePOIs(userid.longValue(), poisForUpdate);
						
						for (POIDo poiForUpdate : poisForUpdate) {
							poiModelClient.insertPOIPerformance(poiForUpdate.getId(), POIChangeTypeEnum.tag_change, poiForUpdate.getOwner(), 
									poiForUpdate.getEditVer(), userid.longValue(), attachid);
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
			String type = module.getParameter("type");
			Double lng = module.getDoubleParameter("lng", 0.0);
			Double lat = module.getDoubleParameter("lat", 0.0);
			Double lng2 = module.getDoubleParameter("lng2", 0.0);
			Double lat2 = module.getDoubleParameter("lat2", 0.0);
			Long taskid = module.getLongParameter("taskid", -1L);
			Long attachid = module.getLongParameter("attachid", -1L);
			Integer index = module.getIntParameter("index", 0);
			Integer userid = module.getUserInfo().getId();
			
			// 参数合法性判断
			if (namec.isEmpty()) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMNULL.getMsg("namec"));
				logger.debug("BREAK");
				return result;
			}
			
			POIDoIndexWithError poiDo = new POIDoIndexWithError();
			poiDo.setNamec(namec);
			poiDo.setAttachid(attachid);
			poiDo.setIndex(index);
			if (featcode.compareTo(0L) >= 0) {
				poiDo.setFeatcode(featcode);
			}
			poiDo.setSortcode(sortcode);
			poiDo.setConfirmUId(userid.longValue());
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
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			poiDo.setGrade(GradeEnum.general.toString());
			
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
				poiModelClient.insertPOIPerformance(newID, POIChangeTypeEnum.create, poiDo.getOwner(), 
						poiDo.getEditVer(), userid.longValue(), attachid);
				
				TaskAttachLinkPOIModel record = new TaskAttachLinkPOIModel();
				record.setTaskid(taskid);
				record.setAttachid(attachid);
				record.setFeatureid(newID);
				record.setIndex(index);
				record.setLayerid(2);
				record.setUpdatetime(sf.format(new Date()));
				taskModelClient.addTaskAttachLinkPOIModel(record);
				result.setResult(1);
				
				result.put("newpoi", poiDo);
			} else {
				result.setResult(0);
				result.setResultMsg("新增POI失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	private ResultModel savePOI(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long poiID = module.getLongParameter("poiID", -1L);
			String namec = module.getParameter("namec");
			Long featcode = module.getLongParameter("featcode", 0L);
			String sortcode = module.getParameter("sortcode");
			String address4 = module.getParameter("address4");
			String address5 = module.getParameter("address5");
			String address6 = module.getParameter("address6");
			String address7 = module.getParameter("address7");
			String address8 = module.getParameter("address8");
			String tel = module.getParameter("tel");
			Long attachid = module.getLongParameter("attachid", -1L);
			Integer userid = module.getUserInfo().getId();
			
			// 参数合法性判断
			if (poiID.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("poiID", poiID));
				logger.debug("BREAK");
				return result;
			}
			if (namec.isEmpty()) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMNULL.getMsg("namec"));
				logger.debug("BREAK");
				return result;
			}
			
			List<POIDo> pois = new ArrayList<POIDo>();
			POIDo poi = poiModelClient.selectPOIByID(poiID);
			poi.setNamec(namec);
			if (featcode.compareTo(0L) >= 0) {
				poi.setFeatcode(featcode);
			}
			poi.setSortcode(sortcode);
			
			String _address4 = null;
			String _address5 = null;
			String _address6 = null;
			String _address7 = null;
			String _address8 = null;
			String _tel = null;
			
			for (TagDO tag : poi.getTags()) {
				if (tag.getK().equals(POIAttrnameEnum.address4.toString())) {
					_address4 = tag.getV();
				} else if (tag.getK().equals(POIAttrnameEnum.address5.toString())) {
					_address5 = tag.getV();
				} else if (tag.getK().equals(POIAttrnameEnum.address6.toString())) {
					_address6 = tag.getV();
				} else if (tag.getK().equals(POIAttrnameEnum.address7.toString())) {
					_address7 = tag.getV();
				} else if (tag.getK().equals(POIAttrnameEnum.address8.toString())) {
					_address8 = tag.getV();
				}  else if (tag.getK().equals(POIAttrnameEnum.tel.toString())) {
					_tel = tag.getV();
				}
			}
			
			List<TagDO> tags = new ArrayList<TagDO>();
			if (_address4 != null && !_address4.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setId(poiID);
				tag.setK(POIAttrnameEnum.address4.toString());
				tag.setV(address4);
				tags.add(tag);
			} else {
				if (address4 != null && !address4.isEmpty()) {
					TagDO tag = new TagDO();
					tag.setId(poiID);
					tag.setK(POIAttrnameEnum.address4.toString());
					tag.setV(address4);
					tags.add(tag);
				}
			}
			
			if (_address5 != null && !_address5.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setId(poiID);
				tag.setK(POIAttrnameEnum.address5.toString());
				tag.setV(address5);
				tags.add(tag);
			} else {
				if (address5 != null && !address5.isEmpty()) {
					TagDO tag = new TagDO();
					tag.setId(poiID);
					tag.setK(POIAttrnameEnum.address5.toString());
					tag.setV(address5);
					tags.add(tag);
				}
			}
			if (_address6 != null && !_address6.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setId(poiID);
				tag.setK(POIAttrnameEnum.address6.toString());
				tag.setV(address6);
				tags.add(tag);
			} else {
				if (address6 != null && !address6.isEmpty()) {
					TagDO tag = new TagDO();
					tag.setId(poiID);
					tag.setK(POIAttrnameEnum.address6.toString());
					tag.setV(address6);
					tags.add(tag);
				}
			}
			if (_address7 != null && !_address7.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setId(poiID);
				tag.setK(POIAttrnameEnum.address7.toString());
				tag.setV(address7);
				tags.add(tag);
			} else {
				if (address7 != null && !address7.isEmpty()) {
					TagDO tag = new TagDO();
					tag.setId(poiID);
					tag.setK(POIAttrnameEnum.address7.toString());
					tag.setV(address7);
					tags.add(tag);
				}
			}
			if (_address8 != null && !_address8.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setId(poiID);
				tag.setK(POIAttrnameEnum.address8.toString());
				tag.setV(address8);
				tags.add(tag);
			} else {
				if (address8 != null && !address8.isEmpty()) {
					TagDO tag = new TagDO();
					tag.setId(poiID);
					tag.setK(POIAttrnameEnum.address8.toString());
					tag.setV(address8);
					tags.add(tag);
				}
			}
			if (_tel != null && !_tel.isEmpty()) {
				TagDO tag = new TagDO();
				tag.setId(poiID);
				tag.setK(POIAttrnameEnum.tel.toString());
				tag.setV(tel);
				tags.add(tag);
			} else {
				if (tel != null && !tel.isEmpty()) {
					TagDO tag = new TagDO();
					tag.setId(poiID);
					tag.setK(POIAttrnameEnum.tel.toString());
					tag.setV(tel);
					tags.add(tag);
				}
			}
			poi.setTags(tags);
			pois.add(poi);
			if (poiModelClient.updatePOIs(userid.longValue(), pois).compareTo(0L) > 0) {
				poiModelClient.insertPOIPerformance(poiID, POIChangeTypeEnum.tag_field_change, poi.getOwner(), 
						poi.getEditVer(), userid.longValue(), attachid);
				
				result.setResult(1);
			} else {
				result.setResult(0);
				result.setResultMsg("更新POI失败，原因未知");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
	private ResultModel deletePOI(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long attachID = module.getLongParameter("attachID", -1L);
			Long poiID = module.getLongParameter("poiID", -1L);
			Long editVer = module.getLongParameter("editVer", -1L);
			Integer owner = module.getIntParameter("owner", 0);
			
			// 参数合法性判断
			if (poiID.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("poiID", poiID));
				logger.debug("BREAK");
				return result;
			}
						
			List<Long> poiIDs = new ArrayList<Long>();
			poiIDs.add(poiID);
			if (poiModelClient.deletePOIs(poiIDs).compareTo(0L) >= 0) {
				taskModelClient.delTaskAttachLinkPOIModelsByAttachIDAndPoiID(attachID, poiID);
				
				poiModelClient.insertPOIPerformance(poiID, POIChangeTypeEnum.remove, owner, editVer, module.getUserInfo().getId().longValue(), attachID);
				
				result.setResult(1);
			} else {
				result.setResult(0);
				result.setResultMsg("删除失败");
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
	private ResultModel movePOI(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long attachID = module.getLongParameter("attachID", -1L);
			Long poiID = module.getLongParameter("poiID", -1L);
			Integer indexF = module.getIntParameter("indexF", -1);
			Integer indexT = module.getIntParameter("indexT", -1);
			Integer userid = module.getUserInfo().getId();
			
			// 参数合法性判断
			if (indexF.equals(indexT)) {
				result.setResult(0);
				result.setResultMsg("该POI在原地踏步");
				logger.debug("BREAK");
				return result;
			}
			
			List<TaskAttachLinkPOIModel> taskLinkAttachs = (List<TaskAttachLinkPOIModel>) taskModelClient.getTaskAttachLinkPOIModelsByAttachIDAndIndex(attachID, indexT);
			if (taskLinkAttachs != null && !taskLinkAttachs.isEmpty()) {
				Long poiIDT = taskLinkAttachs.get(0).getFeatureid();
				POIDo poiT = poiModelClient.selectPOIByID(poiIDT);
				String geoT = poiT.getGeo();
				POIDo poiF = poiModelClient.selectPOIByID(poiID);
				String geoF = poiF.getGeo();
				
				poiT.setGeo(geoF);
				poiF.setGeo(geoT);
				List<POIDo> pois = new ArrayList<POIDo>();
				pois.add(poiT);
				pois.add(poiF);
				if (poiModelClient.updatePOIs(userid.longValue(), pois).compareTo(0L) > 0) {
					poiModelClient.insertPOIPerformance(poiT.getId(), POIChangeTypeEnum.field_change, poiT.getOwner(), 
							poiT.getEditVer(), userid.longValue(), attachID);
					poiModelClient.insertPOIPerformance(poiF.getId(), POIChangeTypeEnum.field_change, poiF.getOwner(), 
							poiF.getEditVer(), userid.longValue(), attachID);
					
					taskModelClient.updateTaskAttachLinkPOIIndexByAttachIDAndPoiID(attachID, poiID, indexT);
					taskModelClient.updateTaskAttachLinkPOIIndexByAttachIDAndPoiID(attachID, poiIDT, indexF);
					result.setResult(1);
				} else {
					result.setResult(0);
					result.setResultMsg("更新POI失败，原因未知");
				}
			} else {
				result.setResult(0);
				result.setResultMsg("没找到目标POI");
			}
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
			
			if (taskModelClient.attachChange(taskid, shapeid, attachIDF, attachIDT, PstateEnum.EDITED.getValue(), PstateEnum.EDITING.getValue()).compareTo(0L) > 0)
				result.setResult(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setResult(0);
			result.setResultMsg(e.getMessage());
		}

		logger.debug("END");
		return result;
	}
	
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
			
			TaskLinkAttachModel record = new TaskLinkAttachModel();
			record.setTaskid(taskid);
			record.setShapeid(shapeid);
			record.setAttachid(attachid);
			record.setPstate(PstateEnum.EDITED.getValue());
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
			record.setPstate(PstateEnum.EDITING.getValue());
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

}
