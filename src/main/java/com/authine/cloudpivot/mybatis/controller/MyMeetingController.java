package com.authine.cloudpivot.mybatis.controller;

import com.authine.cloudpivot.engine.api.model.organization.UserModel;
import com.authine.cloudpivot.mybatis.entity.MeetingApplyInfo;
import com.authine.cloudpivot.mybatis.entity.PageData;
import com.authine.cloudpivot.mybatis.service.SearchMeetingRoomService;
import com.authine.cloudpivot.mybatis.utils.R;
import com.authine.cloudpivot.web.api.controller.base.BaseController;
import com.authine.cloudpivot.web.api.dubbo.DubboConfigService;
import com.authine.cloudpivot.web.api.handler.CustomizedOrigin;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.google.gson.Gson;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "智能会议室接口", tags = "智能会议室接口 ")
@RestController
@RequestMapping("/public/api/meeting")
public class MyMeetingController extends BaseController {

    @Autowired
    private DubboConfigService dubboConfigService;
    @Autowired
    SearchMeetingRoomService searchMeetingRoomService;


    /*
    * 根据传过来的creater人名,查到该名下所有的已经发起的会议室申请
    * */
    @ResponseBody
    @RequestMapping("/getOwnApply")
    @CustomizedOrigin(level = 5)
    public R getOwnApply(String creater, HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String fqr = creater + "";
        String syr = "%" + creater + "%";
        //获取会议室流程
        List<PageData> ownList = searchMeetingRoomService.getOwnApply(fqr, syr);
        List<PageData> pdlist = new ArrayList<>();
        for (PageData pd : ownList) {
            String hysmc = pd.get("hysmc").toString();
            PageData roomName = searchMeetingRoomService.getRoomName(hysmc);
            pd.put("hysmc", roomName.get("hysmc"));
            String createrName = pd.get("creater").toString();
            createrName = searchMeetingRoomService.gerUserName(createrName);
            pd.put("creater", createrName);
            //类型转换
            String syrName = (pd.get("syr").toString()).substring(1, (pd.get("syr").toString()).length() - 1);
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<String, Object>();
            map = gson.fromJson(syrName, map.getClass());
            String id = (String) map.get("id");
            syrName = searchMeetingRoomService.gerUserName(id);
            pd.put("syr", syrName);
            pdlist.add(pd);
        }
        return R.ok().put("data", pdlist);
    }

    /*
    * 取消会议
    * */
    @ResponseBody
    @RequestMapping("/cancel")
    public R cancel(@RequestParam(value = "workflowInstanceId", required = true) String workflowInstanceId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        System.out.println(workflowInstanceId);
        searchMeetingRoomService.cansell(workflowInstanceId);

        return R.ok();
    }

    /*
     * 会议室签到
     * */
    @ResponseBody
    @RequestMapping("/meetingSign")
    public R meetingSign(@RequestParam(value = "workflowInstanceId", required = true) String workflowInstanceId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        searchMeetingRoomService.meetingSign(workflowInstanceId);
        return R.ok();
    }

