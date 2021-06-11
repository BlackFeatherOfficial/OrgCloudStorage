package com.atblack_feather_official.netdesk.service;

import com.atblack_feather_official.netdesk.bean.Msg;
import com.atblack_feather_official.netdesk.mapper.MsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgService {
    @Autowired
    MsgMapper msgMapper;


    public Msg getMsgByID(String ID){
       return msgMapper.getMsgByID(ID);
    }

    public List<Msg> MsgBySender(Long sender){
        return msgMapper.sendMsg(sender);
    }
    public  List<Msg> MsgByReceiver(Long receiver){
        return msgMapper.receiverMsg(receiver);
    }

    public void  addOrgInvent(Long sender,Long receiver,String msgID,Integer layer,Integer order){
        String departmentName = msgMapper.queryDepartmentName(sender, layer, order);
        if (layer==0 && order==0)
            departmentName ="董事会";
        msgMapper.addMsg(sender,receiver,"<a href='http://localhost:8080/invent?ID="+msgID+"' target='_blank'>收到了企业邀请，点击本链接进行确认</a>",msgID,
                "企业邀请",departmentName,layer,order);
    }

    public void  addOrgApplication(Long sender,Long receiver,String msgID){
        msgMapper.addMsg(sender,receiver,"<a href='http://localhost:8080/application?ID="+msgID+"' target='_blank'>收到了申请，点击本链接进行确认</a>",msgID,
                "企业申请","申请",0,0);
    }



    public void readMsg(String msgID){
        msgMapper.readMsg(msgID);
    }

    public void acceptMsg(String msgID){
        msgMapper.setMsgConfirm(msgID,1);
    }

    public void rejectMsg(String msgID){
        msgMapper.setMsgConfirm(msgID,2);
    }

}
