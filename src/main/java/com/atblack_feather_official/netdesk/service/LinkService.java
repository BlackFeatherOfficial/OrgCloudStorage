package com.atblack_feather_official.netdesk.service;


import com.atblack_feather_official.netdesk.bean.File;
import com.atblack_feather_official.netdesk.bean.Link;
import com.atblack_feather_official.netdesk.mapper.FileMapper;
import com.atblack_feather_official.netdesk.mapper.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {
    @Autowired
    LinkMapper linkMapper;
    @Autowired
    FileMapper fileMapper;


    public void addLink(String ID,String password,Long creator,String deadTime,String reMark){
        linkMapper.addLink(ID,password,creator,deadTime,reMark);
    }

    public void addFileToLink(String belongID,String remark,String fileID,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String fileType,Integer fileOrNot,Integer sizeOfKB){
        linkMapper.addFileToLink(belongID,remark,fileID,layer,order,belongLayer,belongOrder,fileType,fileOrNot,sizeOfKB);
    }
    public void addFileToOrg(Long OrgID,Long belongID,String remark,String fileID,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String fileType,Integer fileOrNot,Integer sizeOfKB,Integer OrgLayer,Integer OrgOrder){
        linkMapper.addFileToOrg(OrgID,belongID,remark,fileID,layer,order,belongLayer,belongOrder,fileType,fileOrNot,sizeOfKB,OrgLayer,OrgOrder);
    }
    public List<Link> linkListByUser(Long userID){
       return linkMapper.linkListByUser(userID);
    }

    public void deleteLink(String linkID){
        linkMapper.deleteLink(linkID);
    };

    public Integer queryMaxOrderInLink(String belongID,Integer layer){
       if (linkMapper.queryMaxOrderInLink(belongID,layer)==null)
           return 0;
       else
           return linkMapper.queryMaxOrderInLink(belongID,layer);
    }

    public Integer queryMaxOrderInOrg(Long OrgID,Integer layer,Integer OrgLayer,Integer OrgOrder){
        if (linkMapper.queryMaxOrderInOrg(OrgID,layer,OrgLayer,OrgOrder)==null)
            return 0;
        else
            return linkMapper.queryMaxOrderInOrg(OrgID,layer,OrgLayer,OrgOrder);
    }

    public Link checkLinkExist(String ID){
        return linkMapper.checkLinkExist(ID);
    }

    public String checkLinkPassword(String ID){
        return linkMapper.checkLinkPassword(ID);
    }

    public List<File> queryAllLinkFileByLocation (String belongID, Integer belongLayer, Integer belongOrder){
        return linkMapper.queryAllLinkFileByLocation(belongID,belongLayer,belongOrder);
    }


}
