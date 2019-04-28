package cn.emg.poibatcheditor.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface AdminModelDao {
	
    List<Map<String, Object>> select(@Param("columns") Set<String> columns, @Param("code") String code, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    Integer count(@Param("columns") Set<String> columns, @Param("code") String code);
    
    List<Map<String, Object>> selectByOwners(@Param("columns") Set<String> columns, @Param("owners") Set<Integer> owners, @Param("limit") Integer limit, @Param("offset") Integer offset);
    
    Integer countByOwners(@Param("columns") Set<String> columns, @Param("owners") Set<Integer> owners);
    
}