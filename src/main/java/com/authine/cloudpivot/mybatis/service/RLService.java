package com.authine.cloudpivot.mybatis.service;

import com.authine.cloudpivot.mybatis.entity.RLEntity;
import com.authine.cloudpivot.mybatis.mapper.RLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RLService {
    @Autowired
    RLMapper rlMapper;

    public RLEntity getDate(String date){
        return rlMapper.getDate(date);
    }
}
