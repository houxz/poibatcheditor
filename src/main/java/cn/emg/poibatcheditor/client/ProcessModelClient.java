package cn.emg.poibatcheditor.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emg.poibatcheditor.common.ProjectsManageApiClientUtils;
import cn.emg.poibatcheditor.common.ProjectsManageApiParamModel;
import cn.emg.poibatcheditor.performance.PerformanceMonitor;
import cn.emg.poibatcheditor.pojo.ConfigValueModel;
import cn.emg.poibatcheditor.pojo.ProcessModel;

@Service
public class ProcessModelClient {
	
	@Value("${processApi.host}")
	private String host;
	@Value("${processApi.port}")
	private String port;
	@Value("${processApi.path}")
	private String path;
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessModelClient.class);
	
	private final static String interUrl = "http://%s:%s/%s/interface.web";
	
	@PerformanceMonitor
	public ProcessModel selectProcessByID(Long processID) throws Exception {
		ProcessModel process = new ProcessModel();
		try {
			ProjectsManageApiParamModel params = new ProjectsManageApiParamModel();
			params.setAction("selectProcessByID");
			params.putParams("processid", processID);
			process = (ProcessModel) ProjectsManageApiClientUtils.getModel(String.format(interUrl, host, port, path), params , ProcessModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return process;
	}

	@PerformanceMonitor
	public List<?> selectProcessConfigs(Long processid, Integer moduleid, Integer configid) throws Exception {
		List<?> myProjects = new ArrayList<ConfigValueModel>();
		try {
			ProjectsManageApiParamModel params = new ProjectsManageApiParamModel();
			params.setAction("selectProcessConfigs");
			params.putParams("processid", processid);
			params.putParams("moduleid", moduleid);
			params.putParams("configid", configid);
			myProjects = (ArrayList<?>) ProjectsManageApiClientUtils.getList(String.format(interUrl, host, port, path), params , ConfigValueModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return myProjects;
	}
	
}

