package com.rain.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.rain.pojo.Book;
import com.rain.pojo.Car;
import com.rain.pojo.Category;
import com.rain.pojo.Order;
import com.rain.service.BookService;

@Controller
@RequestMapping("/Book")
public class BookController {

    @Resource
    private BookService bookService;
    @RequestMapping("/GetAllBook")
    public void getAllbook(HttpServletResponse resp,HttpServletRequest req) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String a=req.getParameter("lb");
        List<Book> AllBook;
        resp.setContentType("text/html; charset=utf-8");
        if(a==null){
             AllBook=bookService.selectBookList();
        }else{
             AllBook=bookService.selectBookListbyid(a);
        }
        System.out.println(AllBook);
        Gson gson=new Gson();
        String allbook=gson.toJson(AllBook);
        PrintWriter pw=resp.getWriter();
        pw.println(allbook);
    }

    @RequestMapping("/GetBookCategory")
    public void getBookCategory(HttpServletResponse resp) throws IOException {
        String strJson="";
        boolean bFirst = true;
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        List<Category> AllCategory=bookService.selectBookCate();
        System.out.println(AllCategory);
        PrintWriter pw=resp.getWriter();
        Gson gson=new Gson();
        String allcategory=gson.toJson(AllCategory);
        pw.println(allcategory);

    }
    @RequestMapping("/GetCar")
    public void getcar(HttpServletResponse resp,HttpServletRequest req) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String a=req.getParameter("id");
        int b=Integer.parseInt(a);
        System.out.println(b);
        List<Car> AllCar;
        resp.setContentType("text/html; charset=utf-8");
        AllCar=bookService.selectCar(b);
        Gson gson=new Gson();
        String allcar=gson.toJson(AllCar);
        PrintWriter pw=resp.getWriter();
        pw.println(allcar);
    }

    @RequestMapping("/deletecar")
    public void deletecar(HttpServletResponse resp,HttpServletRequest req) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String strJson;
        String a=req.getParameter("id");
        int b=Integer.parseInt(a);
        resp.setContentType("text/html; charset=utf-8");
        int result=bookService.deletecar(b);
        if(result==0){
            strJson = "{\"success\":\"0\"}";
        }else{

            strJson="{\"success\":\"1\"}";
        }

        PrintWriter pw=resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value = "inscar")
    public void inscar(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;
        Car car=new Car();
        
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        String[] bname = map.get("bname");
        String bname_str = URLDecoder.decode(req.getParameter("bname"),"UTF-8");
        bname[0] = bname_str;
        //System.out.println("sex="+sex_str+"addr="+addr_str);
        map.put("bname", bname);
        
        BeanUtils.populate(car,map);

        System.out.println(car);
        int c=bookService.insertcar(car);
        if(c>=0){
            strJson="{\"success\":\""+c+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping(value = "insertorder")
    public void insorder(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String strJson;
        //req.setCharacterEncoding("GBK");//设置参数的编码格式;

        Map<String,String[]> map=req.getParameterMap();
        Order order=new Order();
        BeanUtils.populate(order,map);
        order.setBname("已买书籍……");
        System.out.println(order);
        int c=bookService.insertorder(order);
        System.out.println("直接凯迪拉克"+c);
        if(c>=0){
            strJson="{\"success\":\""+c+"\"}";
        }else{
            strJson = "{\"success\":\"0\"}";
        }
        String a;
        PrintWriter pw= resp.getWriter();
        pw.println(strJson);
    }
    @RequestMapping("/GetOrder")
    public void getorder(HttpServletResponse resp,HttpServletRequest req) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String a=req.getParameter("id");
        int b=Integer.parseInt(a);

        List<Order> Allorder;
        resp.setContentType("text/html; charset=utf-8");
        Allorder=bookService.selectOrder(b);
        System.out.println();
        Gson gson=new Gson();
        String allorder=gson.toJson(Allorder);
        System.out.println(allorder);
        PrintWriter pw=resp.getWriter();
        pw.println(allorder);
    }

    @RequestMapping("/deleteorder")
    public void deleteorder(HttpServletResponse resp,HttpServletRequest req) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String strJson;
        String a=req.getParameter("id");
        int b=Integer.parseInt(a);
        resp.setContentType("text/html; charset=utf-8");
        int result=bookService.deleteorder(b);
        if(result==0){
            strJson = "{\"success\":\"0\"}";
        }else{

            strJson="{\"success\":\"1\"}";
        }

        PrintWriter pw=resp.getWriter();
        pw.println(strJson);
    }

    @RequestMapping("/clearcar")
    public void clearcar(HttpServletResponse resp,HttpServletRequest req) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String strJson;
        String a=req.getParameter("id");
        int b=Integer.parseInt(a);
        resp.setContentType("text/html; charset=utf-8");
        int result=bookService.clearcar(b);
        if(result==0){
            strJson = "{\"success\":\"0\"}";
        }else{

            strJson="{\"success\":\"1\"}";
        }

        PrintWriter pw=resp.getWriter();
        pw.println(strJson);
    }
}
