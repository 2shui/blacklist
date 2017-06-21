package com.blacklist.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blacklist.config.MiniProgramConfig;

public class MailSender {
	private static Logger log = LoggerFactory.getLogger(MailSender.class);
	
	public static boolean sendGmail(String toUser, String title, String content) {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.port", "578");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		try {
			Session session = Session.getDefaultInstance(prop);
			MimeMessage msg = new MimeMessage(session);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
			msg.setSubject(title);
			msg.setContent(content, "text/html");
			
			Transport ts = session.getTransport("smtp");
			//ts.connect("smtp.gmail.com", "itblacklist.cn@gmail.com", "blacklist@gmail");
			ts.connect("smtp.gmail.com", MiniProgramConfig.userNameGmail, MiniProgramConfig.pwdGmail);
			ts.sendMessage(msg, msg.getAllRecipients());
			ts.close();
		} catch (Exception e) {
			log.error("send gmail error:{}", e);
			return false;
		}
		
		return true;
	}
	
	private static boolean sendhy(String toUser, String title, String content,
			String user, String pwd, String host) {
		Properties prop = System.getProperties();
		prop.put("mail.host", host);
		prop.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user,pwd);
			}
		});
		session.setDebug(true);
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(user));
			msg.setRecipient(RecipientType.TO, new InternetAddress(toUser));
			msg.setSubject(title);
			MimeMultipart mm = new MimeMultipart();
			MimeBodyPart body = new MimeBodyPart();
			body.setContent(content, "text/html;charset=utf-8");
			mm.addBodyPart(body);
			msg.setContent(mm);
			Transport.send(msg);
		} catch (Exception e) {
			log.error("send mail error:{}", e);
			return false;
		}
		return true;
	}

	public static boolean send163(String toUser, String title, String content) {
		return sendhy(toUser, title, content,
				MiniProgramConfig.userName163, MiniProgramConfig.pwd163, "smtp.163.com");
	}

	public static boolean send126(String toUser, String title, String content) {
		return sendhy(toUser, title, content, 
				"zgjlovelife@126.com", "localhost2126", "smtp.126.com");
//				MiniProgramConfig.userName126, MiniProgramConfig.pwd126, "smtp.126.com");
	}
	/*
	public static void main(String[] args) {
		String con = "hi honey:<br/>今天是农历2017-12-31"+
				"，您在<a href='http://blog.itblacklist.cn/'>小程序</a>中设置了提醒：<b>老表生日"+"</b>。<br/>不要忘记这个重要的日子哦~";
		MailSender.send126("731458741@qq.com", "日程提醒-【日历小二】", con);
	}
	*/
}
