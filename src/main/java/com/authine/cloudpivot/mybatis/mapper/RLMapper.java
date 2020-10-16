package com.authine.cloudpivot.mybatis.mapper;

import com.authine.cloudpivot.mybatis.entity.RLEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RLMapper {

    RLEntity getDate(String date);
}
