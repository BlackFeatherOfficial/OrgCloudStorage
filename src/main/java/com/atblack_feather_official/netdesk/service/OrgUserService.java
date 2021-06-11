package com.atblack_feather_official.netdesk.service;

import com.atblack_feather_official.netdesk.bean.OrgUser;
import com.atblack_feather_official.netdesk.mapper.OrgUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class OrgUserService {
    @Autowired
    OrgUserMapper orgUserMapper;

    public void delOrgUser( Long OrgID, Integer layer, Integer order){
        orgUserMapper.delOrgUser(OrgID,layer,order);
    }
    public List<OrgUser> getAllUserByOrgID(@RequestParam Long OrgID,@RequestParam Integer belongLayer,@RequestParam Integer belongOrder){
        return orgUserMapper.getAllUserByOrgID(OrgID,belongLayer,belongOrder);
    }

    public Integer queryMaxOrder(Long OrgID,Integer layer){
        if (orgUserMapper.queryOrderMax(OrgID,layer) != null)
            return orgUserMapper.queryOrderMax(OrgID,layer);
        else
            return 0;
    }

    public void addGroupNote(Long OrgID,String remark,Integer belongLayer,Integer belongOrder){
        orgUserMapper.addUserToOrg(OrgID,"分组", 0L,remark,0,belongLayer+1,queryMaxOrder(OrgID,belongLayer+1)+1,belongLayer,belongOrder,0);
    }
    public void addUserNote(Long OrgID,String remark,Integer belongLayer,Integer belongOrder,Long userID){
        orgUserMapper.addUserToOrg(OrgID,"组员",userID,remark,0,belongLayer+1,queryMaxOrder(OrgID,belongLayer+1)+1,belongLayer,belongOrder,1);
    }
}