    /*
   * 会议能否延长的判断
   * 首先判断当前会议和已提交申请的本会议室的会议的最短间隔时间,如果最短间隔时间<30分钟,返回false,
   * 最短间隔时间>30分钟,返回true
   * */
    @GetMapping(value = "/lengthen")
    @ResponseBody
    public R canLengthen(@RequestParam(value = "workflowInstanceId", required = true) String workflowInstanceId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        PageData pd = searchMeetingRoomService.getHYSLCBySequenceNo(workflowInstanceId);
        String hysmc = pd.get("hysmc").toString();
        String dqhyjssj = pd.get("ydjsrq").toString();
        String dqSequenceNo = pd.get("workflowInstanceId").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<Integer> listInt = new ArrayList<>();
        try {
            int dqjssj = (int) (sdf.parse(dqhyjssj).getTime() / 1000);//当前结束时间的时间戳
            List<PageData> pdList = searchMeetingRoomService.getMeetings(hysmc, dqSequenceNo, dqhyjssj);
            if (pdList.size() != 0) {
                for (PageData pdl : pdList) {
                    int syhykssj = (int) (sdf.parse(pdl.get("ydksrq").toString()).getTime() / 1000);
                    listInt.add(syhykssj);
                }
                int zxsj = Collections.min(listInt);//最小时间---即最近的会议开始时间
                int jgsj = (int) (zxsj - dqjssj) / 60;
                if (jgsj - 30 <= 0) {
                    return R.ok().put("flag", false);
                } else {
                    return R.ok().put("flag", true).put("jgsj", jgsj);
                }
            } else {
                return R.ok().put("flag", true).put("jgsj", 0);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return R.ok().put("flag", false);
        }
    }

    /*
    * 延长会议
    * 将本次会议结束时间延长
    * */
    @ResponseBody
    @GetMapping("/updateMeeting")
    public R updateMeeting(@RequestParam(value = "min", required = true) String min, @RequestParam(value = "workflowInstanceId", required = true) String workflowInstanceId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        System.out.println("min===" + min + "workflowInstanceId===" + workflowInstanceId);
        searchMeetingRoomService.updateMeeting(min, workflowInstanceId);
        return R.ok();
    }

    /*
    * 会议提前和结束
    * */
    @ResponseBody
    @GetMapping("/overEarly")
    public R overEarly(@RequestParam(value = "workflowInstanceId", required = true) String workflowInstanceId, @RequestParam(value = "ydjsrq", required = true) String ydjsrq, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        //取消会议,变更会议结束时间
        searchMeetingRoomService.overEarly(workflowInstanceId);

        //给与会人员,发送会议提前结束通知
        PageData pdConferee = searchMeetingRoomService.getConferee(workflowInstanceId);

        JSONArray jsonArray = JSONArray.fromObject(pdConferee.get("gschry"));
        StringBuffer strNull = new StringBuffer("");
        for (int i = 0; i < jsonArray.size(); i++) {
            String chryId = jsonArray.getJSONObject(i).getString("id");
            PageData pdUserId = searchMeetingRoomService.getUserID(chryId);
            strNull.append(",").append(pdUserId.get("userId").toString());
        }

        String substring = strNull.toString().substring(1);
        try {
            //获取token
            DingTalkClient client0 = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req0 = new OapiGettokenRequest();
            //测试信息
            req0.setAppkey("dingf9opadn3615tjbdy");
            req0.setAppsecret("uKW-Z9Ogls-ILWS8pZVzHTb2PCYf--CEIUFQYZPq8CNavK_XekZbvPXgJ1G5XnQ_");
            req0.setHttpMethod("GET");
            OapiGettokenResponse rsp0 = client0.execute(req0);
            System.out.println(rsp0.getBody());
            String accessToken = rsp0.getAccessToken();

            //消息接口
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
            OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
            req.setAgentId((long) 761685751);
            //正式
            req.setUseridList("305735330524193727");
            OapiMessageCorpconversationAsyncsendV2Request.Msg obj1 = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            obj1.setMsgtype("text");
            OapiMessageCorpconversationAsyncsendV2Request.Text obj2 = new OapiMessageCorpconversationAsyncsendV2Request.Text();
            obj2.setContent("您于" + ydjsrq + "结束的会议,已提前结束,特此通知!");
            obj1.setText(obj2);
            req.setMsg(obj1);
            OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    /*
    * 根据会议室名称,会议开始时间,会议结束时间
    * 查询这个会议室在此开始时间和结束时间之内,有没有已发起的会议室申请
    * 如果有,返回true,如果没有,返回false
    * */
    @ResponseBody
    @GetMapping("/isHaveAppliedMeetingRoom")
    @CustomizedOrigin(level = 5)
    public boolean isHaveAppliedMeetingRoom(@RequestParam(value = "roomName", required = true) String roomName1, @RequestParam(value = "ksrq", required = true) String ksrq, @RequestParam(value = "jsrq", required = true) String jsrq, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<PageData> dataList = new ArrayList<>();
        try {
            ksrq = formatter.format(formatter.parse(ksrq));
            jsrq = formatter.format(formatter.parse(jsrq));
            System.out.println(roomName1 + "=====" + ksrq + "======" + jsrq);
            dataList = searchMeetingRoomService.isHadApplied(roomName1, ksrq, jsrq);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (0 != dataList.size()) {
            return false;
        } else {
            return true;
        }
    }


    /*
     * 查询所有会议室的预定情况
     *
     * */
    @RequestMapping("/allMeetingRoomApply")
    @ResponseBody
    public R allMeetingRoomApply(@RequestParam(value = "date", required = true) String date, HttpServletResponse response) throws ParseException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String kssj = date + " 00:00:00";
        String jssj = date + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        if (date.contains("/")) {
            kssj = kssj.replaceAll("/", "-");
            jssj = jssj.replaceAll("/", "-");
        }
        System.out.println("------------>" + kssj);
        System.out.println("------------>" + jssj);

        ArrayList<MeetingApplyInfo> arrMeetingInfoList = new ArrayList<>();
        ArrayList<String> arrList = new ArrayList<>();
        List<PageData> pdList = searchMeetingRoomService.getAllMeetingApply(kssj, jssj);
        if (pdList != null && pdList.size() > 0) {
            //遍历查询到的会议室预定数据中的会议室名称
            for (PageData pd : pdList) {
                //判断会议室是否已存在
                if (!arrList.contains(pd.get("hysmc").toString())) {
                    arrList.add(pd.get("hysmc").toString());
                }
            }
            List<PageData> hysList = new ArrayList<>();
            //判断是否查询到数据
            if (arrList != null && arrList.size() > 0) {
                //循环将会议室数据添加到list集合中
                for (int i = 0; i < arrList.size(); i++) {
                    PageData hys = searchMeetingRoomService.getHYSXX(arrList.get(i));
                    if (hys != null) {
                        hysList.add(hys);
                    }
                }
            }
            System.out.println(pdList.size() + "查询到的会议室预定流程有====" + pdList);
            System.out.println(arrList.size() + "查询到的会议室名称有====" + arrList);
            System.out.println(hysList.size() + "查询到的会议室有====" + hysList);
            List<PageData> ydList = new ArrayList<>();

            //判断是否查询到对应会议室
            if (hysList != null && hysList.size() > 0) {
                //先循环看有多少会议室被预定
                for (int j = 0; j < hysList.size(); j++) {
                    List<String> list1 = new ArrayList<>();
                    List<String> list2 = new ArrayList<>();
                    //循环所有查询到的会议室预定流程
                    for (int i = 0; i < pdList.size(); i++) {
                        //根据会议室名称进行分类
                        if ((pdList.get(i).get("hysmc").toString()).equals(hysList.get(j).get("hysmc").toString())) {
                            //获取同一会议室会议预定的开始时间和结束时间
                            int starthours = sdf.parse(pdList.get(i).get("ydksrq").toString()).getHours();
                            int startMinutes = sdf.parse(pdList.get(i).get("ydksrq").toString()).getMinutes();
                            System.out.println("starthours===" + starthours);
                            System.out.println("startMinutes===" + startMinutes);
                            if (startMinutes >= 0 && startMinutes <= 59) {
                                list1.add((starthours + 1) + ":00");
                            } else {
                                list1.add((starthours) + ":00");
                            }
                            int endhours = sdf.parse(pdList.get(i).get("ydjsrq").toString()).getHours();
                            int endMinutes = sdf.parse(pdList.get(i).get("ydjsrq").toString()).getMinutes();
                            System.out.println("endhours===" + endhours);
                            System.out.println("endMinutes===" + endMinutes);
                            if (endMinutes >= 0 && endMinutes <= 59) {
                                list2.add((endhours + 1) + ":00");
                            } else {
                                list2.add((endhours) + ":00");
                            }
                            System.out.println("startTime===" + list1);
                            System.out.println("endTime===" + list2);
                        }
                    }
                    System.out.println("list1===" + list1);
                    System.out.println("list2===" + list2);
                    PageData yd = new PageData();
                    //会议室基本信息
                    yd.put("hysmc", hysList.get(j).get("hysmc").toString());
                    yd.put("sbmx", hysList.get(j).get("sbmx").toString());
                    String rnrs = hysList.get(j).get("rnrs").toString();
                    rnrs = rnrs.substring(0, rnrs.indexOf("."));
                    yd.put("rnrs", rnrs);
                    yd.put("hysdd", hysList.get(j).get("hysdd").toString());
                    yd.put("refId", hysList.get(j).get("refId").toString());
                    yd.put("startTime", list1);
                    yd.put("endTime", list2);
                    ydList.add(yd);
                }
            }

            return R.ok().put("flag", true).put("data", ydList);

        } else {

            List<PageData> datalist1 = searchMeetingRoomService.getAllHYS();
            List<PageData> ydList = new ArrayList<>();

            if (datalist1 != null && datalist1.size() > 0) {
                for (int j = 0; j < datalist1.size(); j++) {
                    PageData yd = new PageData();
                    //会议室基本信息
                    yd.put("hysmc", datalist1.get(j).get("hysmc").toString());
                    yd.put("sbmx", datalist1.get(j).get("sbmx").toString());
                    String rnrs = datalist1.get(j).get("rnrs").toString();
                    rnrs = rnrs.substring(0, rnrs.indexOf("."));
                    yd.put("rnrs", rnrs);
                    yd.put("hysdd", datalist1.get(j).get("hysdd").toString());
                    yd.put("refId", datalist1.get(j).get("refId").toString());
                    yd.put("startTime", "");
                    yd.put("endTime", "");
                    ydList.add(yd);
                }
            }

            return R.ok().put("flag", false).put("data", ydList);
        }

    }


}
