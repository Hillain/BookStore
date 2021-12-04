package com.rain.controller;

import com.google.gson.Gson;
import com.rain.pojo.User;
import com.rain.service.UserService;
import com.rain.utils.MD5Utils;
import com.rain.utils.UserUtil;

//import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @RequestMapping(value = "login")
    public void login(HttpServletRequest req, HttpServletResponse resp){
        String strJson;
        try {
            req.setCharacterEncoding("UTF-8");//设置参数的编码格式;
            //必须要做这个转换，否则无法修改该Map中的数据
            Map<String,String[]> map=new HashMap<>(req.getParameterMap());
            String[] password = map.get("password");
            password[0] = MD5Utils.md5(password[0]);
            map.put("password", password);
            User user=new User();
            BeanUtils.populate(user,map);
            System.out.println(user);
            User user1=new User();
            user1=userService.selectUser(user);
            System.out.println(user1);
            if(null==user1){
                strJson = "{\"username\":\"0\"}";
            }else{
                int id=user1.getId();
                System.out.println(user1);
                strJson=new Gson().toJson(user1);
               // strJson="{\"username\":\""+user1.getUsername()+"\",\"password\":\""+user1.getPassword()+"\",\"pnumber\":\""+user1.getpnumber()+"\"}";
            }
            System.out.println(strJson);
            PrintWriter pw= resp.getWriter();
            pw.println(strJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value="register")
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        User user=new User();
        String[] password = map.get("password");
        String[] sex = map.get("sex");
        String[] addr = map.get("addr");
        //System.out.println("email="+map.get("email")[0]);
        String addr_str = URLDecoder.decode(req.getParameter("addr"),"UTF-8");
        String sex_str = URLDecoder.decode(req.getParameter("sex"),"UTF-8");
        sex[0] = sex_str;
        addr[0] = addr_str;
        //System.out.println("sex="+sex_str+"addr="+addr_str);
        password[0] = MD5Utils.md5(password[0]);
        map.put("sex", sex);
        map.put("addr", addr);
        map.put("password", password);  //解决No modifications are allowed to a locked ParameterMap
        BeanUtils.populate(user,map);
        user.setSign("这个人很懒，什么都没写~");	//这个人很懒，什么都没写~
        user.setNickname(UserUtil.getRandomUserName());	//随机
        System.out.println(user);
        int a=userService.insertUser(user);
        if(a>=0){
            strJson = "{\"success\":\""+a+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="getcode")
    public void getcode(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        //先把用户的信息放好
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        String code = userService.sendVerifyCode(req.getParameter("email"));//这里
        if(code!=null){
            strJson="{\"success\":\""+code+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    
    @RequestMapping(value="updateNick")
    public void updateNick(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        String[] nickname = map.get("nickname");
        String nickname_str = URLDecoder.decode(req.getParameter("nickname"),"UTF-8");
        nickname[0] = nickname_str;
        //System.out.println("sex="+sex_str+"addr="+addr_str);
        map.put("nickname", nickname);
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        int a=userService.updateNick(user);
        if(a>=0){
            strJson="{\"success\":\""+a+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="updateSex")
    public void updateSex(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        String[] sex = map.get("sex");
        String sex_str = URLDecoder.decode(req.getParameter("sex"),"UTF-8");
        sex[0] = sex_str;
        //System.out.println("sex="+sex_str+"addr="+addr_str);
        map.put("sex", sex);
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        int a=userService.updateSex(user);
        if(a>=0){
            strJson="{\"success\":\""+a+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="updateSign")
    public void updateSign(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        String[] sign = map.get("sign");
        String sign_str = URLDecoder.decode(req.getParameter("sign"),"UTF-8");
        sign[0] = sign_str;
        //System.out.println("sex="+sex_str+"addr="+addr_str);
        map.put("sex", sign);
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        int a=userService.updateSign(user);
        if(a>=0){
            strJson="{\"success\":\""+a+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="updatePwd")
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        
        //修改密码并加密
        String[] password = map.get("password");
        password[0] = MD5Utils.md5(password[0]);
        map.put("password", password);  //解决No modifications are allowed to a locked ParameterMap
        
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        int a=userService.updatePwd(user);
        if(a>=0){
            strJson="{\"success\":\""+a+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="updatePhone")
    public void updatePhone(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=req.getParameterMap();
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        int a=userService.updatePhone(user);
        if(a>=0){
            strJson="{\"success\":\""+a+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="updateEmail")
    public void updateEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=req.getParameterMap();
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        int a=userService.updateEmail(user);
        if(a>=0){
            strJson="{\"success\":\""+a+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="correctPhone")
    public void correctPhone(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=req.getParameterMap();
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        User user1 = userService.correctPhone(user);
        if(user1!=null){
            strJson="{\"success\":\"true\"}";
        }else{
            strJson = "{\"success\":\"false\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value="correctEmail")
    public void correctEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Map<String,String[]> map=req.getParameterMap();
        User user=new User();
        BeanUtils.populate(user,map);
        System.out.println(user);
        User user1 =userService.correctEmail(user);
        if(user1!=null){
            strJson="{\"success\":\"trueEmail\"}";
        }else{
            strJson = "{\"success\":\"falseEmail\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
}
