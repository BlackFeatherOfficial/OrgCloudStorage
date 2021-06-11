package com.atblack_feather_official.netdesk.bean;

import lombok.Data;

@Data
public class OrgUser {
    String userType;
    Integer layer;
    Integer order;
    Integer belongLayer;
    Integer belongOrder;
    Integer userID;
    String remark;
    String userOrNot;
    Integer manager;
}
