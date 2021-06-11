package com.atblack_feather_official.netdesk.service;

import com.atblack_feather_official.netdesk.bean.User;
import com.atblack_feather_official.netdesk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserById(Long id){
       return userMapper.getUserById(id);
    }

    public User getUserByUserName(String userName){
        return userMapper.getUserByUserName(userName);
    }

    public Integer getUserIDByName(String userName){
        return userMapper.getUserIDByName(userName);
    }

    public User addUserByFastRegister(String userName,String password){
        userMapper.addUserByFastRegister(userName,password);
        return userMapper.getUserByUserName(userName);
    }

    public Boolean CheckUserExistByUserName(String userName){
        if (userMapper.getUserByUserName(userName) != null)
            return true;
        else
            return false;
    }




    public Integer queryUserClass(String userName){
        return userMapper.queryUserClass(userName);
    }

    public String getUserPasswordByUserName(String userName){
        if(userMapper.getUserByUserName(userName)!=null){
            return userMapper.getUserByUserName(userName).getPassword();
        }
        else
            return null;
    }

    public Boolean establishUserTableByUserName(String userName){
        userMapper.establishUserFriendTable(userName);
        userMapper.establishUserFileTable(userName);
        return true;
    }

    public Boolean establishUserOrgTableByUserID(Long userID){
        userMapper.establishUserOrg(userID);
        userMapper.establishUserOrgFiles(userID);
        userMapper.userClassChange(userID,2);
        userMapper.userBelongingChange(userID,userID);
        userMapper.addUserToOrg(userID,userID,1,1,0,0,"",1,"董事长",1);
        return true;
    }

    public boolean addUserToOrg(Long userID,Long OrgID){
        userMapper.userClassChange(userID,1);
        userMapper.userBelongingChange(userID,OrgID);
        return true;
    }
}
