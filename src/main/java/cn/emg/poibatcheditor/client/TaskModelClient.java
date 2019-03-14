package cn.emg.poibatcheditor.client;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emg.poibatcheditor.commonjar.TaskTypeEnum;
import cn.emg.poibatcheditor.performance.PerformanceMonitor;
import cn.emg.poibatcheditor.common.ExecuteSQLApiClientUtils;
import cn.emg.poibatcheditor.pojo.TaskAttachLinkPOIModel;
import cn.emg.poibatcheditor.pojo.TaskLinkAllModel;
import cn.emg.poibatcheditor.pojo.TaskLinkAttachModel;
import cn.emg.poibatcheditor.pojo.TaskLinkErrorModel;
import cn.emg.poibatcheditor.pojo.TaskModel;
import cn.emg.poibatcheditor.pojo.TaskModelWithFeatrueid;

@Service
public class TaskModelClient {
	
	@Value("${taskApi.host}")
	private String host;
	@Value("${taskApi.port}")
	private String port;
	@Value("${taskApi.path}")
	private String path;
	
	private final static String SELECT = "select";
	private final static String UPDATE = "update";
	
	private static final Logger logger = LoggerFactory.getLogger(TaskModelClient.class);
	
	private String interUrl = "http://%s:%s/%s/poitask/%s/%s/execute";
	
