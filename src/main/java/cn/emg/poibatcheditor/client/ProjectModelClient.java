package cn.emg.poibatcheditor.client;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emg.poibatcheditor.common.ProjectsManageApiClientUtils;
import cn.emg.poibatcheditor.common.ProjectsManageApiParamModel;
import cn.emg.poibatcheditor.commonjar.ProcessTypeEnum;
import cn.emg.poibatcheditor.commonjar.SystemType;
import cn.emg.poibatcheditor.performance.PerformanceMonitor;
import cn.emg.poibatcheditor.pojo.ProjectModel;

@Service
public class ProjectModelClient {
	
	@Value("${projectApi.host}")
	private String host;
	@Value("${projectApi.port}")
	private String port;
	@Value("${projectApi.path}")
	private String path;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectModelClient.class);
	
	private final static String interUrl = "http://%s:%s/%s/interface.web";
	
	@PerformanceMonitor
	public ProjectModel selectProjectByID(Long projectID) throws Exception {
		ProjectModel process = new ProjectModel();
		try {
			ProjectsManageApiParamModel params = new ProjectsManageApiParamModel();
			params.setAction("selectProjectByID");
			params.putParams("pid", projectID);
			process = (ProjectModel) ProjectsManageApiClientUtils.getModel(String.format(interUrl, host, port, path), params , ProjectModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return process;
	}
	
	@PerformanceMonitor
	public List<?> selectMyProjects(Integer systemid, Integer userid, Integer roleid) throws Exception {
		ArrayList<?> myProjects = new ArrayList<ProjectModel>();
		try {
			ProjectsManageApiParamModel params = new ProjectsManageApiParamModel();
			params.setAction("selectMyProjects");
			params.putParams("systemid", systemid);
			params.putParams("userid", userid);
			params.putParams("roleid", roleid);
			myProjects = (ArrayList<?>) ProjectsManageApiClientUtils.getList(String.format(interUrl, host, port, path), params , ProjectModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return myProjects;
	}
	
	@PerformanceMonitor
	public List<?> selectProjectsByProcessName(String processName) throws Exception {
		ArrayList<?> myProjects = new ArrayList<ProjectModel>();
		try {
			ProjectsManageApiParamModel params = new ProjectsManageApiParamModel();
			params.setAction("selectProjectByName");
			params.putParams("name", URLEncoder.encode("%" + processName + "%", "utf-8"));
			params.putParams("proType", ProcessTypeEnum.GEN.getValue());
			params.putParams("systemid", SystemType.poi_GEN.getValue());
			myProjects = (ArrayList<?>) ProjectsManageApiClientUtils.getList(String.format(interUrl, host, port, path), params , ProjectModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return myProjects;
	}
}

