package com.atblack_feather_official.netdesk.controller;

import com.atblack_feather_official.netdesk.bean.File;
import com.atblack_feather_official.netdesk.bean.Msg;
import com.atblack_feather_official.netdesk.bean.User;
import com.atblack_feather_official.netdesk.bean.UserInfo;
import com.atblack_feather_official.netdesk.service.FileService;
import com.atblack_feather_official.netdesk.service.MsgService;
import com.atblack_feather_official.netdesk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.HandshakeRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Controller
public class pageController {

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @Autowired
    MsgService  msgService;

    @GetMapping({"/", "/login"})
    public String login_pageController(){
        return "login";
    }

    //重定向防止表单重复提交
    @GetMapping("/addUserToSession")
    public void addUserToSession(HttpSession session, @RequestParam String userName){
        User loginUser = userService.getUserByUserName(userName);
        loginUser.setPassword(DigestUtils.md5DigestAsHex(loginUser.getPassword().getBytes()));
        session.setAttribute("loginUser", loginUser);
        session.setAttribute("userName",loginUser.getUserName());
        log.info("已经添加user"+userName);
    }

    public static double getRealValue(double value, int resLen) {
        if(resLen==0)
            return Math.round(value*10+5)/10;
        double db = Math.pow(10, resLen);
        return Math.round(value*db)/db;
    }


    @GetMapping("/updateUserInfo")
    @ResponseBody
    public void updateUserInfo(HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        UserInfo userInfo = fileService.queryUserInfo(user.getUserName());
        Double percent;
        Integer filesSize = userInfo.getFilesSize();
        Integer storageSpace = userInfo.getStorageSpace();
        percent = (double)filesSize/storageSpace;
        percent *=100;
        percent =getRealValue(percent,2);
        userInfo.setPercent(percent);
        session.setAttribute("userInfo",userInfo);
    }