	@PerformanceMonitor
	public TaskModel selectMyNextEditTask(List<Long> projectIDs, TaskTypeEnum taskType, Integer userid) throws Exception {
		TaskModel task = null;
		try {
			String sql = getEditTaskSQL(projectIDs, taskType, userid);
			task = (TaskModel) ExecuteSQLApiClientUtils.getModel(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return task;
	}
	
	@PerformanceMonitor
	public TaskModel selectMyNextCheckTask(List<Long> projectIDs, TaskTypeEnum taskType, Integer userid) throws Exception {
		TaskModel task = null;
		try {
			String sql = getCheckTaskSQL(projectIDs, taskType, userid);
			task = (TaskModel) ExecuteSQLApiClientUtils.getModel(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return task;
	}
	
	@PerformanceMonitor
	public TaskModel selectMyNextModifyTask(List<Long> projectIDs, TaskTypeEnum taskType, Integer userid) throws Exception {
		TaskModel task = null;
		try {
			String sql = getModifyTaskSQL(projectIDs, taskType, userid);
			task = (TaskModel) ExecuteSQLApiClientUtils.getModel(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return task;
	}
	
	@PerformanceMonitor
	public ArrayList<?> selectPOIIDsForExport(List<Long> projectIDs, Long taskid, String timeStart, String timeEnd) throws Exception {
		ArrayList<?> tasks = new ArrayList<TaskModelWithFeatrueid>();
		try {
			String sql = getPOIIDsForExportSQL(projectIDs, taskid, timeStart, timeEnd);
			tasks = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskModelWithFeatrueid.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return tasks;
	}
	
	@PerformanceMonitor
	public Long submitEditTask(Long taskid, Long projectid, Integer tasktype, Integer editid) throws Exception {
		Long ret = -1L;
		try {
			String sql = getSubmitEditTaskSQL(taskid, projectid, tasktype, editid);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long submitCheckTask(Long taskid, Long projectid, Integer tasktype, Integer checkid) throws Exception {
		Long ret = -1L;
		try {
			String sql = getSubmitCheckTaskSQL(taskid, projectid, tasktype, checkid);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long completeCheckTask(Long taskid, Long projectid, Integer tasktype, Integer checkid) throws Exception {
		Long ret = -1L;
		try {
			String sql = getCompleteCheckTaskSQL(taskid, projectid, tasktype, checkid);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long submitModifyTask(Long taskid, Long projectid, Integer tasktype, Integer checkid) throws Exception {
		Long ret = -1L;
		try {
			String sql = getSubmitModifyTaskSQL(taskid, projectid, tasktype, checkid);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long completeModifyTask(Long taskid, Long projectid, Integer tasktype, Integer checkid) throws Exception {
		Long ret = -1L;
		try {
			String sql = getCompleteModifyTaskSQL(taskid, projectid, tasktype, checkid);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getTaskLinkAttachModelsByTaskID(Long taskID) throws Exception {
		ArrayList<?> taskLinkAttachs = new ArrayList<TaskLinkAttachModel>();
		try {
			String sql = getAttachSQL(taskID);
			taskLinkAttachs = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskLinkAttachModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return taskLinkAttachs;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getTaskLinkAllModelsByTaskID(Long taskID) throws Exception {
		ArrayList<?> taskLinkAttachs = new ArrayList<TaskLinkAllModel>();
		try {
			String sql = getTaskLinkAllModelsByTaskIDSQL(taskID);
			taskLinkAttachs = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskLinkAllModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return taskLinkAttachs;
	}
	
	@PerformanceMonitor
	public Long attachChange(Long taskid, Long shapeid, Long attachIDF, Long attachIDT, Integer pstateF, Integer pstateT) throws Exception {
		Long ret = -1L;
		try {
			String sql = getAttachChangeSQL(taskid, shapeid, attachIDF, attachIDT, pstateF, pstateT);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long setAttachDoneByAttachID(TaskLinkAttachModel record) throws Exception {
		Long ret = -1L;
		try {
			String sql = getSetAttachDoneByAttachIDSQL(record);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getTaskAttachLinkPOIModelsByAttachID(Set<Long> attachIDs) throws Exception {
		ArrayList<?> taskAttachLinkPOIModels = new ArrayList<TaskAttachLinkPOIModel>();
		try {
			String sql = getTaskAttachLinkPOIModelsByAttachIDSQL(attachIDs);
			taskAttachLinkPOIModels = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskAttachLinkPOIModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return taskAttachLinkPOIModels;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getTaskAttachLinkPOIModelsByAttachIDAndIndex(Long attachID, Integer index) throws Exception {
		ArrayList<?> taskAttachLinkPOIModels = new ArrayList<TaskAttachLinkPOIModel>();
		try {
			String sql = getTaskAttachLinkPOIModelsByAttachIDAndIndexSQL(attachID, index);
			taskAttachLinkPOIModels = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskAttachLinkPOIModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return taskAttachLinkPOIModels;
	}
	
	@PerformanceMonitor
	public Long updateTaskAttachLinkPOIIndexByAttachIDAndPoiID(Long attachID, Long poiID, Integer index) throws Exception {
		Long ret = -1L;
		try {
			String sql = getUpdateTaskAttachLinkPOIIndexByAttachIDAndPoiIDSQL(attachID, poiID, index);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long delTaskAttachLinkPOIModelsByAttachIDAndPoiID(Long attachID, Long poiID) throws Exception {
		Long ret = -1L;
		try {
			String sql = getDelTaskAttachLinkPOIModelsByAttachIDAndPoiIDSQL(attachID, poiID);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long addTaskAttachLinkPOIModel(TaskAttachLinkPOIModel record) throws Exception {
		Long ret = -1L;
		try {
			String sql = getAddTaskAttachLinkPOIModelSQL(record);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long addTaskLinkErrorModel(TaskLinkErrorModel record) throws Exception {
		Long ret = -1L;
		try {
			List<TaskLinkErrorModel> records = new ArrayList<TaskLinkErrorModel>() {
				private static final long serialVersionUID = 2592404962142224155L;
			{
				add(record);
			}};
			String sql = getAddTaskLinkErrorModelsSQL(records);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long addTaskLinkErrorModels(List<TaskLinkErrorModel> records) throws Exception {
		Long ret = -1L;
		try {
			String sql = getAddTaskLinkErrorModelsSQL(records);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getTaskLinkErrorModelsByTaskID(Long taskID) throws Exception {
		ArrayList<?> taskLinkErrors = new ArrayList<TaskLinkErrorModel>();
		try {
			String sql = getTaskLinkErrorModelsByTaskIDSQL(taskID);
			taskLinkErrors = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskLinkErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return taskLinkErrors;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getTaskLinkErrorModelsBypoiIDs(Set<Long> poiIDs) throws Exception {
		ArrayList<?> taskLinkErrors = new ArrayList<TaskLinkErrorModel>();
		try {
			String sql = getTaskLinkErrorModelsBypoiIDSQL(poiIDs);
			taskLinkErrors = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskLinkErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return taskLinkErrors;
	}
	
	@PerformanceMonitor
	public ArrayList<?> updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiID(Long taskID, Long errorID, Long poiID, Integer pstate) throws Exception {
		ArrayList<?> taskLinkErrors = new ArrayList<TaskLinkErrorModel>();
		try {
			String sql = updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiIDSQL(taskID, errorID, poiID, pstate);
			taskLinkErrors = ExecuteSQLApiClientUtils.updateAndGetList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), TaskLinkErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return taskLinkErrors;
	}
	
	private String getAddTaskLinkErrorModelsSQL(List<TaskLinkErrorModel> records) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO tb_task_link_error ");
		sb.append("(taskid, errorid, layerid, updatetime, state, type, errortype, pstate, groupid, featureid) VALUES ");
		for (TaskLinkErrorModel record : records) {
			sb.append("( ");
			sb.append(record.getTaskid() + ",");
			sb.append(record.getErrorid() + ",");
			sb.append(record.getLayerid() + ",");
			sb.append("now(),");
			sb.append(record.getState() + ",");
			sb.append(record.getType() + ",");
			sb.append(record.getErrortype() + ",");
			sb.append(record.getPstate() + ",");
			sb.append(record.getGroupid() + ",");
			sb.append(record.getFeatureid());
			sb.append("), ");
		}
		sb = sb.deleteCharAt(sb.length() - 2);
		return sb.toString();
	}

	private String getAttachSQL(Long taskID) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM tb_task_link_attach ");
		sb.append("WHERE taskid = " + taskID);
		return sb.toString();
	}
	
	private String getTaskLinkAllModelsByTaskIDSQL(Long taskID) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.*, ap.featureid AS poiid, ap.index AS poiindex, e.errorid, e.errortype, e.pstate AS errorpstate ");
		sb.append("FROM tb_task_link_attach a ");
		sb.append("LEFT JOIN tb_task_attach_link_poi ap ON ap.attachid = a.attachid AND ap.taskid = a.taskid ");
		sb.append("LEFT JOIN tb_task_link_error e ON e.featureid = ap.featureid ");
		sb.append("WHERE a.taskid = " + taskID);
		return sb.toString();
	}
	
	private String getPOIIDsForExportSQL(List<Long> projectIDs, Long taskid, String timeStart, String timeEnd) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT t.*, p.featureid, f.shapeid ");
		sb.append("FROM tb_task t ");
		sb.append("JOIN tb_task_attach_link_poi p ON p.taskid = t.id ");
		sb.append("JOIN tb_task_link_fielddata f ON f.taskid = t.id ");
		sb.append("WHERE t.tasktype = 16101 ");
		if (taskid != null && taskid.compareTo(0L) > 0) {
			sb.append(" AND t.id = " + taskid);
		}
		if (projectIDs != null && projectIDs.size() > 0) {
			sb.append(" AND t.projectid IN ( " + StringUtils.join(projectIDs, ",") + " )");
		}
		if (timeStart != null && !timeStart.isEmpty()) {
			sb.append(" AND t.state = 3");
			sb.append(" AND t.operatetime >= '" + timeStart + "'");
		}
		if (timeEnd != null && !timeEnd.isEmpty()) {
			sb.append(" AND t.state = 3");
			sb.append(" AND t.operatetime <= '" + timeEnd + "'");
		}
		
		return sb.toString();
	}
	
	private String getEditTaskSQL(List<Long> projectIDs, TaskTypeEnum taskType, Integer userid) {
		StringBuilder sb = new StringBuilder();
		sb.append("with projectid(projectid) as (select * from unnest(array[" + StringUtils.join(projectIDs, ",") + "])), ");
		sb.append("		sorttable(state, process, sortvalue) as (values(1,5,0)) ");
		sb.append("update tb_task ");
		sb.append("set starttime=now(),operatetime=now(),state=1,process=5,editid= " + userid);
		sb.append("from ( ");
		sb.append("		select * from ( ");
		sb.append("			select coalesce ( ");
		sb.append("				( select id from tb_task ");
		sb.append("					join sorttable using(state, process) ");
		sb.append("					where tasktype = " + taskType.getValue() + " and editid=" + userid +" and projectid=p.projectid ");
		sb.append("					order by sortvalue ,id ");
		sb.append("					limit 1 for update), ");
		sb.append("				( select id from tb_task ");
		sb.append("					where  tasktype = " + taskType.getValue() + " and (state=0 and process=0 )  and projectid=p.projectid and ( editid=0 or editid ISNULL) ");
		sb.append("					order by id ");
		sb.append("					limit 1 for update) ");
		sb.append("			) as taskid ");
		sb.append("			from projectid as p ");
		sb.append("		) as b where taskid is not null limit 1 ");
		sb.append(") as a(id) where tb_task.id = a.id returning tb_task.*; ");
		return sb.toString();
	}
	
	private String getCheckTaskSQL(List<Long> projectIDs, TaskTypeEnum taskType, Integer userid) {
		StringBuilder sb = new StringBuilder();
		sb.append("with projectid(projectid) as (select * from unnest(array[" + StringUtils.join(projectIDs, ",") + "])), ");
		sb.append("		sorttable(state, process, sortvalue) as (values(1,6,0),(2,7,1)) ");
		sb.append("update tb_task ");
		sb.append("set operatetime=now(),state=1,process=6,checkid= " + userid);
		sb.append("from ( ");
		sb.append("		select * from ( ");
		sb.append("			select coalesce ( ");
		sb.append("				( select id from tb_task ");
		sb.append("					join sorttable using(state, process) ");
		sb.append("					where tasktype = " + taskType.getValue() + " and checkid=" + userid +" and projectid=p.projectid ");
		sb.append("					order by sortvalue ,id ");
		sb.append("					limit 1 for update), ");
		sb.append("				( select id from tb_task ");
		sb.append("					where  tasktype = " + taskType.getValue() + " and (state=2 and process=5 )  and projectid=p.projectid and ( checkid=0 or editid ISNULL) ");
		sb.append("					order by id ");
		sb.append("					limit 1 for update) ");
		sb.append("			) as taskid ");
		sb.append("			from projectid as p ");
		sb.append("		) as b where taskid is not null limit 1 ");
		sb.append(") as a(id) where tb_task.id = a.id returning tb_task.*; ");
		return sb.toString();
	}
	
	private String getModifyTaskSQL(List<Long> projectIDs, TaskTypeEnum taskType, Integer userid) {
		StringBuilder sb = new StringBuilder();
		sb.append("with projectid(projectid) as (select * from unnest(array[" + StringUtils.join(projectIDs, ",") + "])), ");
		sb.append("		sorttable(state, process, sortvalue) as (values(1,7,0),(2,6,1)) ");
		sb.append("update tb_task ");
		sb.append("set operatetime=now(),state=1,process=7,editid= " + userid);
		sb.append("from ( ");
		sb.append("		select * from ( ");
		sb.append("			select coalesce ( ");
		sb.append("				( select id from tb_task ");
		sb.append("					join sorttable using(state, process) ");
		sb.append("					where tasktype = " + taskType.getValue() + " and editid=" + userid +" and projectid=p.projectid ");
		sb.append("					order by sortvalue ,id ");
		sb.append("					limit 1 for update) ");
		sb.append("			) as taskid ");
		sb.append("			from projectid as p ");
		sb.append("		) as b where taskid is not null limit 1 ");
		sb.append(") as a(id) where tb_task.id = a.id returning tb_task.*; ");
		return sb.toString();
	}
	
	private String getSubmitEditTaskSQL(Long taskid, Long projectid, Integer tasktype, Integer editid) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task");
		sb.append(" SET endtime=now(),operatetime=now(),state = 2");
		sb.append(" WHERE id = " + taskid);
		sb.append(" AND projectid = " + projectid);
		sb.append(" AND tasktype = " + tasktype);
		sb.append(" AND editid = " + editid);
		return sb.toString();
	}
	
	private String getSubmitCheckTaskSQL(Long taskid, Long projectid, Integer tasktype, Integer checkid) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task");
		sb.append(" SET endtime=now(),operatetime=now(),state = 2");
		sb.append(" WHERE id = " + taskid);
		sb.append(" AND projectid = " + projectid);
		sb.append(" AND tasktype = " + tasktype);
		sb.append(" AND checkid = " + checkid);
		return sb.toString();
	}
	
	private String getSubmitModifyTaskSQL(Long taskid, Long projectid, Integer tasktype, Integer editid) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task");
		sb.append(" SET endtime=now(),operatetime=now(),state = 2");
		sb.append(" WHERE id = " + taskid);
		sb.append(" AND projectid = " + projectid);
		sb.append(" AND tasktype = " + tasktype);
		sb.append(" AND editid = " + editid);
		return sb.toString();
	}
	
	private String getCompleteCheckTaskSQL(Long taskid, Long projectid, Integer tasktype, Integer checkid) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task");
		sb.append(" SET endtime=now(),operatetime=now(),state = 3");
		sb.append(" WHERE id = " + taskid);
		sb.append(" AND projectid = " + projectid);
		sb.append(" AND tasktype = " + tasktype);
		sb.append(" AND checkid = " + checkid);
		return sb.toString();
	}
	
	private String getCompleteModifyTaskSQL(Long taskid, Long projectid, Integer tasktype, Integer editid) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task");
		sb.append(" SET endtime=now(),operatetime=now(),state = 3");
		sb.append(" WHERE id = " + taskid);
		sb.append(" AND projectid = " + projectid);
		sb.append(" AND tasktype = " + tasktype);
		sb.append(" AND editid = " + editid);
		return sb.toString();
	}
	
	private String getTaskAttachLinkPOIModelsByAttachIDSQL(Set<Long> attachIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM tb_task_attach_link_poi ");
		sb.append("WHERE attachid IN (" + StringUtils.join(attachIDs, ",") + ")");
		sb.append(" ORDER BY index, featureid");
		return sb.toString();
	}
	
	private String getTaskAttachLinkPOIModelsByAttachIDAndIndexSQL(Long attachID, Integer index) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM tb_task_attach_link_poi ");
		sb.append("WHERE attachid = " + attachID);
		sb.append(" AND index = " + index);
		return sb.toString();
	}
	
	private String getUpdateTaskAttachLinkPOIIndexByAttachIDAndPoiIDSQL(Long attachID, Long poiID, Integer index) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(" tb_task_attach_link_poi ");
		sb.append("SET updatetime=now(),index = " + index);
		sb.append("WHERE attachid = " + attachID);
		sb.append(" AND featureid = " + poiID);
		return sb.toString();
	}
	
	private String getDelTaskAttachLinkPOIModelsByAttachIDAndPoiIDSQL(Long attachID, Long poiID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append("FROM tb_task_attach_link_poi ");
		sb.append("WHERE attachid = " + attachID);
		sb.append(" AND featureid = " + poiID);
		return sb.toString();
	}
	
	private String getAddTaskAttachLinkPOIModelSQL(TaskAttachLinkPOIModel record) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO tb_task_attach_link_poi ");
		sb.append("(taskid, attachid, featureid, layerid, index, updatetime) VALUES (");
		sb.append(record.getTaskid() + ",");
		sb.append(record.getAttachid() + ",");
		sb.append(record.getFeatureid() + ",");
		sb.append(record.getLayerid() + ",");
		sb.append(record.getIndex() + ",'");
		sb.append(record.getUpdatetime() + "')");
		return sb.toString();
	}
	
	private String getSetAttachDoneByAttachIDSQL(TaskLinkAttachModel record) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task_link_attach ");
		sb.append(" SET pstate = " + record.getPstate());
		sb.append(" ,updatetime = now()");
		sb.append(" WHERE taskid = " + record.getTaskid());
		sb.append(" AND shapeid = " + record.getShapeid());
		sb.append(" AND attachid = " + record.getAttachid());
		return sb.toString();
	}
	
	private String getAttachChangeSQL(Long taskid, Long shapeid, Long attachIDF, Long attachIDT, Integer pstateF, Integer pstateT) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task_link_attach ");
		sb.append(" SET pstate = (");
		sb.append(" 	CASE attachid ");
		sb.append(" 	WHEN " + attachIDT + " THEN " + pstateT);
		sb.append(" 	WHEN " + attachIDF + " THEN " + pstateF);
		sb.append(" 	END) ");
		sb.append(" ,updatetime = now()");
		sb.append(" WHERE taskid = " + taskid);
		sb.append(" AND shapeid = " + shapeid);
		sb.append(" AND attachid IN ( " + attachIDF + "," + attachIDT + ")");
		return sb.toString();
	}
	
	private String getTaskLinkErrorModelsByTaskIDSQL(Long taskID) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM tb_task_link_error ");
		sb.append("WHERE taskid = " + taskID);
		return sb.toString();
	}
	
	private String getTaskLinkErrorModelsBypoiIDSQL(Set<Long> poiIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * ");
		sb.append("FROM tb_task_link_error ");
		sb.append("WHERE featureid IN (" +  StringUtils.join(poiIDs, ",") + ")");
		return sb.toString();
	}
	
	private String updateTaskLinkErrorPstateByTaskIDErrorIDAndpoiIDSQL(Long taskID, Long errorID, Long poiID, Integer pstate) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_task_link_error ");
		sb.append(" SET updatetime=now(),pstate = " + pstate);
		sb.append(" WHERE taskID = " +  taskID);
		if (errorID != null && errorID.compareTo(0L) > 0) {
			sb.append(" AND errorid = " + errorID);
		}
		sb.append(" AND featureid = " + poiID);
		sb.append(" RETURNING *;");
		return sb.toString();
	}
}
