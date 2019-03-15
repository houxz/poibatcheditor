package cn.emg.poibatcheditor.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface POIModelDao {
	
    List<Map<String, Object>> select(@Param("columns") Set<String> columns, @Param("code") String code);
    
}