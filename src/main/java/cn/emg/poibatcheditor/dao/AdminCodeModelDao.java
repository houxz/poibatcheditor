package cn.emg.poibatcheditor.dao;

import cn.emg.poibatcheditor.pojo.AdminCodeModel;
import cn.emg.poibatcheditor.pojo.AdminCodeModelExample;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface AdminCodeModelDao {
	
	List<AdminCodeModel> selectOnTree(@Param("list") Set<Integer> list);
	
    int countByExample(AdminCodeModelExample example);

    int deleteByExample(AdminCodeModelExample example);

    int insert(AdminCodeModel record);

    int insertSelective(AdminCodeModel record);

    List<AdminCodeModel> selectByExample(AdminCodeModelExample example);

    int updateByExampleSelective(@Param("record") AdminCodeModel record, @Param("example") AdminCodeModelExample example);

    int updateByExample(@Param("record") AdminCodeModel record, @Param("example") AdminCodeModelExample example);
}