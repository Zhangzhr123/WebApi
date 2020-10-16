package com.authine.cloudpivot.ext.controller;

import com.authine.cloudpivot.engine.api.model.event.EventModel;
import com.authine.cloudpivot.engine.api.model.organization.UserModel;
import com.authine.cloudpivot.web.api.dubbo.DubboConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/standard")
public class StandardController {

    @Autowired
    private DubboConfigService dubboConfigService;

    @GetMapping("/voidTest")
    public void voidTest(String param1, String param2, String param3, String param4) {
    }

    @GetMapping("/webapiTest")
    public UserModel webapiTest(String param1, String param2, String param3, String param4) {
        return new UserModel();
    }

    @GetMapping("/webapi-engine-rpc-test-void")
    public void rpcTestVoid() {
        dubboConfigService.getEventFacade().test("null", new HashMap<>());
    }

    @GetMapping("/webapi-engine-rpc-test")
    public UserModel rpcTest() {
        dubboConfigService.getEventFacade().test("null", new HashMap<>());
        return new UserModel();
    }

    @GetMapping("/engine-find")
    public List<EventModel> engineFind(String clientId) {
        return dubboConfigService.getEventFacade().findAllEvent(clientId);
    }

    @PostMapping("/engine-insert")
    public void engineInsert(@RequestBody EventModel eventModel) {
        eventModel.setClientId(UUID.randomUUID().toString());
        dubboConfigService.getEventFacade().insertEvent(eventModel);
    }

    @PostMapping("/engine-update")
    public void engineUpdate(@RequestBody EventModel eventModel) {
        dubboConfigService.getEventFacade().updateEvent(eventModel);
    }
}
