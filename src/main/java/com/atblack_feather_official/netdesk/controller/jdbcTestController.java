package com.atblack_feather_official.netdesk.controller;



import com.atblack_feather_official.netdesk.bean.*;
import com.atblack_feather_official.netdesk.service.*;
import com.mysql.jdbc.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class jdbcTestController {

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @Autowired
    LinkService linkService;

    @Autowired
    OrgUserService orgUserService;
    
    @Autowired
    MsgService msgService;
    @ResponseBody
    @GetMapping("/getUserIDByName")
    public Integer getUserIDByName(@RequestParam("userName") String userName){
        return userService.getUserIDByName(userName);
    }

    @ResponseBody
    @GetMapping("/sql")
    public User getUserById(@RequestParam("id") long id){
        return userService.getUserById(id);
    }
    @ResponseBody
    @GetMapping("/sqlCheck")
    public User getUserByIdAndCheck(@RequestParam("id") long id,HttpSession session){
        User loginUser =(User) session.getAttribute("loginUser");
        if (id==loginUser.getID()){
            User user = new User();
            user.setID((long) -1);
            return user;
        }
        return userService.getUserById(id);
    }

    @ResponseBody
    @GetMapping("/getUserByName")
    public User getUserByName(HttpSession session){
        User loginUser =(User) session.getAttribute("loginUser");
        return userService.getUserById(loginUser.getID());
    }


    @ResponseBody
    @GetMapping("/CheckUserExists")
    public boolean CheckUserExists(@RequestParam("userName") String userName){
        return userService.CheckUserExistByUserName(userName);
    }
    @ResponseBody
    @GetMapping("/CheckUserPassword")
    public boolean CheckUserPassword(@RequestParam("userName") String userName,@RequestParam("password") String password){
        String password_MD5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (password_MD5.equals(userService.getUserPasswordByUserName(userName))){
            return true;
        }
        else
            return false;
    }
    @ResponseBody
    @GetMapping("/list_json")
    public Map<String, Object> retrievalAllFilesJsonByLocation(HttpSession session, @RequestParam Integer belongLayer,
                                                               @RequestParam Integer belongOrder, HttpServletRequest request){
        User loginUser = (User)session.getAttribute("loginUser");
        List<File> files = fileService.retrievalAllFilesByLocation(belongLayer, belongOrder, loginUser.getUserName() ,request.getParameter("remark"),request.getParameter("fileType"));

        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer page = Integer.valueOf(request.getParameter("page"));

        if (files == null ){
            return fileDataToJson(null,0);
        }
        if ( files.size()<limit)
            return fileDataToJson(files,files.size());
        else {
            if (files.size()>=limit*page)
                return fileDataToJson(files.subList(limit * (page - 1), limit * page), files.size());
            else
                return fileDataToJson(files.subList(limit * (page - 1), files.size()), files.size());
        }
    }

    @ResponseBody
    @GetMapping("/OrgList_json")
    public Map<String, Object> retrievalAllOrgFilesByLocation(HttpSession session, @RequestParam Integer belongLayer,
                                                               @RequestParam Integer belongOrder,@RequestParam Integer OrgLayer,
                                                                  @RequestParam Integer OrgOrder, HttpServletRequest request){

        User loginUser = (User)session.getAttribute("loginUser");
        List<File> files = fileService.retrievalAllOrgFilesByLocation(Math.toIntExact(loginUser.getBelonging()),OrgLayer,OrgOrder, belongLayer ,belongOrder,request.getParameter("remark"),loginUser.getUserName());
        if (files != null)
          return fileDataToJson(files,files.size());
        else
            return fileDataToJson(null,0);
    }


    @ResponseBody
    @GetMapping("/linkList_json")
    public Map<String, Object> retrievalAllFilesJsonByLink(@RequestParam("url") String ID, @RequestParam("belongLayer") Integer belongLayer, @RequestParam("belongOrder") Integer belongOrder){
        List<File> files = fileService.retrievalAllFilesByLink(belongLayer, belongOrder, ID );
        if (files == null)
            return fileDataToJson(null,0);
        else
        return fileDataToJson(files,files.size());
    }
    private Map<String, Object> LinkDataToJson(List<Link> links,Integer linksSize){
        Map<String, Object> links_json = new HashMap<>();
        links_json.put("code",0);
        links_json.put("msg","");
        if (links == null)
            links_json.put("count",0);
        else
            links_json.put("count",linksSize);
        links_json.put("data",links);
        return links_json;
    }
    
    @ResponseBody
    @GetMapping("/linkListByUser_json")
    public Map<String, Object> linkListByUser(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        List<Link> links = linkService.linkListByUser(loginUser.getID());
        for (Link link : links) {
            link.setID("http://localhost:8080/link?url="+link.getID());
        }
        return LinkDataToJson(links,links.size());
    }
    @ResponseBody
    @GetMapping("/deleteLink")
    public void deleteLink(@RequestParam String linkID){
        linkService.deleteLink(linkID);
    }

    private Map<String, Object> fileDataToJson(List<File> files,Integer filesSize) {
        Map<String, Object> files_json = new HashMap<>();
        files_json.put("code",0);
        files_json.put("msg","");
        if (files == null)
            files_json.put("count",0);
        else
            files_json.put("count",filesSize);
        files_json.put("data",files);
        return files_json;
    }


    @ResponseBody
    @GetMapping("/QueryIndexTree")
    public Object indexTree(HttpSession session){

        User loginUser = (User)session.getAttribute("loginUser");
        return fileService.indexTree(loginUser.getUserName());
    }
    @ResponseBody
    @GetMapping("/QueryOrgFileTree")
    public Object OrgFileTree(HttpSession session,Integer OrgLayer,Integer OrgOrder){

        User loginUser = (User)session.getAttribute("loginUser");
        return fileService.orgFileTree(loginUser.getBelonging(),OrgLayer,OrgOrder);
    }
    @ResponseBody
    @GetMapping("/QueryOrgTree")
    public Object OrgIndexTree(HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        return fileService.OrgIndexTree(loginUser.getBelonging());
    }


    @ResponseBody
    @GetMapping("/queryMusicName")
    public String queryMusicName(@RequestParam String MusicID,HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        return  fileService.queryMusicName(MusicID,loginUser.getUserName());
    }

    @ResponseBody
    @GetMapping("/list")
    public List<File> retrievalAllFilesByLocation(HttpSession httpSession, @RequestParam("belongLayer") Integer layer
            , @RequestParam("belongOrder") Integer order,HttpServletRequest httpServletRequest){

        User loginUser = (User)httpSession.getAttribute("loginUser");
        if (loginUser.getUserName()!=null)
            return fileService.retrievalAllFilesByLocation(layer, order,loginUser.getUserName(),"",httpServletRequest.getParameter("fileType"));
        else {
            return null;
        }
    }

    @ResponseBody
    @GetMapping("/linkList")
    public List<File> retrievalAllLinkFilesByLocation(@RequestParam("belongID") String belongID, @RequestParam("belongLayer") Integer belongLayer,
                                                      @RequestParam("belongOrder") Integer belongOrder){
        return linkService.queryAllLinkFileByLocation(belongID, belongLayer, belongOrder);
    }

    @ResponseBody
    @GetMapping("/createLink")
    public void createLink(HttpSession session,@RequestParam("ID") String ID,@RequestParam("password") String password,
                           @RequestParam("reMark") String reMark,@RequestParam("deadTime")String deadTime){
        User loginUser = (User)session.getAttribute("loginUser");
        linkService.addLink(ID,password,loginUser.getID(),deadTime,reMark);
    }

    @ResponseBody
    @GetMapping("/addFilesToLink")
    public void addFileToLink(@RequestParam String belongID,@RequestParam String remark,@RequestParam String fileID,
                              @RequestParam Integer layer,@RequestParam Integer order,@RequestParam Integer belongLayer,
                              @RequestParam Integer belongOrder,@RequestParam String fileType,@RequestParam Integer fileOrNot,
                              @RequestParam Integer sizeOfKB){
        linkService.addFileToLink(belongID,remark,fileID,layer,order,belongLayer,belongOrder,fileType,fileOrNot,sizeOfKB);
    }

    @ResponseBody
    @GetMapping("/addFilesToOrg")
    public void addFileToOrg(HttpSession session,@RequestParam String remark,@RequestParam String fileID,
                              @RequestParam Integer layer,@RequestParam Integer order,@RequestParam Integer belongLayer,
                              @RequestParam Integer belongOrder,@RequestParam String fileType,@RequestParam Integer fileOrNot,
                              @RequestParam Integer sizeOfKB,@RequestParam Integer OrgLayer,@RequestParam Integer OrgOrder){
        User loginUser = (User)session.getAttribute("loginUser");
        linkService.addFileToOrg(loginUser.getBelonging(),loginUser.getID(),remark,fileID,layer,order,belongLayer,belongOrder,fileType,fileOrNot,sizeOfKB,OrgLayer,OrgOrder);

    }
    @ResponseBody
    @GetMapping("/addLinkToFile")
    public void addLinkToFile(HttpSession session,@RequestParam String remark,@RequestParam String fileID,
                              @RequestParam Integer layer,@RequestParam Integer order,@RequestParam Integer belongLayer,
                              @RequestParam Integer belongOrder,@RequestParam String fileType,
                              @RequestParam Long sizeOfKB,@RequestParam Integer fileOrNot){
        User loginUser = (User)session.getAttribute("loginUser");
        fileService.addFile(fileID,remark,fileType,layer,order,belongLayer,belongOrder,loginUser.getUserName(),sizeOfKB,fileOrNot);
    }

    @ResponseBody
    @GetMapping("/queryMaxOrder")
    public Integer queryMaxOrder(HttpSession session,@RequestParam Integer layer){
        User loginUser = (User)session.getAttribute("loginUser");
        return fileService.queryMaxOrder(loginUser.getUserName(),layer);
    }

    @ResponseBody
    @GetMapping("/queryMaxOrderInLink")
    public Integer queryMaxOrderInLink(@RequestParam String belongID,@RequestParam Integer layer){

        return linkService.queryMaxOrderInLink(belongID,layer);
    }

    @ResponseBody
    @GetMapping("/queryMaxOrderInOrg")
    public Integer queryMaxOrderInLink(HttpSession session,@RequestParam Integer layer,@RequestParam Integer OrgLayer,@RequestParam Integer OrgOrder){
        User loginUser = (User)session.getAttribute("loginUser");
        return linkService.queryMaxOrderInOrg(loginUser.getBelonging(),layer,OrgLayer,OrgOrder);

    }

    @ResponseBody
    @GetMapping("/addFolder")
    public void addFolder(@RequestParam String remark,@RequestParam Integer layer,
                          @RequestParam Integer order,@RequestParam Integer belongLayer,
                          @RequestParam Integer belongOrder,HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        fileService.addFolder(remark,layer,order,belongLayer,belongOrder,loginUser.getUserName());
    }

    @ResponseBody
    @GetMapping("/CreatFileInfo")
    public void CreatFileInfo(@RequestParam String fileID,@RequestParam String src){
        fileService.CreatFileInfo(fileID,src);
    }

    @ResponseBody
    @GetMapping("/checkLinkExist")
    public Integer checkLinkExist(@RequestParam("url") String ID){
        if (linkService.checkLinkExist(ID) == null)
            return 0;
        if (linkService.checkLinkExist(ID).getDeadTime() == null)
            return 2;
        else
            return 1;

    }
    @ResponseBody
    @GetMapping("/checkLinkPassword")
    public boolean checkLinkPassword(@RequestParam("ID") String ID,@RequestParam("password") String password){

        if (password.equals(linkService.checkLinkPassword(ID)))
            return true;
        else
            return false;
    }
    @ResponseBody
    @GetMapping("/queryUserClass")
    public Integer queryUserClass(HttpSession session){

        User loginUser = (User)session.getAttribute("loginUser");
        return userService.queryUserClass(loginUser.getUserName());
    }

    @ResponseBody
    @GetMapping("/creatOrg")
    public void creatOrg (HttpSession session){
        User loginUser = (User)session.getAttribute("loginUser");
        userService.establishUserOrgTableByUserID(loginUser.getID());

    }

    @ResponseBody
    @GetMapping("/userList_json")
    public Map<String, Object> retrievalAllUser(HttpSession session, @RequestParam("belongLayer") Integer belongLayer, @RequestParam("belongOrder") Integer belongOrder){
        User loginUser = (User)session.getAttribute("loginUser");
        List<OrgUser> users = orgUserService.getAllUserByOrgID(loginUser.getBelonging(),belongLayer, belongOrder);
        for (OrgUser user : users) {
            if (user.getUserID() == 0){
                user.setRemark("<a  onclick=\"addPath('"+ user.getRemark() + "'," + user.getLayer() + "," + user.getOrder() +")\" href='OrgManager?belongLayer=" + user.getLayer() + "&belongOrder=" + user.getOrder() + "'>" + user.getRemark() + "</a>");
            }
        }
        return userDataToJson(users);
    }

    private Map<String, Object> userDataToJson(List<OrgUser> users) {
        Map<String, Object> users_json = new HashMap<>();
        users_json.put("code",0);
        users_json.put("msg","");
        if (users == null)
            users_json.put("count",0);
        else
            users_json.put("count",users.size());
        users_json.put("data",users);
        return users_json;
    }

    @ResponseBody
    @GetMapping("/Msg_json")
    public  Map<String, Object> Msg_json(HttpSession session,@RequestParam String type){
        User loginUser = (User) session.getAttribute("loginUser");
        List<Msg> msgs = null;
        if (type.equals("receiver")) {
            msgs = msgService.MsgByReceiver(loginUser.getID());
        }
        if (type.equals("sender"))
            msgs = msgService.MsgBySender(loginUser.getID());
        if (msgs.size()!=0)
        for (Msg msg : msgs) {
            if ("0".equals(msg.getReadMark()))
                msg.setReadMark("未读");
            if ("1".equals(msg.getReadMark()))
                msg.setReadMark("已读");
            if ("0".equals(msg.getConfirm()))
                msg.setConfirm("未确认");
            if ("1".equals(msg.getConfirm()))
                msg.setConfirm("已确认");
            if ("2".equals(msg.getConfirm()))
                msg.setConfirm("已拒绝");
        }

        return MsgListToJson(msgs);
    }
    
    Map<String, Object> MsgListToJson(List<Msg> msgs){
        Map<String, Object> msgs_json = new HashMap<>();
        msgs_json.put("code",0);
        msgs_json.put("msg",0);
        if (msgs == null)
            msgs_json.put("count",0);
        else
            msgs_json.put("count",msgs.size());
        msgs_json.put("data",msgs);
        return msgs_json;
    }

    @ResponseBody
    @GetMapping("/addGroupNote")
    public  void addGroupNote(HttpSession session,HttpServletRequest request,@RequestParam Integer  belongLayer,@RequestParam Integer belongOrder){
        User loginUser = (User)session.getAttribute("loginUser");
        String remark = request.getParameter("remark");
        orgUserService.addGroupNote(loginUser.getBelonging(),remark,belongLayer,belongOrder);
    }

    @ResponseBody
    @GetMapping("/addUserNote")
    public  void addUserNote(HttpSession session,HttpServletRequest request,@RequestParam Integer  belongLayer,@RequestParam Integer belongOrder){
        User loginUser = (User)session.getAttribute("loginUser");
        String remark = request.getParameter("remark");
        if (remark == null)
            remark = loginUser.getNetName();
        if (remark == null)
            remark = loginUser.getUserName();

        orgUserService.addUserNote(Long.valueOf(request.getParameter("OrgID")),remark,belongLayer,belongOrder,loginUser.getID());
        userService.addUserToOrg(loginUser.getID(), Long.valueOf(request.getParameter("OrgID")));
    }

    @ResponseBody
    @GetMapping("/applicantUser")
    public  void applicantUserIn(HttpSession session, @RequestParam Integer  belongLayer, @RequestParam Integer belongOrder,@RequestParam Long userID){

        User loginUser = (User)session.getAttribute("loginUser");
        orgUserService.addUserNote(loginUser.getBelonging(),null,belongLayer,belongOrder,userID);
        userService.addUserToOrg(userID,loginUser.getBelonging());

    }

    @ResponseBody
    @GetMapping("/deleteFile")
    public  void deleteFile(HttpSession session,@RequestParam Integer layer,@RequestParam Integer order){
        User loginUser = (User) session.getAttribute("loginUser");
        fileService.deleteFile(loginUser.getUserName(),layer,order);
    }
    @ResponseBody
    @GetMapping("/cancelDeleteFile")
    public  void cancelDeleteFile(HttpSession session,@RequestParam Integer layer,@RequestParam Integer order){
        User loginUser = (User) session.getAttribute("loginUser");
        fileService.cancelDeleteFile(loginUser.getUserName(),layer,order);
    }

    @ResponseBody
    @GetMapping("/checkFileDelete")
    public List<File> checkFileDelete(HttpSession session , @RequestParam Integer layer , @RequestParam Integer order){
        User loginUser = (User) session.getAttribute("loginUser");
        return fileService.checkFileDelete(loginUser.getUserName(),layer,order);
    }

    @ResponseBody
    @GetMapping("/sendOrgInvent")
    public void addOrgInvent(HttpSession session,Long receiver,String msgID,Integer layer,Integer order){
        User loginUser = (User) session.getAttribute("loginUser");
        msgService.addOrgInvent(loginUser.getBelonging(),receiver,msgID,layer,order);
    }

    @ResponseBody
    @GetMapping("/sendOrgApplication")
    public void addOrgApplication(HttpSession session,Long receiver,String msgID){
        User loginUser = (User) session.getAttribute("loginUser");
        msgService.addOrgApplication(loginUser.getID(),receiver,msgID);
    }

    @ResponseBody
    @GetMapping("/delOrgUser")
    public void delOrgUser(HttpSession session ,@RequestParam Integer layer,@RequestParam Integer order){
        User loginUser = (User) session.getAttribute("loginUser");
        orgUserService.delOrgUser(loginUser.getBelonging(),layer,order);
    }

    @ResponseBody
    @GetMapping("/readMsg")
    public void readMsg(String msgID) {
        msgService.readMsg(msgID);
    }
    @ResponseBody
    @GetMapping("/acceptMsg")
    public void acceptMsg(String msgID) {
        msgService.acceptMsg(msgID);
    }
    @ResponseBody
    @GetMapping("/rejectMsg")
    public void rejectMsg(String msgID) {
        msgService.rejectMsg(msgID);
    }


}
