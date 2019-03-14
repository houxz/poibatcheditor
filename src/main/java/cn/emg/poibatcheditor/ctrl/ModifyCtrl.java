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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
import cn.emg.poibatcheditor.commonjar.GeoErrorTypeEnum;
import cn.emg.poibatcheditor.commonjar.ModifyStateEnum;
import cn.emg.poibatcheditor.commonjar.POIChangeTypeEnum;
import cn.emg.poibatcheditor.commonjar.ParamLegalTypeEnum;
import cn.emg.poibatcheditor.commonjar.ProptyErrorTypeEnum;
import cn.emg.poibatcheditor.commonjar.PstateEnum;
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
@RequestMapping("/modify.web")
public class ModifyCtrl extends BaseCtrl {

	private static final Logger logger = LoggerFactory.getLogger(ModifyCtrl.class);
	
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

	@SuppressWarnings("unchecked")
	@RequestMapping()
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
			Set<Long> errorUndo = new HashSet<Long>();
			Set<Long> attachWithError = new HashSet<Long>();
			Integer firstAtttachWithErrorIndex = -1;
			
			RoleTypeEnum roleType = RoleTypeEnum.ROLE_WORKER;
			List<ProjectModel> myProjects = (List<ProjectModel>) projectModelClient.selectMyProjects(SystemType.poi_GEN.getValue(), userid, roleType.getValue());
			if (myProjects != null && !myProjects.isEmpty()) {
				List<Long> myProjectIDs = new ArrayList<Long>();
				for (ProjectModel myProject : myProjects) {
					myProjectIDs.add(myProject.getId());
				}
				
				task = taskModelClient.selectMyNextModifyTask(myProjectIDs, TaskTypeEnum.GEN_WEB, userid);
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
										if (errorPstate != null) {
											if (errorPstate.equals(PstateEnum.UNDO.getValue()) || 
												errorPstate.equals(PstateEnum.CHECKEDWITHERROR.getValue())) {
												errorUndo.add(errorID);
												attachWithError.add(attID);
												if (firstAtttachWithErrorIndex.compareTo(0) < 0) {
													firstAtttachWithErrorIndex = ind;
												}
											}
										}
										error.setId(errorID);
										error.setErrortype(errorType);
										error.setPstate(errorPstate);
										attach.addError(poiID, error);
									}
									
									
									if (pstate.equals(PstateEnum.MODIFYING.getValue())) {
										attachDoing = ind;
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
			model.addAttribute("attachWithError", attachWithError);
			model.addAttribute("attachWithErrorJson", JSONArray.toJSONString(attachWithError));
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
			model.addAttribute("errorUndo", errorUndo);
			model.addAttribute("errorUndoJson", JSONArray.toJSONString(errorUndo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("OPENLADER OVER");
		return "modify";
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
			case submitModifyTask:
				result = submitModifyTask(module);
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
			case acceptError:
				result = acceptError(module);
				break;
			case refuseError:
				result = refuseError(module);
				break;
			case anotherError:
				result = anotherError(module);
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
	private ResultModel getPOIbyAttach(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
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
					for (POIDoIndexWithError poi : pois) {
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
	
	private ResultModel submitModifyTask(RequestModule module) {
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
			
			if (errorModelClient.getUndoErrorModelsByTaskID(taskid).isEmpty()) {
				if (taskModelClient.completeModifyTask(taskid, projectid, tasktype, editid).compareTo(0L) > 0) {
					logger.debug("Modify task( %s ) complete without error. ", taskid);
					result.setResult(1);
				} else {
					result.setResult(0);
					result.setResultMsg("任务提交异常");
				}
			} else {
				if (taskModelClient.submitModifyTask(taskid, projectid, tasktype, editid).compareTo(0L) > 0) {
					logger.debug("Modify task( %s ) submit with errors. ", taskid);
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
	
	@SuppressWarnings("unchecked")
	private ResultModel acceptError(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskid = module.getLongParameter("taskid", -1L);
			Long attachid = module.getLongParameter("attachid", -1L);
			Long poiID = module.getLongParameter("poiID", -1L);
			Long errorid = module.getLongParameter("errorid", -1L);
			Long errortype = module.getLongParameter("errortype", -1L);
			String fieldname = module.getParameter("fieldname");
			String checkvalue = module.getParameter("checkvalue");
			Integer editid = module.getUserInfo().getId();
			Long editVer = module.getLongParameter("editVer", -1L);
			Integer owner = module.getIntParameter("owner", 0);
			
			// 参数合法性判断
			if (errorid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("errorid", errorid));
				logger.debug("BREAK");
				return result;
			}
			
			if (GeoErrorTypeEnum.is(errortype)) {
				// 改错接受多新增的几何错误时，需要一并将POI删除
				if (GeoErrorTypeEnum.ERROR_TYPE_OVER_NEW2.getValue().equals(errortype)) {
					List<Long> poiIDs = new ArrayList<Long>();
					poiIDs.add(poiID);
					if (poiModelClient.deletePOIs(poiIDs).compareTo(0L) >= 0) {
						// 关闭相关错误
						List<TaskLinkErrorModel> taskLinkErrors = (List<TaskLinkErrorModel>) taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskid, null, poiID, PstateEnum.MODIFIEDWITHOUTERROR.getValue());
						Set<Long> errorIDs = new HashSet<Long>(); 
						for (TaskLinkErrorModel taskLinkError: taskLinkErrors) {
							errorIDs.add(taskLinkError.getErrorid());
						}
						errorModelClient.closeErrorModel(new ArrayList<Long>(errorIDs));
						
						// 删除POI关联
						taskModelClient.delTaskAttachLinkPOIModelsByAttachIDAndPoiID(attachid, poiID);
						
						// 新增POI删除履历
						poiModelClient.insertPOIPerformance(poiID, POIChangeTypeEnum.remove, owner, editVer, module.getUserInfo().getId().longValue(), attachid);
						
						result.setResult(1);
					}
				} else {
					if (errorModelClient.acceptErrorModel(new ArrayList<Long>() {
						private static final long serialVersionUID = -2682241111421059018L;
					{
						add(errorid);
					}}).compareTo(0L) > 0) {
						taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskid, errorid, poiID, PstateEnum.MODIFIEDWITHOUTERROR.getValue());
						result.setResult(1);
					}
				}
				
			} else if(ProptyErrorTypeEnum.is(errortype)) {
				List<POIDo> pois = new ArrayList<POIDo>();
				Boolean hasTag = false;
				POIDo poi = poiModelClient.selectPOIByID(poiID);
				if (fieldname.equals("namec")) {
					poi.setNamec(checkvalue);
					hasTag = true;
				} else if (fieldname.equals("featcode")) {
					poi.setFeatcode(Long.valueOf(checkvalue));
					hasTag = true;
				} else if (fieldname.equals("sortcode")) {
					poi.setSortcode(checkvalue);
					hasTag = true;
				}
				
				List<TagDO> newTags = new ArrayList<TagDO>();
				List<TagDO> tags = poi.getTags();
				for (TagDO tag : tags) {
					tag.setId(poiID);
					if (tag.getK().equals(fieldname)) {
						hasTag = true;
						tag.setV(checkvalue);
					}
					newTags.add(tag);
				}
				if (!hasTag) {
					TagDO tag = new TagDO();
					tag.setId(poiID);
					tag.setK(fieldname);
					tag.setV(checkvalue);
					newTags.add(tag);
				}
				poi.setTags(newTags);
				pois.add(poi);
				if (poiModelClient.updatePOIs(editid.longValue(), pois).compareTo(0L) > 0) {
					poiModelClient.insertPOIPerformance(poiID, POIChangeTypeEnum.tag_field_change, poi.getOwner(),
							poi.getEditVer(), module.getUserInfo().getId().longValue(), attachid);
					
					if (errorModelClient.acceptErrorModel(new ArrayList<Long>() {
						private static final long serialVersionUID = -2682243333421059018L;
					{
						add(errorid);
					}}).compareTo(0L) > 0) {
						taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskid, errorid, poiID, PstateEnum.MODIFIEDWITHOUTERROR.getValue());
						result.put("checkvalue", checkvalue);
						result.setResult(1);
					}
				} else {
					result.setResult(0);
					result.setResultMsg("更新POI失败，原因未知");
				}
			} else {
				result.setResult(0);
				result.setResultMsg("未知的错误类型：" + errortype);
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
			Long errorid = module.getLongParameter("errorid", -1L);
			Long taskid = module.getLongParameter("taskid", -1L);
			Long poiID = module.getLongParameter("poiID", -1L);
			
			// 参数合法性判断
			if (errorid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("errorid", errorid));
				logger.debug("BREAK");
				return result;
			}
			
			if (errorModelClient.refuseErrorModel(errorid).compareTo(0L) > 0) {
				taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskid, errorid, poiID, PstateEnum.MODIFIEDWITHERROR.getValue());
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
	
	private ResultModel anotherError(RequestModule module) {
		logger.debug("START");
		ResultModel result = new ResultModel();
		try {
			Long taskid = module.getLongParameter("taskid", -1L);
			Long attachid = module.getLongParameter("attachid", -1L);
			Long poiID = module.getLongParameter("poiID", -1L);
			Long errorid = module.getLongParameter("errorid", -1L);
			String fieldname = module.getParameter("fieldname");
			String editvalue = module.getParameter("editvalue");
			Integer editid = module.getUserInfo().getId();
			
			// 参数合法性判断
			if (errorid.compareTo(0L) <= 0) {
				result.setResult(0);
				result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("errorid", errorid));
				logger.debug("BREAK");
				return result;
			}
			
			// 其他值错误后，直接对POI进行更新
			List<POIDo> pois = new ArrayList<POIDo>();
			Boolean hasTag = false;
			POIDo poi = poiModelClient.selectPOIByID(poiID);
			if (fieldname.equals("namec")) {
				poi.setNamec(editvalue);
				hasTag = true;
			} else if (fieldname.equals("featcode")) {
				Long featcode = -1L;
				try {
					featcode = Long.valueOf(editvalue);
				} catch (Exception e) {
					result.setResult(0);
					result.setResultMsg(ParamLegalTypeEnum.PARAMOUTOFRANGE.getMsg("editvalue", editvalue));
					logger.debug("BREAK");
					return result;
				}
				poi.setFeatcode(featcode);
				hasTag = true;
			} else if (fieldname.equals("sortcode")) {
				poi.setSortcode(editvalue);
				hasTag = true;
			}
			
			List<TagDO> newTags = new ArrayList<TagDO>();
			List<TagDO> tags = poi.getTags();
			for (TagDO tag : tags) {
				tag.setId(poiID);
				if (tag.getK().equals(fieldname)) {
					hasTag = true;
					tag.setV(editvalue);
				}
				newTags.add(tag);
			}
			if (!hasTag) {
				TagDO tag = new TagDO();
				tag.setId(poiID);
				tag.setK(fieldname);
				tag.setV(editvalue);
				newTags.add(tag);
			}
			poi.setTags(newTags);
			pois.add(poi);
			if (poiModelClient.updatePOIs(editid.longValue(), pois).compareTo(0L) > 0) {
				poiModelClient.insertPOIPerformance(poiID, POIChangeTypeEnum.tag_field_change, poi.getOwner(),
						poi.getEditVer(), module.getUserInfo().getId().longValue(), attachid);
				
				if (errorModelClient.anotherValueErrorModel(errorid, editvalue).compareTo(0L) > 0) {
					taskModelClient.updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(taskid, errorid, poiID, PstateEnum.MODIFIEDWITHERROR.getValue());
					result.put("editvalue", editvalue);
					result.setResult(1);
				}
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
			record.setPstate(PstateEnum.MODIFYING.getValue());
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
			
			if (taskModelClient.attachChange(taskid, shapeid, attachIDF, attachIDT, PstateEnum.MODIFIED.getValue(), PstateEnum.MODIFYING.getValue()).compareTo(0L) > 0)
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
			
			PstateEnum pstate = PstateEnum.MODIFIEDWITHOUTERROR;
			
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
							if (error.getErrorstate().compareTo(1) <= 0) {
								pstate = PstateEnum.MODIFIEDWITHERROR;
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

}
