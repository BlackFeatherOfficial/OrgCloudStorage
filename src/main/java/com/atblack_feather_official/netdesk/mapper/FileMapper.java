package com.atblack_feather_official.netdesk.mapper;

import com.atblack_feather_official.netdesk.bean.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("select * from ${userName}_file WHERE deleteMark=0 AND( fileType = '.vsd' OR fileType = '.vsdx' OR  fileType = '.txt' OR fileType = '.doc' OR fileType = '.docx' OR fileType = '.rtf' OR fileType = '.xls' OR fileType = '.xlsx' OR fileType = '.sql' OR fileType = '.ppt' OR fileType = '.pptx' OR fileType = '.lrc') ")
    List<File> retrievalAllFilesByTextType(String userName);

    @Select("select * from ${userName}_file where deleteMark=0 AND( fileType = '.ico' OR  fileType = '.png' OR fileType = '.jpeg' OR fileType = '.jpg' OR fileType = '.tiff' OR fileType = '.gif' OR fileType = '.psd' OR fileType = '.ps' OR fileType = '.raw' OR fileType = '.eps' OR fileType = '.svg' OR fileType = '.bmp' OR fileType = '.pdf') ")
    List<File> retrievalAllFilesByImageType(String userName);

    @Select("select * from ${userName}_file where deleteMark=0 AND( fileType = '.avi' OR fileType = '.wmv' OR fileType = '.mpeg' OR fileType = '.mpg' OR fileType = '.mov' OR fileType = '.swf' OR fileType = '.flv' OR fileType = '.mp4' OR fileType = '.rm' OR fileType = '.ram' OR fileType = '.asf' OR fileType = '.rmvb' OR fileType = '.3gp' OR fileType = '.mpg' OR fileType = '.dat' OR fileType = '.ts' OR fileType = '.lavf' OR fileType = '.dirac' OR fileType = '.3g2' OR fileType = '.m2ts' OR fileType = '.webm' OR fileType = '.m2t' OR fileType = '.fli' OR fileType = '.flc')")
    List<File> retrievalAllFilesByVideoType(String userName);

    @Select("select * from ${userName}_file where deleteMark=0 AND( fileType = '.mp3' OR fileType = '.cda' OR fileType = '.wav' OR fileType = '.mp3p' OR fileType = '.wma' OR fileType = '.ra' OR fileType = '.midi' OR fileType = '.ogg' OR fileType = '.ape' OR fileType = '.flac' OR fileType = '.aac') ")
    List<File> retrievalAllFilesByMusicType(String userName);

    @Select("select * from ${userName}_file where deleteMark=1")
    List<File> retrievalAllFilesByDeleteType(String userName);

    @Select("select * from ${userName}_file where deleteMark=0 AND( fileType != '.ico' AND fileType != '.vsd' AND fileType != '.vsdx' AND  fileType != '0' AND fileType = '.ico' AND  fileType != '.txt' AND fileType != '.doc' AND fileType != '.docx' AND fileType != '.rtf' AND fileType != '.xls' AND fileType != '.xlsx' AND fileType != '.sql' AND fileType != '.ppt' AND fileType != '.pptx' AND fileType != '.lrc' AND fileType != '.png' AND fileType != '.jpeg' AND fileType != '.jpg' AND fileType != '.tiff' AND fileType != '.gif' AND fileType != '.psd' AND fileType != '.ps' AND fileType != '.raw' AND fileType != '.eps' AND fileType != '.svg' AND fileType != '.bmp' AND fileType != '.pdf' AND fileType != '.avi' AND fileType != '.wmv' AND fileType != '.mpeg' AND fileType != '.mpg' AND fileType != '.mov' AND fileType != '.swf' AND fileType != '.flv' AND fileType != '.mp4' AND fileType != '.rm' AND fileType != '.ram' AND fileType != '.asf' AND fileType != '.rmvb' AND fileType != '.3gp' AND fileType != '.mpg' AND fileType != '.dat' AND fileType != '.ts' AND fileType != '.lavf' AND fileType != '.dirac' AND fileType != '.3g2' AND fileType != '.m2ts' AND fileType != '.webm' AND fileType != '.m2t' AND fileType != '.fli' AND fileType != '.flc' AND  fileType != '.mp3' AND fileType != '.cda' AND fileType != '.wav' AND fileType != '.mp3p' AND fileType != '.wma' AND fileType != '.ra' AND fileType != '.midi' AND fileType != '.ogg' AND fileType != '.ape' AND fileType != '.flac' AND fileType != '.aac'  ) ")
    List<File> retrievalAllFilesByUnknownType(String userName);


    @Select("select * from ${userName}_file WHERE deleteMark=0 AND remark LIKE '%${remark}%' AND ( fileType = '.vsd' OR fileType = '.vsdx' OR  fileType = '.txt' OR fileType = '.doc' OR fileType = '.docx' OR fileType = '.rtf' OR fileType = '.xls' OR fileType = '.xlsx' OR fileType = '.sql' OR fileType = '.ppt' OR fileType = '.pptx' OR fileType = '.lrc')")
    List<File> retrievalAllFilesByTextTypeAndRemark(String userName,String remark);

    @Select("select * from ${userName}_file where deleteMark=0 AND remark LIKE '%${remark}%' AND (  fileType = '.ico' OR  fileType = '.png' OR fileType = '.jpeg' OR fileType = '.jpg' OR fileType = '.tiff' OR fileType = '.gif' OR fileType = '.psd' OR fileType = '.ps' OR fileType = '.raw' OR fileType = '.eps' OR fileType = '.svg' OR fileType = '.bmp' OR fileType = '.pdf') ")
    List<File> retrievalAllFilesByImageTypeAndRemark(String userName,String remark);

    @Select("select * from ${userName}_file where deleteMark=0 AND remark LIKE '%${remark}%' AND (  fileType = '.avi' OR fileType = '.wmv' OR fileType = '.mpeg' OR fileType = '.mpg' OR fileType = '.mov' OR fileType = '.swf' OR fileType = '.flv' OR fileType = '.mp4' OR fileType = '.rm' OR fileType = '.ram' OR fileType = '.asf' OR fileType = '.rmvb' OR fileType = '.3gp' OR fileType = '.mpg' OR fileType = '.dat' OR fileType = '.ts' OR fileType = '.lavf' OR fileType = '.dirac' OR fileType = '.3g2' OR fileType = '.m2ts' OR fileType = '.webm' OR fileType = '.m2t' OR fileType = '.fli' OR fileType = '.flc')")
    List<File> retrievalAllFilesByVideoTypeAndRemark(String userName,String remark);

    @Select("select * from ${userName}_file where deleteMark=0 AND remark LIKE '%${remark}%' AND ( fileType = '.mp3' OR fileType = '.cda' OR fileType = '.wav' OR fileType = '.mp3p' OR fileType = '.wma' OR fileType = '.ra' OR fileType = '.midi' OR fileType = '.ogg' OR fileType = '.ape' OR fileType = '.flac' OR fileType = '.aac' )")
    List<File> retrievalAllFilesByMusicTypeAndRemark(String userName,String remark);

    @Select("select * from ${userName}_file where deleteMark=0 AND remark LIKE '%${remark}%' AND ( fileType != '.ico' AND fileType != '.vsd' AND fileType != '.vsdx' AND  fileType != '0' AND fileType = '.ico' AND  fileType != '.txt' AND fileType != '.doc' AND fileType != '.docx' AND fileType != '.rtf' AND fileType != '.xls' AND fileType != '.xlsx' AND fileType != '.sql' AND fileType != '.ppt' AND fileType != '.pptx' AND fileType != '.lrc' AND fileType != '.png' AND fileType != '.jpeg' AND fileType != '.jpg' AND fileType != '.tiff' AND fileType != '.gif' AND fileType != '.psd' AND fileType != '.ps' AND fileType != '.raw' AND fileType != '.eps' AND fileType != '.svg' AND fileType != '.bmp' AND fileType != '.pdf' AND fileType != '.avi' AND fileType != '.wmv' AND fileType != '.mpeg' AND fileType != '.mpg' AND fileType != '.mov' AND fileType != '.swf' AND fileType != '.flv' AND fileType != '.mp4' AND fileType != '.rm' AND fileType != '.ram' AND fileType != '.asf' AND fileType != '.rmvb' AND fileType != '.3gp' AND fileType != '.mpg' AND fileType != '.dat' AND fileType != '.ts' AND fileType != '.lavf' AND fileType != '.dirac' AND fileType != '.3g2' AND fileType != '.m2ts' AND fileType != '.webm' AND fileType != '.m2t' AND fileType != '.fli' AND fileType != '.flc' AND  fileType != '.mp3' AND fileType != '.cda' AND fileType != '.wav' AND fileType != '.mp3p' AND fileType != '.wma' AND fileType != '.ra' AND fileType != '.midi' AND fileType != '.ogg' AND fileType != '.ape' AND fileType != '.flac' AND fileType != '.aac')   ")
    List<File> retrievalAllFilesByUnknownTypeAndRemark(String userName,String remark);

    @Select("select remark from ${userName}_file where deleteMark=0 AND fileID = '${MusicID}'")
    String queryMusicName(String MusicID,String userName);

    @Select("select * from ${userName}_file where deleteMark=0 AND fileType = '${fileType}' and remark LIKE '%${remark}%'")
    List<File> retrievalAllFilesByFileTypeAndRemark(String userName,String fileType,String remark);



    @Select("select * from ${userName}_file where deleteMark=0 AND `belongLayer` = ${belongLayer} and `belongOrder` = ${belongOrder}")
    List<File> retrievalAllFilesByLocation(Integer belongLayer,Integer belongOrder,String userName);

    @Select("select * from ${userName}_file where deleteMark=0 AND `belongLayer` = ${belongLayer} and `belongOrder` = ${belongOrder} and remark LIKE '%${remark}%'")
    List<File> retrievalAllFilesByLocationAndRemark(Integer belongLayer,Integer belongOrder,String userName,String remark);

    @Select("select * from orgfile_${OrgID} where deleteMark=0 AND `belongLayer` = ${belongLayer} and `belongOrder` = ${belongOrder} and `belongOrgLayer` = ${OrgLayer} and `belongOrgOrder` = ${OrgOrder}")
    List<File> retrievalAllOrgFilesByLocation(Integer OrgID,Integer OrgLayer,Integer OrgOrder,Integer belongLayer,Integer belongOrder);

    @Select("select * from orgfile_${OrgID} where deleteMark=0 AND `belongLayer` = ${belongLayer} and `belongOrder` = ${belongOrder} and `belongOrgLayer` = ${OrgLayer} and `belongOrgOrder` = ${OrgOrder} and remark LIKE '%${remark}%'")
    List<File> retrievalAllOrgFilesByLocationAndRemark(Integer OrgID,Integer OrgLayer,Integer OrgOrder,Integer belongLayer,Integer belongOrder,String remark);

    @Select("select COUNT(sizeOfKB) from ${userName}_file where deleteMark=0 ")
    Integer queryUserAllFilesCount(String userName);
    @Select("select COUNT(sizeOfKB) from ${userName}_file WHERE deleteMark=0 AND( fileType = '.vsd' OR fileType = '.vsdx' OR  fileType = '.txt' OR fileType = '.doc' OR fileType = '.docx' OR fileType = '.rtf' OR fileType = '.xls' OR fileType = '.xlsx' OR fileType = '.sql' OR fileType = '.ppt' OR fileType = '.pptx' OR fileType = '.lrc') ")
    Integer queryUserTextFilesCount(String userName);
    @Select("select COUNT(sizeOfKB) from ${userName}_file where deleteMark=0 AND( fileType = '.mp3' OR fileType = '.cda' OR fileType = '.wav' OR fileType = '.mp3p' OR fileType = '.wma' OR fileType = '.ra' OR fileType = '.midi' OR fileType = '.ogg' OR fileType = '.ape' OR fileType = '.flac' OR fileType = '.aac') ")
    Integer queryUserMusicFilesCount(String userName);
    @Select("select COUNT(sizeOfKB) from ${userName}_file where deleteMark=0 AND( fileType = '.ico' OR fileType = '.png' OR fileType = '.jpeg' OR fileType = '.jpg' OR fileType = '.tiff' OR fileType = '.gif' OR fileType = '.psd' OR fileType = '.ps' OR fileType = '.raw' OR fileType = '.eps' OR fileType = '.svg' OR fileType = '.bmp' OR fileType = '.pdf') ")
    Integer queryUserImageFilesCount(String userName);
    @Select("select COUNT(sizeOfKB) from ${userName}_file where deleteMark=0 AND( fileType = '.avi' OR fileType = '.wmv' OR fileType = '.mpeg' OR fileType = '.mpg' OR fileType = '.mov' OR fileType = '.swf' OR fileType = '.flv' OR fileType = '.mp4' OR fileType = '.rm' OR fileType = '.ram' OR fileType = '.asf' OR fileType = '.rmvb' OR fileType = '.3gp' OR fileType = '.mpg' OR fileType = '.dat' OR fileType = '.ts' OR fileType = '.lavf' OR fileType = '.dirac' OR fileType = '.3g2' OR fileType = '.m2ts' OR fileType = '.webm' OR fileType = '.m2t' OR fileType = '.fli' OR fileType = '.flc')")
    Integer queryUserVideoFilesCount(String userName);


    @Select("select SUM(sizeOfKB) from ${userName}_file where deleteMark=0 ")
    Integer queryUserAllFilesSize(String userName);
    @Select("select SUM(sizeOfKB) from ${userName}_file WHERE deleteMark=0 AND( fileType = '.vsd' OR fileType = '.vsdx' OR fileType = '.txt' OR fileType = '.doc' OR fileType = '.docx' OR fileType = '.rtf' OR fileType = '.xls' OR fileType = '.xlsx' OR fileType = '.sql' OR fileType = '.ppt' OR fileType = '.pptx' OR fileType = '.lrc') ")
    Integer queryUserTextFilesSize(String userName);
    @Select("select SUM(sizeOfKB) from ${userName}_file where deleteMark=0 AND( fileType = '.mp3' OR fileType = '.cda' OR fileType = '.wav' OR fileType = '.mp3p' OR fileType = '.wma' OR fileType = '.ra' OR fileType = '.midi' OR fileType = '.ogg' OR fileType = '.ape' OR fileType = '.flac' OR fileType = '.aac') ")
    Integer queryUserMusicFilesSize(String userName);
    @Select("select SUM(sizeOfKB) from ${userName}_file where deleteMark=0 AND( fileType = '.ico' OR fileType = '.png' OR  fileType = '.jpeg' OR fileType = '.jpg' OR fileType = '.tiff' OR fileType = '.gif' OR fileType = '.psd' OR fileType = '.ps' OR fileType = '.raw' OR fileType = '.eps' OR fileType = '.svg' OR fileType = '.bmp' OR fileType = '.pdf') ")
    Integer queryUserImageFilesSize(String userName);
    @Select("select SUM(sizeOfKB) from ${userName}_file where deleteMark=0 AND( fileType = '.avi' OR fileType = '.wmv' OR fileType = '.mpeg' OR fileType = '.mpg' OR fileType = '.mov' OR fileType = '.swf' OR fileType = '.flv' OR fileType = '.mp4' OR fileType = '.rm' OR fileType = '.ram' OR fileType = '.asf' OR fileType = '.rmvb' OR fileType = '.3gp' OR fileType = '.mpg' OR fileType = '.dat' OR fileType = '.ts' OR fileType = '.lavf' OR fileType = '.dirac' OR fileType = '.3g2' OR fileType = '.m2ts' OR fileType = '.webm' OR fileType = '.m2t' OR fileType = '.fli' OR fileType = '.flc')")
    Integer queryUserVideoFilesSize(String userName);

    @Select("select VIP_class from user where user_name = '${userName}'")
    Integer queryStorageSpace(String userName);


    @Select("select * from linkfile where `belongLayer` = ${belongLayer} and `belongOrder` = ${belongOrder} and belongID = '${ID}'")
    List<File> retrievalAllFilesByLink(Integer belongLayer,Integer belongOrder,String ID);

    @Select("select * from ${userName}_file where `layer` = ${layer} and `order` = ${order}")
    List<File> checkFileDelete(String userName,Integer layer,Integer order);

    @Select("select MAX(`order`) from ${userName}_file where `layer` = ${layer}")
    Integer queryOrderMax(Integer layer,String userName);

    @Select("INSERT INTO `file` (fileID,src,count) VALUES ('${fileID}','${src}',1)")
    void CreatFileInfo(String fileID,String src);

    @Select("insert into ${userName}_file (fileID,remark,fileType,layer,`order`,fileOrNot,belongLayer,belongOrder,sizeOfKB,deleteMark) VALUES ('0','${remark}','0',${layer},${order},0,${belongLayer},${belongOrder},0,0)")
    void addFolder(String remark,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String userName);

    @Select("insert into ${userName}_file (fileID,remark,fileType,layer,`order`,fileOrNot,belongLayer,belongOrder,sizeOfKB,deleteMark) VALUES ('${fileID}','${remark}','${fileType}',${layer},${order},${fileOrNot},${belongLayer},${belongOrder},${sizeOfKB},0)")
    void addFile(String fileID,String remark,String fileType,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String userName,Long sizeOfKB,Integer fileOrNot);

    @Select("SELECT * FROM file WHERE fileID ='${fileID}'")
    File checkFile(String fileID);

    @Select("UPDATE file SET count = count +1 WHERE fileID = '${fileID}'")
    void addCount(String fileID);

    @Select("SELECT * FROM ${userName}_file WHERE deleteMark=0 and fileID ='${fileID}' and belongLayer = ${belongLayer} and belongOrder = ${belongOrder}")
    File checkFileInUser(String userName,String fileID,Integer belongLayer,Integer belongOrder);

    @Select("SELECT * FROM ${userName}_file WHERE deleteMark=0 and fileOrNot = 0")
    List<File> checkFolder(String userName);

    @Select("SELECT * FROM orgfile_${OrgID} WHERE deleteMark=0 and fileOrNot = 0 and belongOrgLayer = ${OrgLayer} and belongOrgOrder = ${OrgOrder}")
    List<File> checkOrgFolderByDepartment(Long OrgID,Integer OrgLayer,Integer OrgOrder);

    @Select("UPDATE ${userName}_file SET deleteMark = 1 WHERE layer = ${layer} and `order` = ${order} ")
    Void deleteFile(String userName,Integer layer,Integer order);

    @Select("UPDATE ${userName}_file SET deleteMark = 0 WHERE layer = ${layer} and `order` = ${order} ")
    Void cancelDeleteFile(String userName,Integer layer,Integer order);

    @Select("SELECT * FROM org_${OrgID} WHERE userOrNot = 0 and deleteMark =0")
    List<OrgUser> checkOrgFolder(Long OrgID);



}
