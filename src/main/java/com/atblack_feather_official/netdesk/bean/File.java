package com.atblack_feather_official.netdesk.bean;

import lombok.Data;

@Data
public class File {
    private String FileID;
    private String remark;
    private String fileType;
    private Integer layer;
    private Integer order;
    private Integer belongLayer;
    private Integer belongOrder;
    private Integer fileOrNot;
    private Integer sizeOfKB;
    private Integer belongOrgLayer;
    private Integer belongOrgOrder;
    private Integer deleteMark;
    private String belongID;
    private Long creator;
}
