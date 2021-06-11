package com.atblack_feather_official.netdesk.mapper;

import com.atblack_feather_official.netdesk.bean.OrgUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface OrgUserMapper {

    @Select("select * from org_${OrgID} where deleteMark =0 AND belongLayer = ${belongLayer} and belongOrder = ${belongOrder}")
    List<OrgUser> getAllUserByOrgID(Long OrgID,Integer belongLayer,Integer belongOrder);

    @Select("INSERT INTO org_${OrgID} (userType,userID,remark,manager,layer,`order`,belongLayer,belongOrder,userOrNot,deleteMark)VALUES('${userType}',${userID},'${remark}',${manager},${layer},${order},${belongLayer},${belongOrder},${userOrNot},0)")
    void addUserToOrg(Long OrgID,String userType,Long userID,String remark,Integer manager,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,Integer userOrNot);

    @Select("select MAX(`order`) from org_${OrgID} where `layer` = ${layer}")
    Integer queryOrderMax(Long OrgID,Integer layer);

    @Select("update org_${OrgID} set deleteMark = 1 where layer = ${layer} and `order` = ${order}")
    void delOrgUser(Long OrgID,Integer layer,Integer order);
}
