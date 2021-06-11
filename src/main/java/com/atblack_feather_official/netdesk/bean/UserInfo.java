package com.atblack_feather_official.netdesk.bean;

import lombok.Data;

@Data
public class UserInfo {
    private Integer filesCount;
    private Integer textsCount;
    private Integer musicsCount;
    private Integer imagesCount;
    private Integer videosCount;
    private Integer unknownCount;

    private Integer filesSize;
    private Integer textsSize;
    private Integer musicsSize;
    private Integer imagesSize;
    private Integer videosSize;
    private Integer unknownSize;

    private Double percent;

    private Integer storageSpace;
}
