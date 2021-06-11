package com.atblack_feather_official.netdesk.service;

import com.atblack_feather_official.netdesk.bean.File;
import com.atblack_feather_official.netdesk.bean.OrgUser;
import com.atblack_feather_official.netdesk.bean.UserInfo;
import com.atblack_feather_official.netdesk.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    FileMapper fileMapper;

    public UserInfo queryUserInfo(String userName){
        UserInfo userInfo = new UserInfo();

        userInfo.setFilesCount(fileMapper.queryUserAllFilesCount(userName)==null?0:fileMapper.queryUserAllFilesCount(userName));
        userInfo.setFilesSize(fileMapper.queryUserAllFilesSize(userName)==null?0:fileMapper.queryUserAllFilesSize(userName));
        userInfo.setImagesCount(fileMapper.queryUserImageFilesCount(userName)==null?0:fileMapper.queryUserImageFilesCount(userName));
        userInfo.setImagesSize(fileMapper.queryUserImageFilesSize(userName)==null?0:fileMapper.queryUserImageFilesSize(userName));
        userInfo.setMusicsCount(fileMapper.queryUserMusicFilesCount(userName)==null?0:fileMapper.queryUserMusicFilesCount(userName));
        userInfo.setMusicsSize(fileMapper.queryUserMusicFilesSize(userName)==null?0:fileMapper.queryUserMusicFilesSize(userName));
        userInfo.setTextsCount(fileMapper.queryUserTextFilesCount(userName)==null?0:fileMapper.queryUserTextFilesCount(userName));
        userInfo.setTextsSize(fileMapper.queryUserTextFilesSize(userName)==null?0:fileMapper.queryUserTextFilesSize(userName));
        userInfo.setVideosCount(fileMapper.queryUserVideoFilesCount(userName)==null?0:fileMapper.queryUserVideoFilesCount(userName));
        userInfo.setVideosSize(fileMapper.queryUserVideoFilesSize(userName)==null?0:fileMapper.queryUserVideoFilesSize(userName));

        userInfo.setUnknownCount(userInfo.getFilesCount()-userInfo.getImagesCount()-userInfo.getTextsCount()-userInfo.getMusicsCount()-userInfo.getVideosCount());
        userInfo.setUnknownSize(userInfo.getFilesSize()-userInfo.getImagesSize()-userInfo.getTextsSize()-userInfo.getMusicsSize()-userInfo.getVideosSize());

        userInfo.setStorageSpace((fileMapper.queryStorageSpace(userName)+1)*1024*1024);

        return userInfo;
    }

    public List<File> retrievalAllFilesByLocation(Integer belongLayer, Integer belongOrder, String userName,String remark,String fileType){
        List<File> files;

        //至查找某类型
        if (fileType !=null &&( remark ==null || remark == "")){
            System.out.println("Typ！=="+fileType+"rem= =="+remark);
            if (fileType.equals("text"))
                files = fileMapper.retrievalAllFilesByTextType(userName);
            else if (fileType.equals("image"))
                files = fileMapper.retrievalAllFilesByImageType(userName);
            else if (fileType.equals("video"))
                files = fileMapper.retrievalAllFilesByVideoType(userName);
            else if (fileType.equals("music"))
                files = fileMapper.retrievalAllFilesByMusicType(userName);
            else if (fileType.equals("delete"))
                files = fileMapper.retrievalAllFilesByDeleteType(userName);

            else
                files = fileMapper.retrievalAllFilesByUnknownType(userName);

        }
        //查找某类型符合要求的
        else if (fileType !=null && (remark != null || remark !="")){
            System.out.println("Typ！=="+fileType+"rem! =="+remark);
            if (fileType.equals("text"))
                files = fileMapper.retrievalAllFilesByTextTypeAndRemark(userName,remark);
            else if (fileType.equals("image"))
                files = fileMapper.retrievalAllFilesByImageTypeAndRemark(userName,remark);
            else if (fileType.equals("video"))
                files = fileMapper.retrievalAllFilesByVideoTypeAndRemark(userName,remark);
            else if (fileType.equals("music"))
                files = fileMapper.retrievalAllFilesByMusicTypeAndRemark(userName,remark);
            else
                files = fileMapper.retrievalAllFilesByUnknownTypeAndRemark(userName,remark);
        }
//查找所有{
        else if (fileType ==null && (remark == null || remark =="")) {
            files = fileMapper.retrievalAllFilesByLocation(belongLayer, belongOrder, userName);
        }
        //fileType =="" && remark == ""按名字查找所有文件
        else {
            files = fileMapper.retrievalAllFilesByLocationAndRemark(belongLayer, belongOrder, userName, remark);
        }


        for (File file : files) {
            if(file.getFileOrNot()==0) {
                file.setRemark("<a  onclick=\"addPath('"+ file.getRemark() + "'," + file.getLayer() + "," + file.getOrder() +")\" href='files?userName=" + userName + "&belongLayer=" + file.getLayer() + "&belongOrder=" + file.getOrder() + "'>" + file.getRemark() + "</a>");
                file.setFileType("文件夹");
            }
            else
                file.setRemark("<a onmouseover='showThis(\""+file.getFileID()+"\",\""+file.getFileType()+"\")' onmouseout ='hideThis(\""+file.getFileType()+"\")'  href='static/update/"+file.getFileID()+file.getFileType()+"' download='"+file.getRemark()+"'>"+file.getRemark()+"</a>");
        }
        if (files.isEmpty())
            return null;
        else
            return files;
    }

    public List<File> retrievalAllOrgFilesByLocation(Integer OrgID,Integer OrgLayer,Integer OrgOrder, Integer belongLayer ,Integer belongOrder,String remark ,String userName){
        List<File> files;

        if (remark == "")
            files = fileMapper.retrievalAllOrgFilesByLocation(OrgID,OrgLayer,OrgOrder,belongLayer,belongOrder);
        else
            files = fileMapper.retrievalAllOrgFilesByLocationAndRemark(OrgID,OrgLayer,OrgOrder,belongLayer,belongOrder,remark);

        for (File file : files) {
            if(file.getFileOrNot()==0) {
                file.setRemark("<a  onclick=\"addPath('"+ file.getRemark() + "'," + file.getLayer() + "," + file.getOrder() +")\" href='share?userName="+userName+"&OrgID=" + OrgID + "&belongLayer=" + file.getLayer() + "&belongOrder=" + file.getOrder() + "'>" + file.getRemark() + "</a>");
                file.setFileType("文件夹");
            }
            else
                file.setRemark("<a href='static/update/"+file.getFileID()+file.getFileType()+"' download='"+file.getRemark()+"'>"+file.getRemark()+"</a>");
        }
        if (files.isEmpty())
            return null;
        else
            return files;
    }



    public List<File> retrievalAllFilesByLink(Integer belongLayer, Integer belongOrder, String ID){
        List<File> files = fileMapper.retrievalAllFilesByLink(belongLayer, belongOrder, ID);
        for (File file : files) {
            if(file.getFileOrNot()==0) {
                file.setRemark("<a  onclick=\"addPath('"+ file.getRemark() + "'," + file.getLayer() + "," + file.getOrder() +")\" href='link?url=" + ID + "&belongLayer=" + file.getLayer() + "&belongOrder=" + file.getOrder() + "'>" + file.getRemark() + "</a>");
                file.setFileType("文件夹");
            }
            else
                file.setRemark("<a href='static/update/"+file.getFileID()+file.getFileType()+"' download='"+file.getRemark()+"'>"+file.getRemark()+"</a>");
        }
        if (files.isEmpty())
            return null;
        else
            return files;
    }

    public void CreatFileInfo(String fileID,String src){
        fileMapper.CreatFileInfo(fileID,src);
    }

    public Integer queryMaxOrder(String userName,Integer layer){
        if (fileMapper.queryOrderMax(layer,userName) != null)
            return fileMapper.queryOrderMax(layer,userName);
        else
            return 0;
    }

    public void addFile(String fileID,String remark,String fileType,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String userName,Long sizeOfKB,Integer fileOrNot){
        fileMapper.addFile(fileID,remark,fileType,layer,order,belongLayer,belongOrder,userName,sizeOfKB,fileOrNot);
    }

    public File checkFile(String fileID){
        return  fileMapper.checkFile(fileID);
    }

    public File checkFileInUser(String userName,String fileID,Integer belongLayer ,Integer belongOrder){
        return  fileMapper.checkFileInUser(userName,fileID,belongLayer,belongOrder);
    }

    public void addCount(String fileID){
        fileMapper.addCount(fileID);
    }

    public void addFolder(String remark,Integer layer,Integer order,Integer belongLayer,Integer belongOrder,String userName){
        fileMapper.addFolder( remark, layer, order,  belongLayer, belongOrder, userName);
    }



    public String indexTree(String userName){

        List<File> index = fileMapper.checkFolder(userName);
        String data = "[{title:'根目录{0-0}'},";

        data += creatIndexTree(index,0,0);

        data += "]";

        return data.replace(",children:[]","");
      //  return data;

    }
    public String orgFileTree(Long OrgID,Integer OrgLayer,Integer OrgOrder){
        List<File> index = fileMapper.checkOrgFolderByDepartment(OrgID,OrgLayer,OrgOrder);
        String data = "[{title:'根目录{0-0}'},";
        data += creatIndexTree(index,0,0);
        data += "]";

        String down= data.replace(",children:[]","");

        if (down.equals("[{title:'根目录{0-0}'},null]"))
            return "[{title:'根目录{0-0}'}]";
        else
            return down;
        //  return data; [{title:'根目录{0-0}'},null]

    }

    private String creatIndexTree(List<File> index,Integer belongLayer,Integer belongOrder) {
        String data = "";
        if (index.size()!=0)
        {
            for (File file : index) {
                if (file.getBelongOrder() == belongOrder & file.getBelongLayer() == belongLayer){
                    data += "{title:'"+file.getRemark()+"{"+file.getLayer()+"-"+file.getOrder()+"}',children:[";
                    data += creatIndexTree(index,file.getLayer(),file.getOrder());
                    data +="]},";
                }
            }
            return data;
        }
            else
                return null;
    }

    public String OrgIndexTree(Long OrgID){

        List<OrgUser> index = fileMapper.checkOrgFolder(OrgID);
        String data = "[{title:'根目录{0-0}'},";
        data += creatOrgIndexTree(index,0,0);
        data += "]";
        return data.replace(",children:[]","");
        //  return data;
    }
    private String creatOrgIndexTree(List<OrgUser> index,Integer belongLayer,Integer belongOrder) {
        String data = "";
        if (index.size()!=0)
        {
            for (OrgUser orgUser : index) {
                if (orgUser.getBelongOrder() == belongOrder & orgUser.getBelongLayer() == belongLayer){
                    data += "{title:'"+orgUser.getRemark()+"{"+orgUser.getLayer()+"-"+orgUser.getOrder()+"}',children:[";
                    data += creatOrgIndexTree(index,orgUser.getLayer(),orgUser.getOrder());
                    data +="]},";
                }
            }
            return data;
        }
        else
            return null;
    }
    public String queryMusicName(String MusicID,String UserName){
        return fileMapper.queryMusicName(MusicID,UserName);
    }

    public  void deleteFile(String userName,Integer layer,Integer order){
        fileMapper.deleteFile(userName,layer,order);
    }

    public  void cancelDeleteFile(String userName,Integer layer,Integer order){
        fileMapper.cancelDeleteFile(userName,layer,order);
    }

    public  List<File> checkFileDelete(String userName, Integer layer, Integer order){
        return fileMapper.checkFileDelete(userName,layer,order);
    }
//    public void buildRootFolder(String userName){fileMapper.buildRootFolder(userName);}

}
