package cn.emg.poibatcheditor.common;

import java.util.Map;

import cn.emg.poibatcheditor.pojo.EmployeeModel;

public class RequestModule {
	private EmployeeModel userInfo;
	private Map<String, Object> reqParams;
	private String source;
	public EmployeeModel getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(EmployeeModel userInfo) {
		this.userInfo = userInfo;
	}
	public Map<String, Object> getReqParams() {
		return reqParams;
	}
	public void setReqParams(Map<String, Object> reqParams) {
		this.reqParams = reqParams;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getParameter(String key) {
		if (!this.reqParams.containsKey(key))
			return null;
		
		return this.reqParams.get(key).toString();
	}
	
	public Integer getIntParameter(String key, Integer defaultNum) {
		if (!this.reqParams.containsKey(key) || this.reqParams.get(key) == null)
			return defaultNum;
		
		String temp = this.reqParams.get(key).toString();
		if (temp != null && !temp.equals("")) {
			temp = temp.trim();
			Integer num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}
	
	public Boolean getBoolParamter(String key) {
		Boolean ret = Boolean.FALSE;
		if (!this.reqParams.containsKey(key) || this.reqParams.get(key) == null)
			return ret;
		
		String temp = this.reqParams.get(key).toString();
		if (temp != null && !temp.equals("")) {
			temp = temp.trim();
			try {
				ret = Boolean.parseBoolean(temp);
			} catch (Exception ignored) {
			}
		}
		return ret;
	}
	
	public Long getLongParameter(String key, Long defaultNum) {
		if (!this.reqParams.containsKey(key) || this.reqParams.get(key) == null)
			return defaultNum;
		
		String temp = this.reqParams.get(key).toString();
		if (temp != null && !temp.equals("")) {
			temp = temp.trim();
			Long num = defaultNum;
			try {
				num = Long.parseLong(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}
	
	public Double getDoubleParameter(String key, Double defaultNum) {
		if (!this.reqParams.containsKey(key) || this.reqParams.get(key) == null)
			return defaultNum;
		
		String temp = this.reqParams.get(key).toString();
		if (temp != null && !temp.equals("")) {
			temp = temp.trim();
			Double num = defaultNum;
			try {
				num = Double.parseDouble(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}
}
