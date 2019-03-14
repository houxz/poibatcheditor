package cn.emg.poibatcheditor.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.emg.poibatcheditor.common.ChangePOIVO;
import cn.emg.poibatcheditor.common.HttpClientResult;
import cn.emg.poibatcheditor.common.HttpClientUtils;
import cn.emg.poibatcheditor.common.RoleEnum;
import cn.emg.poibatcheditor.commonjar.POIChangeTypeEnum;
import cn.emg.poibatcheditor.performance.PerformanceMonitor;
import cn.emg.poibatcheditor.pojo.POIDo;
import cn.emg.poibatcheditor.pojo.POIDoIndexWithError;
import cn.emg.poibatcheditor.pojo.TagDO;

@Service
public class POIModelClient {
	
	@Value("${poiApi.host}")
	private String host;
	@Value("${poiApi.port}")
	private String port;
	@Value("${poiApi.path}")
	private String path;
	
	private static final Logger logger = LoggerFactory.getLogger(POIModelClient.class);
	
	private final static String getPOIByIDsUrl = "http://%s:%s/%s/poi/ids/%s";
	
	private final static String getPOIMaxID = "http://%s:%s/%s/poi/maxid";
	
	private final static String updatePOIUrl = "http://%s:%s/%s/poi/upload/all";
	
	private final static String performancePOIUrl = "http://%s:%s/%s/poi/upload/performance";
	
	@PerformanceMonitor
	public List<POIDoIndexWithError> selectPOIByIDS(Set<Long> poiIDs) throws Exception {
		List<POIDoIndexWithError> pois = new ArrayList<POIDoIndexWithError>();
		try {
			List<Long> poiIDlist = new ArrayList<Long>(poiIDs);
			Integer size = poiIDlist.size();
			Integer bac = 100;
			for (Integer l = 0; l <= size/bac; l++) {
				Integer start = l * bac;
				Integer end = (l + 1) * bac;
				List<Long> _poiIDs = poiIDlist.subList(start.compareTo(size) > 0 ? size : start, end.compareTo(size) > 0 ? size : end);
				String sql = String.format(getPOIByIDsUrl, host, port, path, StringUtils.join(_poiIDs, ","));
				HttpClientResult result = HttpClientUtils.doGet(sql);
				if (!result.getStatus().equals(HttpStatus.OK))
					return pois;
				
				JSONArray json = JSONArray.parseArray(result.getJson());
				if (!json.isEmpty()) {
					for (Integer i = 0, len = json.size(); i < len; i++) {
						@SuppressWarnings("rawtypes")
						Map<String, Class> classMap = new HashMap<String, Class>();
						classMap.put("tags", TagDO.class);
						POIDoIndexWithError poi = (POIDoIndexWithError) JSONObject.toJavaObject(json.getJSONObject(i), POIDoIndexWithError.class);
						pois.add(poi);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return pois;
	}
	
	@SuppressWarnings("rawtypes")
	@PerformanceMonitor
	public POIDo selectPOIByID(Long poiID) throws Exception {
		try {
			String sql = String.format(getPOIByIDsUrl, host, port, path, poiID);
			HttpClientResult result = HttpClientUtils.doGet(sql);
			if (!result.getStatus().equals(HttpStatus.OK))
				return null;
			
			JSONArray json = JSONArray.parseArray(result.getJson());
			if (!json.isEmpty()) {
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("tags", TagDO.class);
				POIDo poi = (POIDo) JSONObject.toJavaObject(json.getJSONObject(0), POIDo.class);
				return poi;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return null;
	}
	
	@PerformanceMonitor
	public Long insertPOIs(POIDo poiDo) throws Exception {
		Long newPOIID = -1L;
		
		HttpClientResult result = HttpClientUtils.doGet(String.format(getPOIMaxID, host, port, path));
		if (!result.getStatus().equals(HttpStatus.OK))
			return newPOIID;
		
		String maxid = result.getJson();
		newPOIID = Long.valueOf(maxid.trim());
		poiDo.setId(newPOIID);
		
		for (TagDO tag : poiDo.getTags()) {
			tag.setId(newPOIID);
		}
		
		ChangePOIVO changeVO = new ChangePOIVO();
		changeVO.setRole(RoleEnum.edit);
		
		List<POIDo> poiCreate = new ArrayList<POIDo>();
		poiCreate.add(poiDo);
		changeVO.setPoiCreate(poiCreate);
		JSONObject json = (JSONObject) JSON.toJSON(changeVO);
		result = HttpClientUtils.doPost(String.format(updatePOIUrl, host, port, path), "application/json", json.toString());
		if (result.getStatus().equals(HttpStatus.OK) && !result.getJson().contains("error")) {
			return newPOIID;
		} else {
			return -1L;
		}
	}
	
	@PerformanceMonitor
	public Long updatePOIs(Long uId, List<POIDo> pois) {
		ChangePOIVO changeVO = new ChangePOIVO();
		changeVO.setRole(RoleEnum.edit);
		changeVO.setPoiModify(pois);
		changeVO.setuId(uId);
		
		JSONObject json = (JSONObject) JSON.toJSON(changeVO);
		
		HttpClientResult result = HttpClientUtils.doPost(String.format(updatePOIUrl, host, port, path), "application/json", json.toString());
		if (result.getStatus().equals(HttpStatus.OK) && !result.getJson().contains("error")) {
			return Long.valueOf(1);
		} else {
			return -1L;
		}
	}
	
	@PerformanceMonitor
	public Long deletePOIs(List<Long> poiIDs) {
		ChangePOIVO changeVO = new ChangePOIVO();
		changeVO.setRole(RoleEnum.edit);
		List<POIDo> poiDelete = new ArrayList<POIDo>();
		for (Long poiID : poiIDs) {
			POIDo poi = new POIDo();
			poi.setId(poiID);
			poiDelete.add(poi);
		}
		changeVO.setPoiDel(poiDelete);
		
		JSONObject json = (JSONObject) JSON.toJSON(changeVO);
		HttpClientResult result = HttpClientUtils.doPost(String.format(updatePOIUrl, host, port, path), "application/json", json.toString());
		if (result.getStatus().equals(HttpStatus.OK) && !result.getJson().contains("error")) {
			return Long.valueOf(1);
		} else {
			return -1L;
		}
	}
	
	@PerformanceMonitor
	public Long insertPOIPerformance(Long oid, POIChangeTypeEnum changeType, Integer owner, Long editver, Long uid, Long sourceid) {
		String urlFormat = String.format("%s?%s", performancePOIUrl, "oid=%d&changetype=%s&owner=%d&editver=%d&uid=%d&sourceid=%d");
		String url = String.format(urlFormat, host, port, path, oid, changeType, owner, editver, uid, sourceid);
		HttpClientResult result = HttpClientUtils.doPost(url, "application/json", null);
		if (result.getStatus().equals(HttpStatus.OK)) {
			return 1L;
		} else {
			return -1L;
		}
	}
}
