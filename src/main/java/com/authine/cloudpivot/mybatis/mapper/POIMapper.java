package com.authine.cloudpivot.mybatis.mapper;

import com.authine.cloudpivot.mybatis.entity.LCBEntity;
import com.authine.cloudpivot.mybatis.entity.POIEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface POIMapper {

    POIEntity getPOI(String workflowInstanceId);

    List<LCBEntity> getLCB(String parentId);
}
