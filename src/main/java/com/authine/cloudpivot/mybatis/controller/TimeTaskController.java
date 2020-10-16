package com.authine.cloudpivot.mybatis.controller;

import com.authine.cloudpivot.mybatis.service.TimeTaskService;
import com.authine.cloudpivot.web.api.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *定时任务:在会议可是日期前10分钟,和会议开始日期后10分钟,都发起一次签到提醒
 */
@Controller
@RequestMapping("/signRemind")
public class TimeTaskController extends BaseController {

    //public static boolean ifStart=false;
    @Autowired
    TimeTaskService taskService;

//    BlockingQueue queue= new LinkedBlockingDeque();
//    ThreadPoolExecutor executor=new ThreadPoolExecutor(3,6,1000, TimeUnit.SECONDS,queue);

    @RequestMapping
    public void signRemindTimeTask(){
//        if(!ifStart){
//            ifStart=true;
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    while (ifStart){
                        taskService.signRemindTimeTask();
//                        try {
//                            Thread.sleep(10*60000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }
    }

}
