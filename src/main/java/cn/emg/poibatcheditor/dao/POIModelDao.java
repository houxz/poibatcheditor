package cn.emg.poibatcheditor.dao;

import cn.emg.poibatcheditor.pojo.POIModel;
import cn.emg.poibatcheditor.pojo.POIModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface POIModelDao {
    int countByExample(POIModelExample example);

    int deleteByExample(POIModelExample example);

    int deleteByPrimaryKey(Long oid);

    int insert(POIModel record);

    int insertSelective(POIModel record);

    List<POIModel> selectByExample(POIModelExample example);

    POIModel selectByPrimaryKey(Long oid);

    int updateByExampleSelective(@Param("record") POIModel record, @Param("example") POIModelExample example);

    int updateByExample(@Param("record") POIModel record, @Param("example") POIModelExample example);

    int updateByPrimaryKeySelective(POIModel record);

    int updateByPrimaryKey(POIModel record);
}