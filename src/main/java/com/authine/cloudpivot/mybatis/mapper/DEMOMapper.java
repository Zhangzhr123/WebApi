package com.authine.cloudpivot.mybatis.mapper;

import com.authine.cloudpivot.mybatis.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DEMOMapper {

    DEMOEntity getDEMO(String workflowInstanceId);

    List<DEMO1Entity> getDEMO1(String parentId);//GHFWQD

    List<DEMO4Entity> getDEMO4(String parentId);//GJYQJQD

    List<DEMO2Entity> getDEMO2_1(String parentId);//BCPBJJDDSC
    List<DEMO2Entity> getDEMO2_2(String parentId);//BCPBJJDLB
    List<DEMO2Entity> getDEMO2_3(String parentId);//BCPBJJDNCC
    List<DEMO2Entity> getDEMO2_4(String parentId);//BCPBJJDTC
    List<DEMO2Entity> getDEMO2_5(String parentId);//BCPBJJDTM
    List<DEMO2Entity> getDEMO2_6(String parentId);//BCPBJJDTQ
    List<DEMO2Entity> getDEMO2_7(String parentId);//DLJDDSC
    List<DEMO2Entity> getDEMO2_8(String parentId);//DLJDGDT
    List<DEMO2Entity> getDEMO2_9(String parentId);//DLJDLB
    List<DEMO2Entity> getDEMO2_10(String parentId);//DLJDNCCHTC
    List<DEMO2Entity> getDEMO2_11(String parentId);//DLJDTM
    List<DEMO2Entity> getDEMO2_12(String parentId);//KCJD

    List<DEMO3Entity> getDEMO3_1(String parentId);//DSCGLJ
    List<DEMO3Entity> getDEMO3_2(String parentId);//GDTGLJ
    List<DEMO3Entity> getDEMO3_3(String parentId);//TMCGLJ
    List<DEMO3Entity> getDEMO3_4(String parentId);//ZGLJ
    List<DEMO3Entity> getDEMO3_5(String parentId);//ZJ
}
