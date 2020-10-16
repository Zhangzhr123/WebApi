package com.authine.cloudpivot.ext.controller;

import ch.qos.logback.classic.LoggerContext;
import com.authine.cloudpivot.web.api.view.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/api/log_level")
public class LogUtilsController {

    @GetMapping("/update")
    @ResponseBody
    public ResponseResult<Void> updateLogLevel(@RequestParam("logger") String logger,
                                               @RequestParam("level") String level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(logger).setLevel(ch.qos.logback.classic.Level.toLevel(level));
        return ResponseResult.<Void>builder().errcode(0L).errmsg("SUCCESS").build();
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseResult<Void> printLog() {
        log.error("this is error message!!!");
        log.trace("this is trace message!!!");
        log.info("this is info message!!!");
        log.debug("this is debug message!!!");
        log.warn("this is warn message!!!");
        return ResponseResult.<Void>builder().errcode(0L).errmsg("SUCCESS").build();
    }
}
