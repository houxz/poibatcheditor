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

import com.alibaba.fastjson.JSONObject;

import cn.emg.poibatcheditor.common.ExecuteSQLApiClientUtils;
import cn.emg.poibatcheditor.performance.PerformanceMonitor;
import cn.emg.poibatcheditor.pojo.AttachModel;
import cn.emg.poibatcheditor.pojo.ShapeModel;

@Service
public class FielddataModelClient {
	
	@Value("${fielddataApi.host}")
	private String host;
	@Value("${fielddataApi.port}")
	private String port;
	@Value("${fielddataApi.path}")
	private String path;
	
	private final static String SELECT = "select";
//	private final static String UPDATE = "update";
	
	private static final Logger logger = LoggerFactory.getLogger(FielddataModelClient.class);
	
	private final String interUrl = "http://%s:%s/%s/poifielddata/%s/%s/execute";
	
	@PerformanceMonitor
	public List<?> selectShapeByIDS(Set<Long> shapeIDs) throws Exception {
		ArrayList<?> attaches = new ArrayList<AttachModel>();
		try {
			String sql = getShapeSQL(shapeIDs);
			attaches = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), ShapeModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return attaches;
	}
	
	@PerformanceMonitor
	public JSONObject selectShapeGeoJsonData(Set<Long> shapeIDS) throws Exception {
		JSONObject json = new JSONObject();
		try {
			String sql = getShapeGeoJsonDataSQL(shapeIDS);
			json = ExecuteSQLApiClientUtils.getGeoJSON(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return json;
	}
	
	@PerformanceMonitor
	public List<?> selectAttachByIDS(Set<Long> attachIDS) throws Exception {
		ArrayList<?> attaches = new ArrayList<AttachModel>();
		try {
			String sql = getAttachSQL(attachIDS);
			attaches = (ArrayList<?>) ExecuteSQLApiClientUtils.getList(String.format(interUrl, host, port, path, SELECT, URLEncoder.encode(URLEncoder.encode(sql, "utf-8"), "utf-8")), AttachModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		return attaches;
	}
	
	private String getShapeGeoJsonDataSQL(Set<Long> shapeIDS) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT jsonb_build_object( ");
		sb.append("		'type',     'FeatureCollection', ");
		sb.append("		'features', jsonb_agg(features.feature) ");
		sb.append("		) ");
		sb.append("FROM ( ");
		sb.append("		SELECT jsonb_build_object( ");
		sb.append("				'type',       'Feature', ");
		sb.append("				'id',         id, ");
		sb.append("				'geometry',   ST_AsGeoJSON(shape)::jsonb, ");
		sb.append("				'properties', to_jsonb(inputs) - 'id' - 'shape' ");
		sb.append("				) AS feature ");
		sb.append("		FROM (SELECT * FROM tb_record_shape WHERE id IN (" + StringUtils.join(shapeIDS, ",") + ")) inputs) features; ");
		return sb.toString();
	}
	
	private String getAttachSQL(Set<Long> attachIDS) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT to_char(st_x( a.location ), '999D99999999') AS x, to_char(st_y( a.location ), '999D99999999') AS y, st_astext(a.location) AS location, a.* ");
		sb.append("FROM tb_record_attach a ");
		sb.append("WHERE a.id IN (" + StringUtils.join(attachIDS, ",") + ") ");
		sb.append("ORDER BY a.name ");
		return sb.toString();
	}
	
	private String getShapeSQL(Set<Long> shapeIDS) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT s.*, ST_ASTEXT(s.shape) AS geostr, ");
		sb.append(" ( SELECT sg.VALUE FROM tb_record_shape_gen sg WHERE sg.key = 'task_no' AND sg.shapeid = s.id ) AS srctaskid, ");
		sb.append(" ( SELECT sg.VALUE FROM tb_record_shape_gen sg WHERE sg.key = 'name' AND sg.shapeid = s.id ) AS srctaskname ");
		sb.append("FROM tb_record_shape s ");
		sb.append("WHERE s.id IN (" + StringUtils.join(shapeIDS, ",") + ") ");
		return sb.toString();
	}
}
