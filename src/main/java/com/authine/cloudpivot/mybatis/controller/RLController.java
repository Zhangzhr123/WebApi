package com.authine.cloudpivot.mybatis.controller;


import com.authine.cloudpivot.mybatis.entity.RLEntity;
import com.authine.cloudpivot.mybatis.service.RLService;
import com.authine.cloudpivot.web.api.controller.base.BaseController;
import com.authine.cloudpivot.web.api.handler.CustomizedOrigin;
import com.authine.cloudpivot.web.api.view.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "二次开发测试接口", tags = "二次开发测试接口")
@RestController
@RequestMapping("/public/api/test")
public class RLController extends BaseController {

    @Autowired
    RLService rlService;

    @ApiOperation("获取配置信息接口")
    @GetMapping("/getDate")
    @ResponseBody
    @CustomizedOrigin(level = 5)
    public ResponseResult<RLEntity> GetDate(){
        RLEntity rlEntity = rlService.getDate("2020-07-14");
        System.out.println(rlEntity.toString());
//        getBizObjectFacade().getAttachmentByRefId();
        return getOkResponseResult(rlEntity,"Success");
    }

}
