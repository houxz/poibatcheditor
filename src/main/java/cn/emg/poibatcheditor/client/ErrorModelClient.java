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

import cn.emg.poibatcheditor.common.ExecuteSQLApiClientUtils;
import cn.emg.poibatcheditor.commonjar.ErrorStateEnum;
import cn.emg.poibatcheditor.commonjar.ModifyStateEnum;
import cn.emg.poibatcheditor.performance.PerformanceMonitor;
import cn.emg.poibatcheditor.pojo.ErrorModel;

@Service
public class ErrorModelClient {
	
	@Value("${errorApi.host}")
	private String host;
	@Value("${errorApi.port}")
	private String port;
	@Value("${errorApi.path}")
	private String path;
	
	private final static String SELECT = "select";
	private final static String UPDATE = "update";
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorModelClient.class);
	
	private final static String interUrl = "http://%s:%s/%s/poierror/%s/%s/execute";
	
	@PerformanceMonitor
	public ArrayList<?> addErrorModels(List<ErrorModel> records) throws Exception {
		ArrayList<?> list = new ArrayList<ErrorModel>();
		try {
			String sql = getAddErrorModelSQL(records);
			list = (ArrayList<?>)ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), ErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return list;
	}
	
	@PerformanceMonitor
	public ErrorModel addErrorModel(ErrorModel record) throws Exception {
		ErrorModel list = new ErrorModel();
		try {
			List<ErrorModel> records = new ArrayList<ErrorModel>() {
				private static final long serialVersionUID = 2592404962142224155L;
			{
				add(record);
			}};
			String sql = getAddErrorModelSQL(records);
			list = (ErrorModel)ExecuteSQLApiClientUtils.getModel(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), ErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return list;
	}
	
	@PerformanceMonitor
	public Long saveErrorModel(Long errorID, Long errorType, String checkValue) throws Exception {
		Long ret = -1L;
		try {
			String sql = getSaveErrorModelSQL(errorID, errorType, checkValue);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return ret;
	}
	
	@PerformanceMonitor
	public Long acceptErrorModel(List<Long> errorIDs) throws Exception {
		Long ret = -1L;
		try {
			String sql = getAcceptErrorModelSQL(errorIDs);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return ret;
	}
	
	@PerformanceMonitor
	public Long refuseErrorModel(Long errorID) throws Exception {
		Long ret = -1L;
		try {
			String sql = getRefuseErrorModelSQL(errorID);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return ret;
	}
	
	@PerformanceMonitor
	public Long anotherValueErrorModel(Long errorID, String editvalue) throws Exception {
		Long ret = -1L;
		try {
			String sql = getAnotherValueErrorModelSQL(errorID, editvalue);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return ret;
	}
	
	@PerformanceMonitor
	public Long deleteErrorModel(Long errorID) throws Exception {
		Long ret = -1L;
		try {
			String sql = getDeleteErrorModelSQL(errorID);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return ret;
	}
	
	@PerformanceMonitor
	public Long closeErrorModel(List<Long> errorIDs) throws Exception {
		Long ret = -1L;
		try {
			String sql = getCloseErrorModelSQL(errorIDs);
			ret = ExecuteSQLApiClientUtils.update(String.format(interUrl, host, port, path, UPDATE, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return ret;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getErrorModelsByErrorIDs(Set<Long> errorIDs) throws Exception {
		ArrayList<?> list = new ArrayList<ErrorModel>();
		try {
			String sql = getErrorModelsByErrorIDsSQL(errorIDs);
			list = (ArrayList<?>)ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), ErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return list;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getErrorModelsBypoiIDs(Set<Long> poiIDs) throws Exception {
		ArrayList<?> list = new ArrayList<ErrorModel>();
		try {
			String sql = getErrorModelsBypoiIDsSQL(poiIDs);
			list = (ArrayList<?>)ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), ErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return list;
	}
	
	@PerformanceMonitor
	public ArrayList<?> getUndoErrorModelsByTaskID(Long taskID) throws Exception {
		ArrayList<?> list = new ArrayList<ErrorModel>();
		try {
			String sql = getUndoErrorModelsByTaskIDSQL(taskID);
			list = (ArrayList<?>)ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), ErrorModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return list;
	}
	
	private String getAddErrorModelSQL(List<ErrorModel> records) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO tb_error ");
		sb.append("(taskid, featureid, layerid,field_name, editvalue, "
				+ "checkvalue, qid, errortype, level, modifystate, errorstate, "
				+ "checkroleid, changeroleid, isrelated) VALUES ");
		for (ErrorModel record : records) {
			sb.append("( ");
			sb.append(record.getTaskid() + ",");
			sb.append(record.getFeatureid() + ",");
			sb.append(record.getLayerid() + ",");
			sb.append("'" + record.getFieldname() + "',");
			sb.append("'" + record.getEditvalue() + "',");
			sb.append("'" + record.getCheckvalue() + "',");
			sb.append("'" + record.getQid() + "',");
			sb.append(record.getErrortype() + ", 0, ");
			sb.append(record.getModifystate() + ",");
			sb.append(record.getErrorstate() + ",");
			sb.append(record.getChangeroleid() + ",");
			sb.append(record.getChangeroleid() + ",");
			sb.append("0");
			sb.append("), ");
		}
		sb = sb.deleteCharAt(sb.length() - 2);
		sb.append("RETURNING *, field_name AS fieldname");
		return sb.toString();
	}
	
	private String getSaveErrorModelSQL(Long errorID, Long errorType, String checkValue) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_error ");
		sb.append(" SET errortype = " + errorType);
		sb.append(" , checkvalue = '" + checkValue + "'");
		sb.append(" WHERE id = " + errorID);
		return sb.toString();
	}
	
	private String getAcceptErrorModelSQL(List<Long> errorIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_error ");
		sb.append(" SET modifystate = " + ModifyStateEnum.ACCEPT_YES.getValue());
		sb.append(" , errorstate = " + ErrorStateEnum.ERROR_STATE_SLOVE.getValue());
		sb.append(" WHERE id IN (" + StringUtils.join(errorIDs, ",") + ")");
		return sb.toString();
	}
	
	private String getRefuseErrorModelSQL(Long errorID) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_error ");
		sb.append(" SET modifystate = " + ModifyStateEnum.ACCEPT_NO.getValue());
		sb.append(" , errorstate = " + ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
		sb.append(" WHERE id = " + errorID);
		return sb.toString();
	}
	
	private String getAnotherValueErrorModelSQL(Long errorID, String editvalue) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_error ");
		sb.append(" SET modifystate = " + ModifyStateEnum.ACCEPT_ANOTHER.getValue());
		sb.append(" , errorstate = " + ErrorStateEnum.ERROR_STATE_UN_SLOVE.getValue());
		sb.append(" , editvalue = '" + editvalue + "'");
		sb.append(" WHERE id = " + errorID);
		return sb.toString();
	}
	
	private String getCloseErrorModelSQL(List<Long> errorIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_error ");
		sb.append(" SET errorstate = " + ErrorStateEnum.ERROR_STATE_CLOSE.getValue());
		sb.append(" WHERE id IN (" + StringUtils.join(errorIDs, ",") + ")");
		return sb.toString();
	}
	
	private String getDeleteErrorModelSQL(Long errorID) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM tb_error ");
		sb.append(" WHERE id = " + errorID);
		return sb.toString();
	}
	
	private String getErrorModelsBypoiIDsSQL(Set<Long> poiIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *, field_name AS fieldname FROM tb_error ");
		sb.append(" WHERE featureid IN (" + StringUtils.join(poiIDs, ",") + ")");
		return sb.toString();
	}
	
	private String getErrorModelsByErrorIDsSQL(Set<Long> errorIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *, field_name AS fieldname FROM tb_error ");
		sb.append(" WHERE id IN (" + StringUtils.join(errorIDs, ",") + ")");
		return sb.toString();
	}
	
	private String getUndoErrorModelsByTaskIDSQL(Long taskID) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *, field_name AS fieldname FROM tb_error ");
		sb.append(" WHERE taskid = " + taskID);
		sb.append(" AND errorstate = 1");
		return sb.toString();
	}
	
	
}
