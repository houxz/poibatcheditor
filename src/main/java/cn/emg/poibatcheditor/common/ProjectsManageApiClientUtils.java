package cn.emg.poibatcheditor.common;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ProjectsManageApiClientUtils {
	
	public static Object getModel(String httpurl, ProjectsManageApiParamModel params, Class<?> modelClass) throws Exception {
		if (params.getAction().isEmpty())
			return null;
		
		Object model = modelClass.newInstance();
		
		String paramStr = getParamStr(params);
		HttpClientResult result = HttpClientUtils.doPost(httpurl, "application/x-www-form-urlencoded", paramStr);
		if (!result.getStatus().equals(HttpStatus.OK))
			return null;
		
		JSONObject json = JSONObject.parseObject(result.getJson());
		if (json.containsKey("status")) {
			Boolean status = json.getBoolean("status");
			if (status) {
				if (json.containsKey("option")) {
					JSONObject data = json.getJSONObject("option");
					model = JSON.parseObject(data.toJSONString(), modelClass);
				}
			}
		}
		return model;
	}
	
	public static ArrayList<Object> getList(String httpurl, ProjectsManageApiParamModel params, Class<?> modelClass) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		if (params.getAction().isEmpty())
			return list;
		
		String paramStr = getParamStr(params);
		HttpClientResult result = HttpClientUtils.doPost(httpurl, "application/x-www-form-urlencoded", paramStr);
		if (!result.getStatus().equals(HttpStatus.OK))
			return list;
		
		JSONObject json = JSONObject.parseObject(result.getJson());
		if (json.containsKey("status")) {
			Boolean status = json.getBoolean("status");
			if (status) {
				if (json.containsKey("option")) {
					JSONArray data = json.getJSONArray("option");
					if (data != null && data.size() > 0) {
						for (Integer i = 0, len = data.size(); i < len; i++) {
							Object model = modelClass.newInstance();
							model = JSON.parseObject(data.getJSONObject(i).toJSONString(), modelClass);
							list.add(model);
						}
					}
				}
			}
		}
		return list;
	}
	
	private static String getParamStr(ProjectsManageApiParamModel params) {
		StringBuilder param = new StringBuilder();
		param.append("action").append("=").append(params.getAction());
		for (Map.Entry<String, Object> entry : params.getParams().entrySet()) { 
			param.append("&");
			param.append(entry.getKey()).append("=");
			Object value = entry.getValue();
			if (value instanceof Integer ||
				value instanceof Double ||
				value instanceof Float ||
				value instanceof Long) {
				param.append(value);
			} else {
				param.append(value);
			}
		}
		return param.toString();
	}
}