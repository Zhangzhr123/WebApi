package com.authine.cloudpivot.mybatis.service;


import com.authine.cloudpivot.mybatis.entity.PageData;
import com.authine.cloudpivot.mybatis.mapper.SearchMeetingRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchMeetingRoomService {

    @Autowired
    SearchMeetingRoomMapper searchMeetingRoomMapper;

    public List<PageData> isHadApplied(String roomName, String ksrq, String jsrq) {
        return searchMeetingRoomMapper.searchMeetingRoom(roomName,ksrq,jsrq);
    }

    public List<PageData> getOwnApply(String fqr,String syr) {
        return searchMeetingRoomMapper.getOwnApply(fqr,syr);
    }

    public PageData getRoomName(String hysmc) {
        return searchMeetingRoomMapper.gerRoomName(hysmc);
    }

    public String gerUserName(String id) {
        return searchMeetingRoomMapper.gerUserName(id);
    }

    public void meetingSign(String workflowInstanceId) {
        searchMeetingRoomMapper.meetingSign(workflowInstanceId);
    }


    public PageData getHYSXX(String roomName1) {
        return searchMeetingRoomMapper.getHYSXX(roomName1);
    }

    public void cansell(String workflowInstanceId) {
     searchMeetingRoomMapper.cancell(workflowInstanceId);
    }

    public PageData getHYSLCBySequenceNo(String workflowInstanceId) {
        return searchMeetingRoomMapper.getHYSLCBySequenceNo(workflowInstanceId);
    }

    public List<PageData> getMeetings(String hysmc, String workflowInstanceId, String dqhyjssj) {
        return searchMeetingRoomMapper.getMeetings(hysmc,workflowInstanceId,dqhyjssj);
    }

    public void updateMeeting(String min, String workflowInstanceId) {
        searchMeetingRoomMapper.updateMeeting(min,workflowInstanceId);
    }

    public void overEarly(String workflowInstanceId) {
        searchMeetingRoomMapper.overEarly(workflowInstanceId);
    }

    public PageData getConferee(String workflowInstanceId) {

        return searchMeetingRoomMapper.getConferee(workflowInstanceId);
    }

    public PageData getUserID(String chryId) {
        return searchMeetingRoomMapper.getUserID(chryId);
    }

    public List<PageData> getAllMeetingApply(String dateKssj, String dateJssj) {
        return searchMeetingRoomMapper.getAllMeetingApply(dateKssj,dateJssj);
    }

    public List<PageData> getAllHYS() {
        return searchMeetingRoomMapper.getAllHYS();
    }
}
