package com.wang;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

// 发送一封简单的邮件
public class MailDemo01 {
    public static void main(String[] args) throws Exception {


        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.qq.com");// 设置qq邮箱服务器
        prop.setProperty("mail.transport.protocol","smtp");// 邮箱发送协议
        prop.setProperty("mail.smtp.auth","true");// 需要验证用户名密码

        // 关于qq邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);

        // 使用JavaMail发送邮件5个步骤

        // 1.创建定义整个应用程序所需的环境信息的 Session 对象

        // QQ才有！ 其他邮箱不用
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // 发件人邮件用户名，授权码
                return new PasswordAuthentication("2375847849@qq.com", "xohefzhudrecechh");
            }
        });
        // 开启session的debug模式，这样就可以查看到程序发送Email的状态

        // 2.通过session得到transport对象
        Transport ts = session.getTransport();
        // 3.使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.qq.com","2375847849@qq.com","xohefzhudrecechh");
        // 4.创建邮件
        // 需要传递session
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("2375847849@qq.com"));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("2375847849@qq.com"));
        // 邮件的标题
        message.setSubject("只包含文本的简单文件");
        // 邮件的文本内容
        message.setContent("<h1>你好啊！</h1>","text/html;charset=UTF-8");
        // 5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());
        // 6.关闭连接
        ts.close();
    }
}
