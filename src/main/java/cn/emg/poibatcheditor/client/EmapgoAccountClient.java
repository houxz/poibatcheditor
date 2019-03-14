package cn.emg.poibatcheditor.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.emg.poibatcheditor.common.ProjectsManageApiClientUtils;
import cn.emg.poibatcheditor.common.ProjectsManageApiParamModel;
import cn.emg.poibatcheditor.performance.PerformanceMonitor;
import cn.emg.poibatcheditor.pojo.EmployeeModel;
import cn.emg.poibatcheditor.pojo.UserRoleModel;

@Component
public class EmapgoAccountClient {
	
	@Value("${emapgoAccountApi.host}")
	private String host;
	@Value("${emapgoAccountApi.port}")
	private String port;
	@Value("${emapgoAccountApi.path}")
	private String path;
	
	private static final Logger logger = LoggerFactory.getLogger(EmapgoAccountClient.class);
	
	private final static String interUrl = "http://%s:%s/%s/interface.web";

	@PerformanceMonitor
	public EmployeeModel getEmployeeByUsername(String username) throws Exception {
		EmployeeModel authority = new EmployeeModel();
		try {
			ProjectsManageApiParamModel params = new ProjectsManageApiParamModel();
			params.setAction("selectUserInfoByUsername");
			params.putParams("username", username);
			authority = (EmployeeModel) ProjectsManageApiClientUtils.getModel(String.format(interUrl, host, port, path), params , EmployeeModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return authority;
	}
	
	@PerformanceMonitor
	public List<?> selectUserRoleByUserID(Integer userid) throws Exception {
		List<?> list = new ArrayList<UserRoleModel>();
		try {
			ProjectsManageApiParamModel params = new ProjectsManageApiParamModel();
			params.setAction("selectUserRoleByUserID");
			params.putParams("userid", userid);
			list = (ArrayList<?>) ProjectsManageApiClientUtils.getList(String.format(interUrl, host, port, path), params , UserRoleModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return list;
	}
}

