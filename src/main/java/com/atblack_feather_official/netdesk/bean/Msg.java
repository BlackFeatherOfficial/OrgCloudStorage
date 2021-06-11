package com.atblack_feather_official.netdesk.bean;

import lombok.Data;

@Data
public class Msg {
    String msg;
    Long sender;
    Long receiver;
    String readMark;
    String sendTime;
    String readTime;
    String confirm;
    String type;
    String msgID;
    Integer OrgLayer;
    Integer OrgOrder;
    String OrgPosition;

}
