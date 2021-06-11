package com.atblack_feather_official.netdesk.mapper;

import com.atblack_feather_official.netdesk.bean.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MsgMapper {

    @Select("select * from msg where receiver = ${receiver} and readMark=  ${readMark}")
    List<Msg> getMsgForReceiverByUserID(Long receiver, Integer readMark);

    @Select("select * from msg where sender = ${sender} and readMark=  ${readMark}")
    List<Msg> getMsgForSenderByUserID(Long sender,Integer readMark);

    @Select("select * from msg where receiver = ${receiver}")
    List<Msg> receiverMsg(Long receiver);

    @Select("select * from msg where sender = ${sender}")
    List<Msg> sendMsg(Long sender);

    @Select("select remark from org_${OrgID} where layer = ${layer} and `order` = ${order}")
    String queryDepartmentName(Long OrgID,Integer layer,Integer order);

    @Select("select * from msg where msgID = '${ID}'")
    Msg getMsgByID(String ID);

    @Select("INSERT INTO  msg (msg,sender,receiver,readMark,sendTime,confirm,type,msgID,OrgLayer,OrgOrder,OrgPosition) VALUES (\"${msg}\",${sender},${receiver},0,NOW(),0,'${type}','${msgID}',${OrgLayer},${OrgOrder},'${OrgPosition}')")
    void addMsg(Long sender,Long receiver,String msg,String msgID,String type,String OrgPosition,Integer OrgLayer,Integer OrgOrder);

    @Select("UPDATE `msg`  SET readTime = NOW() , readMark =1 WHERE msgID= '${msgID}'")
    void readMsg(String msgID);

    @Select("UPDATE `msg` SET confirm = ${newConfirm} WHERE msgID= '${msgID}'")
    void setMsgConfirm(String msgID,Integer newConfirm);
}
