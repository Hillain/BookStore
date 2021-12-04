package com.rain.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件工具类
 * @author Administrator
 *
 */
public class MailUtil {

	private JavaMailSender javaMailSender;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	//发件人
	private String from;

	public void sendMail(String to, String subject, String text) throws MessagingException{
		//1. 创建邮件信息
		MimeMessage message = javaMailSender.createMimeMessage();
		//2. 使用spring邮件工具类
		MimeMessageHelper helper = new MimeMessageHelper(message);
		//3.收件人
		helper.setTo(to);
		//4.发件人
		helper.setFrom(from);
		//5.设置邮件的标题
		helper.setSubject(subject);
		//6.邮件的内容
		helper.setText(text);
		//7.发送邮件
		javaMailSender.send(message);
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getRandomCode(){
		String[] letter = {"0","1","2","3","4","5","6","7","8","9",
					"a","b","c","d","e","f","g","h","i","j","k","l",
					"m","n","o","p","q","r","s","t","u","v","w","x",
					"y","z","A","B","C","D","E","F","G","H","I","J",
					"K","L","M","N","O","P","Q","R","S","T","U","V",
					"W","X","Y","Z"};
		int random = -1;
		String code = "";
		for(int i=0;i<4;i++){
			random = (int)(Math.random()*62);
			code += letter[random];
		}
		/*System.out.println(code);*/
		return code;
	}
}