    @PostMapping("/login")
    public String index_pageController(HttpSession session, @RequestParam String userName,HttpServletResponse httpServletResponse){
        User loginUser = userService.getUserByUserName(userName);

        if (loginUser != null) {
            loginUser.setPassword(DigestUtils.md5DigestAsHex(loginUser.getPassword().getBytes()));
            session.setAttribute("loginUser", loginUser);
            updateUserInfo(session);
            try {
                if (session.getAttribute("backURL")!=null ) {
                    //如果参数是url形式的代表要转到link链接
                    if (session.getAttribute("url")!=null)
                        try {
                            httpServletResponse.sendRedirect(session.getAttribute("backURL").toString()+"?url="+session.getAttribute("url").toString());
                            session.setAttribute("backURL",null);
                            session.setAttribute("url",null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    //如果参数是ID形式的，代表要转到invent链接
                    if (session.getAttribute("ID")!=null)
                        try {
                            httpServletResponse.sendRedirect(session.getAttribute("backURL").toString()+"?ID="+session.getAttribute("ID").toString());
                            session.setAttribute("backURL",null);
                            session.setAttribute("ID",null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                else
                httpServletResponse.sendRedirect("/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        else {
            session.setAttribute("msg","服务器未找到用户信息");
            return "login";
        }
    }

    @GetMapping("/index")
    public String ReIndex(){
        return "index";
    }

    @ResponseBody
    @GetMapping("/getMsg")
    public Msg getMsg(HttpSession session, @RequestParam String ID){
        User loginUser = (User) session.getAttribute("loginUser");
        Msg msg = msgService.getMsgByID(ID);

        if (loginUser.getBelonging()!=null ) {
            Msg msg1 = new Msg();
            msg1.setMsg("haveBelonging");
            return msg1;
        }
        if (msg != null ){
            if (msg.getReceiver() == loginUser.getID())
             return msg;
            else{
                Msg msg2 = new Msg();
                msg2.setMsg("errorReceiver");
                return msg2;
            }
        }
        else
            return null;
    }

    @ResponseBody
    @GetMapping("/getApplicationMsg")
    public Msg getApplicationMsg(HttpSession session, @RequestParam String ID){
        User loginUser = (User) session.getAttribute("loginUser");
        Msg msg = msgService.getMsgByID(ID);

        if(loginUser.getUserClass()<=1){
            Msg msg2 = new Msg();
            msg2.setMsg("noClass");
            return msg2;
        }
        if(loginUser.getBelonging() != msg.getReceiver()){
            Msg msg1 = new Msg();
            msg1.setMsg("ErrorReceiver");
            return msg1;
        }
        else
            return msg;
    }

    @PostMapping("/register")
    public void registerToIndex(HttpSession session, @RequestParam("registerUserName") String username, @RequestParam("registerPassword") String password, HttpServletResponse httpServletResponse){
        String password_md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        userService.establishUserTableByUserName(username);
        session.setAttribute("loginUser",userService.addUserByFastRegister(username,password_md5));

        try {
            if (session.getAttribute("backURL")!=null || session.getAttribute("backURL")!="") {
                System.out.println("转发到"+session.getAttribute("backURL"));
                try {
                    httpServletResponse.sendRedirect(session.getAttribute("backURL").toString());
                    session.setAttribute("backURL",null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            httpServletResponse.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GetMapping("invent")
    public String inventPage(HttpSession session, HttpServletResponse response ,HttpServletRequest request) throws IOException {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("backURL",request.getRequestURL());
            session.setAttribute("ID",request.getParameter("ID"));
            return "login";
        }
        else
            return "ConfirmInvitation";
    }
    @GetMapping("application")
    public String applicationPage(HttpSession session, HttpServletResponse response ,HttpServletRequest request) throws IOException {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("backURL",request.getRequestURL());
            session.setAttribute("ID",request.getParameter("ID"));
            return "login";
        }
        else
            return "ConfirmApplication";
    }

    @GetMapping("test")
    public String testPage() {
        return "test";
    }
    @GetMapping("indexTree")
    public String indexFilePage() {
        return "indexTree";
    }
    @GetMapping("linkPassword")
    public String linkPasswordPage() {
        return "linkPassword";
    }
    @GetMapping("login_back")
    public String login_backPage() {
        return "login_back";
    }

    @GetMapping("link")
    public String linkPage(HttpSession session, HttpServletResponse response ,HttpServletRequest request) throws IOException {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("backURL",request.getRequestURI());
            session.setAttribute("url",request.getParameter("url"));
            return "login";
        }
        return "link";
    }

    @GetMapping("Overview")
    public String OverviewPage() {
        return "Overview";
    }
    @GetMapping("editLink")
    public String editLinkPage() {
        return "editLink";
    }
    @GetMapping("friend")
    public String friendPage() {
        return "friend";
    }
    @GetMapping("files")
    public String filesPage() {
        return "files";
    }
    @GetMapping("myLink")
    public String myLinkPage() {
        return "myLink";
    }
    @GetMapping("share")
    public String sharePage(HttpSession session) {
        updateUserInfo(session);
        return "share";
    }
    @GetMapping("recycle")
    public String recyclePage() {
        return "recycle";
    }
    @GetMapping("Msg")
    public String MsgPage() {
        return "Msg";
    }
    @GetMapping("music")
    public String musicPage() {
        return "music";
    }
    @GetMapping("text")
    public String textPage() {
        return "text";
    }
    @GetMapping("video")
    public String videoPage() {
        return "video";
    }
    @GetMapping("image")
    public String imagePage() {
        return "image";
    }
    @GetMapping("unknown")
    public String unknownPage() {
        return "unknown";
    }

    @GetMapping("OrgManager")
    public String OrgManagerPage(HttpSession session) {
        updateUserInfo(session);
        return "OrgManager";
    }
    @GetMapping("inventUser")
    public String inventUserPage() {
        return "inventUser";
    }
    @GetMapping("departmentName")
    public String departmentNamePage() {
        return "departmentName";
    }
    @GetMapping("/exit")
    public String userExit(HttpSession session){
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            session.removeAttribute(attributeNames.nextElement());
        }
        return "login";
    }

    @GetMapping("/OrgTree")
    public String QueryOrgTreePage(){
        return"OrgTree";
    }
    @GetMapping("/OrgFileTree")
    public String QueryOrgFileTreePage(){
        return"OrgFileTree";
    }

    @GetMapping("/inputFriendID")
    public String inputFriendIDPage(){
        return"inputFriendID";
    }



}
