package cn.emg.poibatcheditor.common;

import java.util.HashMap;
import java.util.Map;

public class ProjectsManageApiParamModel {
	
	private String action;
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void putParams(String key, Object value) {
		if (!this.params.containsKey(key))
			this.params.put(key, value);
	}
	public void removeParams(String key) {
		if (this.params.containsKey(key))
			this.params.remove(key);
	}
	public void clearParams() {
		this.params.clear();
	}
}
