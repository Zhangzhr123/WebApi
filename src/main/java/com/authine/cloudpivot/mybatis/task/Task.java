package com.authine.cloudpivot.mybatis.task;

import com.authine.cloudpivot.mybatis.controller.TimeTaskController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {

     @Autowired
     private TimeTaskController timeTaskController;

     @Scheduled(fixedRate = 10*60*1000)
     public void doSomething(){

     }
}
