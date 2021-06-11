package com.atblack_feather_official.netdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

@RestController
public class MailAction {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromUser;

    @RequestMapping("/send")
    public String sendMail(String receiver,String subject,String context){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("2410600421@qq.com");
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(context);

        javaMailSender.send(simpleMailMessage);
        return "success";
    }
    @RequestMapping("/sendHtml")
    public String sendHtmlMail(String receiver,String subject,String context) throws MessagingException {
        String htmlText = "";


        htmlText += "<div style=\"background-image:url('static/images/logo.png');background-size: 100% 100%;height: 600px;width: 600px;vertical-align:center;text-align: center\">\n" +
                "<p style=\"padding-top:140px;font-family: 'Adobe 黑体 Std R' ;font-size: 40px\" >黑羽official</p>\n" +
                "  <br>\n" +
                "  <p>您好，请点击\"<a href=\"http://localhost:8080\">http://localhost:8080/</a>\"</p>\n" +
                "</div>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setFrom("2410600421@qq.com");
        mimeMessageHelper.setText(htmlText,true);
        mimeMessageHelper.setSubject(subject);





//        附件 的发送
//        FileSystemResource fileSystemResource = new FileSystemResource("D:\\IDEAworkspace\\netdesk\\src\\main\\resources\\static\\images\\logo.png");
//        String filename = fileSystemResource.getFilename();
//        mimeMessageHelper.addAttachment(filename,fileSystemResource);

        javaMailSender.send(mimeMessage);
        return "success";
    }



    @RequestMapping("/sendImage")
    public String sendImageHtml(String receiver,String subject,String context) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        Message message = new MimeMessage(session);
        InternetAddress from = new InternetAddress(fromUser);
        from.setPersonal(MimeUtility.encodeText("黑羽Official<"+fromUser+">"));
        message.setFrom(from);
        /*抄送*/
        InternetAddress to = new InternetAddress(receiver);
        message.setRecipient(Message.RecipientType.TO, to);
        message.setSubject(MimeUtility.encodeText(subject));
        message.setSentDate(new Date());
        // 我就当这是一个消息包，类型是混杂的
        MimeMultipart msgMultipart = new MimeMultipart("mixed");// 指定为混合关系
        message.setContent(msgMultipart);
        //  显示图片必须为related，如果还需要添加附件必须为multi
        //  邮件内容
        MimeMultipart multipart = new MimeMultipart("related");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<div> <p style=\"padding-left: 85px; font-family: 'Adobe 黑体 Std R' ;font-size: 40px\" >黑羽Official</p> <img width=\"380px\" height=\"380px\"  src=\"cid:image\"> <p>" +context+
                "</p><p>请认准黑羽Official以及白羽Official，作为本系统官方邮件通知工作账号</p></div>";

           //     "<img src=\"cid:image\">";
        //  必须明确指定字体为UTF-8，避免中文乱码
        messageBodyPart.setContent(htmlText, "text/html;charset=utf-8;");
        multipart.addBodyPart(messageBodyPart);
        //  添加图片
        MimeBodyPart imagePart = new MimeBodyPart();
        DataSource fds = new FileDataSource("D:\\IDEAworkspace\\netdesk\\src\\main\\resources\\static\\images\\mailBG.png");
    //    DataSource fds1 = new FileDataSource("D:\\bg.png");
        imagePart.setDataHandler(new DataHandler(fds));
    //    imagePart.setDataHandler(new DataHandler(fds1));
        //  设置ID

        imagePart.setHeader("Content-ID", "<image>");
        imagePart.setHeader("Content-Type", "image/png");
        imagePart.setDisposition(MimeBodyPart.INLINE);
        imagePart.setFileName("image.png");
        //  添加内容

        multipart.addBodyPart(imagePart);
        message.setContent(multipart);
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.qq.com", 25, "2410600421@qq.com", "uwmwvtsvptdueccf");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("发送完毕");
        return "success";

    }
    @RequestMapping("/sendImageBY")
    public String sendImageHtmlFromBY(String receiver,String subject,String context) throws MessagingException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        Message message = new MimeMessage(session);
        InternetAddress from = new InternetAddress("908407503@qq.com");
        from.setPersonal(MimeUtility.encodeText("白羽Official<908407503@qq.com>"));
        message.setFrom(from);
        /*抄送*/
        InternetAddress to = new InternetAddress(receiver);
        message.setRecipient(Message.RecipientType.TO, to);
        message.setSubject(MimeUtility.encodeText(subject));
        message.setSentDate(new Date());
        // 我就当这是一个消息包，类型是混杂的
        MimeMultipart msgMultipart = new MimeMultipart("mixed");// 指定为混合关系
        message.setContent(msgMultipart);
        //  显示图片必须为related，如果还需要添加附件必须为multi
        //  邮件内容
        MimeMultipart multipart = new MimeMultipart("related");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<div> <p style=\"padding-left: 85px; font-family: 'Adobe 黑体 Std R' ;font-size: 40px\" >白羽Official</p> <img width=\"380px\" height=\"380px\"  src=\"cid:image\"> <p>" +context+
                "</p><p>请认准黑羽Official以及白羽Official，作为本系统官方邮件通知工作账号</p></div>";

        //     "<img src=\"cid:image\">";
        //  必须明确指定字体为UTF-8，避免中文乱码
        messageBodyPart.setContent(htmlText, "text/html;charset=utf-8;");
        multipart.addBodyPart(messageBodyPart);
        //  添加图片
        MimeBodyPart imagePart = new MimeBodyPart();
        DataSource fds = new FileDataSource("D:\\IDEAworkspace\\netdesk\\src\\main\\resources\\static\\images\\logoBY.png");
        //    DataSource fds1 = new FileDataSource("D:\\bg.png");
        imagePart.setDataHandler(new DataHandler(fds));
        //    imagePart.setDataHandler(new DataHandler(fds1));
        //  设置ID

        imagePart.setHeader("Content-ID", "<image>");
        imagePart.setHeader("Content-Type", "image/png");
        imagePart.setDisposition(MimeBodyPart.INLINE);
        imagePart.setFileName("image.png");
        //  添加内容

        multipart.addBodyPart(imagePart);
        message.setContent(multipart);
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.qq.com", 25, "908407503@qq.com", "hjkssfiorbgbbcbc");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("发送完毕");
        return "success";
    }
}
