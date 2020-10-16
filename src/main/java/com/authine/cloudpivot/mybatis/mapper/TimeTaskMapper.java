package com.authine.cloudpivot.mybatis.mapper;

import com.authine.cloudpivot.mybatis.entity.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TimeTaskMapper {

    List<PageData> getMeetingInfo();

    PageData getUserId(String RYId);


    void updateSFQXHY(String sequenceNo);
}
