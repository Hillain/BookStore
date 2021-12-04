package com.rain.service.impl;

import com.rain.dao.UserMapper;
import com.rain.pojo.User;
import com.rain.service.UserService;
import com.rain.utils.MailUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private MailUtil mailUtil;
	@Value("欢迎注册若水平台，您的验证码是：[code]\n请记住您的验证码，有效期为5分钟\n感谢您的注册！\n\n\n时间：[time]")
	private String text;
	@Value("[email]")
	private String to;
	@Value("邮  箱  验  证")
	private String subject;
	private User user;
	
	@Resource
    private UserMapper userDao;
    /*@Override
    public User selectUser(int userId) {
        return userDao.selectOne(userId);
    }*/

    @Override
    public User selectUser(User user) {
        return userDao.selectUser(user);
    }

    @Override
    public int insertUser(User user) {
        try {
        	this.user = user;
            return userDao.insertUser(user);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public String sendVerifyCode(String email){
    	String t = null;
		String code = null;
		String to = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	//subject = 预警_时间:[time] => 预警_时间:2020-12-27 10:42:45
			t = text.replace("[time]", sdf.format(new Date()));			
			code = mailUtil.getRandomCode();
			t = t.replace("code", code);
			to = this.to.replace("[email]", email);
			mailUtil.sendMail(to, subject, t);
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
		return code;
    }
	@Override
	public int updateNick(User user) {
		return userDao.updateNick(user);
	}

	@Override
	public int updateSex(User user) {
		return userDao.updateSex(user);
	}

	@Override
	public int updateSign(User user) {
		return userDao.updateSign(user);
	}
	
	@Override
    public int updatePwd(User user) {
		return userDao.updatePwd(user);
    }
	
	@Override
	public int updatePhone(User user) {
		return userDao.updatePhone(user);
	}
	
	@Override
	public int updateEmail(User user) {
		return userDao.updateEmail(user);
	}

	@Override
	public User correctPhone(User user) {
		return userDao.correctPhone(user);
	}
	
	@Override
	public User correctEmail(User user) {
		return userDao.correctEmail(user);
	}
}
