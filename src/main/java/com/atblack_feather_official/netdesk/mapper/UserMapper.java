package com.atblack_feather_official.netdesk.mapper;

import com.atblack_feather_official.netdesk.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select(" select * from user where ID = #{id}")
    User getUserById(Long id);

    @Select(" select ID from user where user_name = #{userName}")
    Integer getUserIDByName(String userName);

    @Select(" select * from user where user_name = #{userName}")
    User getUserByUserName(String userName);

    @Select("INSERT INTO `user` (user_name,`password`,user_class,ban_mark,VIP_class) VALUES (#{userName},#{password},0,0,0) ")
    User addUserByFastRegister(String userName,String password);

    @Select("CREATE TABLE `netdesk`.`${userName}_friend` (`friendID` int(11) NOT NULL,`friendremark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,`belonging` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact")
    Void establishUserFriendTable(String userName);

    @Select("CREATE TABLE `netdesk`.`${userName}_file` (`fileType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,`layer` int(255) NULL DEFAULT NULL,`order` int(255) NULL DEFAULT NULL, `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL, `fileID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, `fileOrNot` int(255) NULL DEFAULT NULL,`belongLayer` int(255) NULL DEFAULT NULL,`belongOrder` int(255) NULL DEFAULT NULL,`sizeOfKB` int(255) NULL DEFAULT NULL) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact")
    Void establishUserFileTable(String userName);

    @Select(" CREATE TABLE `netdesk`.`org_${userID}` (`userType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,`layer` int(255) NULL DEFAULT NULL,`order` int(255) NULL DEFAULT NULL, `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL, `userID` int(255) NOT NULL, `userOrNot` int(255) NULL DEFAULT NULL,`belongLayer` int(255) NULL DEFAULT NULL,`belongOrder` int(255) NULL DEFAULT NULL,`manager` int(255) NULL DEFAULT NULL) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact")
    Void establishUserOrg(Long userID);

    @Select(" CREATE TABLE `netdesk`.`orgFile_${userID}` (`fileType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,`layer` int(255) NULL DEFAULT NULL,`order` int(255) NULL DEFAULT NULL, `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL, `fileID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, `fileOrNot` int(255) NULL DEFAULT NULL,`belongLayer` int(255) NULL DEFAULT NULL,`belongOrder` int(255) NULL DEFAULT NULL,`belongOrgLayer` int(255) NULL DEFAULT NULL,`belongOrgOrder` int(255) NULL DEFAULT NULL,`sizeOfKB` int(255) NULL DEFAULT NULL) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact")
    Void establishUserOrgFiles(Long userID);

    @Select(" select user_class from user where user_name = #{userName}")
    Integer queryUserClass(String userName);

    @Select("UPDATE `user` SET user_class = ${newClass}  WHERE ID = ${userID} ")
    void userClassChange(Long userID,Integer newClass);

    @Select("UPDATE `user` SET belonging = ${newBelonging}  WHERE ID = ${userID} ")
    void userBelongingChange(Long userID,Long newBelonging);

    @Select("INSERT INTO `org_${OrgID}` (userType,`userID`,layer,`order`,belongLayer,belongOrder,remark,userOrNot,manager) VALUES ('${userType}',${userID},${layer},${order},${belongLayer},${belongOrder},'${reMark}',${userOrNot},${manager})")
    void addUserToOrg(Long OrgID,Long userID,Integer layer,Integer order,Integer belongLayer,
                      Integer belongOrder,String reMark,Integer userOrNot,String userType,Integer manager);

}
