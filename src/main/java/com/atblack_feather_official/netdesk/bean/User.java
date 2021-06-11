package com.atblack_feather_official.netdesk.bean;

import lombok.Data;

@Data
public class User {
    private String userName;
    private String netName;
    private String password;
    private Long ID;
    private Integer userClass;
    private String VIPClass;
    private String VIPEndTime;
    private String Email;
    private Long belonging;
}
