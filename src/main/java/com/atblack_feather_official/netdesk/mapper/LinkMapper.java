package com.atblack_feather_official.netdesk.mapper;

import com.atblack_feather_official.netdesk.bean.File;
import com.atblack_feather_official.netdesk.bean.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LinkMapper {

    @Select("INSERT INTO link (DeadTime,ID,`password`,creator,remark,deleteMark) VALUES (${deadTime},'${ID}','${password}','${creator}','${reMark}',0)")
    void addLink(String ID,String password,Long creator,String deadTime,String reMark);

    @Select("INSERT INTO linkfile (belongID,remark,fileID,layer,`order`,belongLayer,belongOrder,fileType,fileOrNot,sizeOfKB) VALUES ('${belongID}','${remark}','${fileID}',${layer},${order},${belongLayer},${belongOrder},'${fileType}',${fileOrNot},${sizeOfKB})")
    void addFileToLink(String belongID,String remark,String fileID,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String fileType,Integer fileOrNot,Integer sizeOfKB);

    @Select("INSERT INTO orgfile_${OrgID} (creator,belongOrgLayer,belongOrgOrder,remark,fileID,layer,`order`,belongLayer,belongOrder,fileType,fileOrNot,sizeOfKB,deleteMark) VALUES (${belongID},'${OrgLayer}','${OrgOrder}','${remark}','${fileID}',${layer},${order},${belongLayer},${belongOrder},'${fileType}',${fileOrNot},${sizeOfKB},0)")
    void addFileToOrg(Long OrgID,Long belongID,String remark,String fileID,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String fileType,Integer fileOrNot,Integer sizeOfKB,Integer OrgLayer,Integer OrgOrder);

    @Select("select MAX(`order`) from linkfile where `layer` = ${layer} and `belongID`='${belongID}'")
    Integer queryMaxOrderInLink(String belongID,Integer layer);

    @Select("select MAX(`order`) from orgfile_${OrgID} where `layer` = ${layer} and `belongOrgLayer`='${OrgLayer}' and `belongOrgOrder`='${OrgOrder}'")
    Integer queryMaxOrderInOrg(Long OrgID,Integer layer,Integer OrgLayer,Integer OrgOrder);

    @Select("SELECT * FROM link WHERE deleteMark = 0 AND ID= '${ID}' AND(DeadTime IS NULL OR DeadTime > NOW())")
    Link checkLinkExist(String ID);

    @Select("SELECT password FROM link WHERE ID= '${ID}'")
    String checkLinkPassword(String ID);

    @Select("select * from linkfile where belongID = '${belongID}' and belongLayer = ${belongLayer} and belongOrder =  ${belongOrder}")
    List<File> queryAllLinkFileByLocation(String belongID, Integer belongLayer, Integer belongOrder);

    @Select("select * from link where creator = '${creator}' and deleteMark = 0")
    List<Link> linkListByUser(Long creator);
    @Select("update link set deleteMark = 1 where ID = '${linkID}'")
    void deleteLink(String linkID);
}
