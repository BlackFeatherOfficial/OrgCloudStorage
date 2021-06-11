package com.atblack_feather_official.netdesk.controller;





import com.atblack_feather_official.netdesk.service.FileService;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

@Slf4j
@Controller
public class FormTestController {

    @Autowired
    FileService fileService;

    public String getMd5(MultipartFile file) {

        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            return hashString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/upload")
    public void upload(@RequestPart("chooseFile") MultipartFile[] multiple,
                       @RequestParam("userName")String userName,
                       @RequestParam("belongOrder") Integer belongOrder,
                       @RequestParam("belongLayer") Integer belongLayer,
                         HttpServletResponse httpServletResponse

    ){
        String src = "files?userName="+userName+"&belongLayer="+belongLayer+"&belongOrder="+belongOrder;
        if(multiple.length > 0){
            for (MultipartFile multipartFile : multiple) {
                if(!multipartFile.isEmpty()){
                    String fileType=multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                    String fileName_MD5 = getMd5(multipartFile);
                    String fileName = multipartFile.getOriginalFilename();
                    log.info(fileName_MD5 + fileName);
                    Integer order = 0;
                  if ( fileService.queryMaxOrder(userName, (belongLayer + 1)) != null)
                      order = fileService.queryMaxOrder(userName, (belongLayer + 1));

                    if (fileService.checkFile(fileName_MD5)==null) {
                        log.info("文件第一次上传");
                        //一下写入真实上传文件的操作
                        fileService.CreatFileInfo(fileName_MD5, "static\\\\update\\\\" + fileName_MD5 + fileType);
                        fileService.addFile(fileName_MD5,fileName,fileType,(belongLayer+1),(order+1),belongLayer,belongOrder,userName,(multipartFile.getSize()/1024),1);
                        try {
                            multipartFile.transferTo(new File("G:\\IDEAWorkspace\\netdesk\\src\\main\\resources\\static\\update\\" + fileName_MD5 + fileType));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        log.info("服务器文件重复上传");
                        if(fileService.checkFileInUser(userName,fileName_MD5,belongLayer,belongOrder) ==null) {
                            log.info("用户文件第一次上传至该文件夹");
                            fileService.addCount(fileName_MD5);
                            fileService.addFile(fileName_MD5, fileName, fileType, (belongLayer + 1), (order + 1), belongLayer, belongOrder, userName,(multipartFile.getSize()/1024),1);
                        }
                        else {
                            log.info("用户文件重复上传");
                            src+="&msg=1";
                        }
                    }
                }
            }
        }

//        if(repeat == 0)
//         return "files?userName="+userName+"&belongLayer="+belongLayer+"&belongOrder="+belongOrder;
//        else
//            return "files?msg=请勿重复上传文件&userName="+userName+"&belongLayer="+belongLayer+"&belongOrder="+belongOrder;


        try {
            httpServletResponse.sendRedirect(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
