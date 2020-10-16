package com.authine.cloudpivot.mybatis.service;

import com.authine.cloudpivot.mybatis.entity.PageData;
import com.authine.cloudpivot.mybatis.mapper.TimeTaskMapper;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeTaskService {

    @Autowired
    TimeTaskMapper timeTaskMapper;

    public void signRemindTimeTask() {
        List<PageData> pdList = timeTaskMapper.getMeetingInfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        int dqsj = (int) new Date().getTime();

        if (pdList.size() != 0) {

            for (PageData pd : pdList) {

                try {
                    int hyksrq = (int) sdf.parse(pd.get("ydksrq").toString()).getTime();
                    PageData pdCreaterId = timeTaskMapper.getUserId(pd.get("creater").toString());
                    //获取使用人id
                    String syrName = (pd.get("syr").toString()).substring(1, (pd.get("syr").toString()).length() - 1);
                    Gson gson = new Gson();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map = gson.fromJson(syrName, map.getClass());
                    String id = (String) map.get("id");
                    PageData pdSYRId = timeTaskMapper.getUserId(id);
                    StringBuffer stringBuffer = new StringBuffer();
                    String userIdList = stringBuffer.append(pdCreaterId).append(",").append(pdSYRId).toString();

                    if (dqsj < hyksrq && dqsj > hyksrq - 10 * 60 * 1000) {
                        //获取token
                        DingTalkClient client0 = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
                        OapiGettokenRequest req0 = new OapiGettokenRequest();
                        //测试信息
                        req0.setAppkey("dingf9opadn3615tjbdy");//dingxigqmxvneops7uce
                        //8oFKGdkjk850jKtp1UmNwObfot-9F74_LNQ5e2Onkem88RiVRdA7iv4l_OXqQ_5U
                        req0.setAppsecret("uKW-Z9Ogls-ILWS8pZVzHTb2PCYf--CEIUFQYZPq8CNavK_XekZbvPXgJ1G5XnQ_");
                        req0.setHttpMethod("GET");
                        OapiGettokenResponse rsp0 = client0.execute(req0);
                        System.out.println(rsp0.getBody());
                        String accessToken = rsp0.getAccessToken();


                        //消息接口
                        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
                        OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
                        req.setAgentId((long) 761685751);//763436042
                        //正式
                        //req.setUseridList(userIdList);
                        req.setUseridList("305735330524193727");//305735330524193727
                        OapiMessageCorpconversationAsyncsendV2Request.Msg obj1 = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
                        obj1.setMsgtype("text");
                        OapiMessageCorpconversationAsyncsendV2Request.Text obj2 = new OapiMessageCorpconversationAsyncsendV2Request.Text();
                        obj2.setContent("请到会议室签到,如果您已签到,请忽略!");
                        obj1.setText(obj2);
                        req.setMsg(obj1);
                        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(req, accessToken);
                        System.out.println(rsp.getBody());
                    }

                    if (dqsj > hyksrq && dqsj < hyksrq + 10 * 60 * 1000) {
                        //获取token
                        DingTalkClient client0 = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
                        OapiGettokenRequest req0 = new OapiGettokenRequest();
                        req0.setAppkey("dingf9opadn3615tjbdy");//dingxigqmxvneops7uce
                        //8oFKGdkjk850jKtp1UmNwObfot-9F74_LNQ5e2Onkem88RiVRdA7iv4l_OXqQ_5U
                        req0.setAppsecret("uKW-Z9Ogls-ILWS8pZVzHTb2PCYf--CEIUFQYZPq8CNavK_XekZbvPXgJ1G5XnQ_");
                        req0.setHttpMethod("GET");
                        OapiGettokenResponse rsp0 = client0.execute(req0);
                        System.out.println(rsp0.getBody());
                        String accessToken = rsp0.getAccessToken();


                        //消息接口
                        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
                        OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
                        req.setAgentId((long) 761685751);//测试
                        //正式
                        //req.setUseridList(userIdList);//763436042
                        req.setUseridList("305735330524193727");//305735330524193727
                        OapiMessageCorpconversationAsyncsendV2Request.Msg obj1 = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
                        obj1.setMsgtype("text");
                        OapiMessageCorpconversationAsyncsendV2Request.Text obj2 = new OapiMessageCorpconversationAsyncsendV2Request.Text();
                        obj2.setContent("请到会议室签到,如果您已签到,请忽略!");
                        obj1.setText(obj2);
                        req.setMsg(obj1);
                        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(req, accessToken);
                        System.out.println(rsp.getBody());
                    }

                    if (dqsj > hyksrq + 15 * 60 * 1000) {
                        timeTaskMapper.updateSFQXHY(pd.get("sequenceNo").toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }



}
