package com.authine.cloudpivot.mybatis.mapper;

import com.authine.cloudpivot.mybatis.entity.PageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SearchMeetingRoomMapper {

    List<PageData> getOwnApply(@Param("fqr")String fqr, @Param("syr")String syr);

    PageData gerRoomName(String hysmc);

    String gerUserName(String id);

    List<PageData> searchMeetingRoom(@Param("roomName")String roomName, @Param("ksrq")String ksrq, @Param("jsrq")String jsrq);

    void meetingSign(String workflowInstanceId);

    PageData getHYSXX(String roomName1);

    void cancell(String workflowInstanceId);

    PageData getHYSLCBySequenceNo(String workflowInstanceId);

    List<PageData> getMeetings(@Param("hysmc")String hysmc, @Param("workflowInstanceId")String workflowInstanceId, @Param("dqhyjssj")String dqhyjssj);

    List<PageData> getAllHYS();

    void updateMeeting(@Param("min")String min, @Param("workflowInstanceId")String workflowInstanceId);

    void overEarly(String workflowInstanceId);

    PageData getConferee(String workflowInstanceId);

    PageData getUserID(String chryId);

    List<PageData> getAllMeetingApply(@Param("dateKssj")String dateKssj, @Param("dateJssj")String dateJssj);
}
