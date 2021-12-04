package com.rain.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.rain.pojo.Admin;
import com.rain.pojo.Book;
import com.rain.pojo.Category;
import com.rain.service.AdminService;
import com.rain.utils.FileUpload;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @RequestMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("123123");
        Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        Admin admin = new Admin();
        BeanUtils.populate(admin, map);
        System.out.println(admin);
        Admin admin2 = adminService.login(admin);
        System.out.println("aaaaaaaaaaaaaaaa");
        if(admin2!=null){
        	System.out.println("sssssssssssssssssssssss");
        	req.getSession().setAttribute("admin", admin2);
        }
        System.out.println("bbbbbbbbbbbbbbbbbbb");
        return "redirect:/admin/index/index.html";
    }
    @RequestMapping("/cate_find")
    public String cate_find(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	List<Category> list = new ArrayList<Category>();
    	list = adminService.getAllCategory();
    	if(list.size()>0){
    		req.getSession().setAttribute("category", list);
    		System.out.println(list.get(0)); 
    		System.out.println(list.get(1)); 
    		System.out.println(list.get(2)); 
    	}
    	req.getSession().setAttribute("order", 0);
        return null;
    }
    @RequestMapping("/cate_add")
    public String cate_add(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("asdasdasdasdasdasdasd");
    	Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        Category category = new Category();
        BeanUtils.populate(category, map);
        adminService.cate_add(category);
    	return null;
    }
    @RequestMapping("/cate_reverse")
    public String cate_reverse(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	List<Category> list = new ArrayList<Category>();
    	list = (List<Category>) req.getSession().getAttribute("category");
    	Collections.reverse(list);
		req.getSession().setAttribute("category", list);
		System.out.println(list.get(0)); 
		System.out.println(list.get(1)); 
		System.out.println(list.get(2)); 
		if((int)req.getSession().getAttribute("order")==1){
			req.getSession().setAttribute("order", 0);
		}else{
			req.getSession().setAttribute("order", 1);
		}
        return null;
    }
    @RequestMapping("/cate_edit")
    public String cate_edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("edit");
    	Map<String,String[]> map=new HashMap<>(req.getParameterMap());
        Category category = new Category();
        System.out.println(category);
        BeanUtils.populate(category, map);
        adminService.cate_edit(category);
    	return null;
    }
    @RequestMapping("/cate_get")
    public String cate_get(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("edit");
    	int id = Integer.parseInt(req.getParameter("id"));
        Category category = adminService.cate_get(id);
        req.getSession().setAttribute("cateinfo", category);
    	return null;
    }
    @RequestMapping("/cate_del")
    public String cate_del(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("cate_del");
    	int id = Integer.parseInt(req.getParameter("id"));
        adminService.cate_del(id);
    	return null;
    }
    
    @RequestMapping("/book_find")
    public String book_find(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("book_find");
        List<Book> books = adminService.book_find();
        List<Category> cates = adminService.getAllCategory();
        if(books.size()>0){
        	req.getSession().setAttribute("books", books);
        }
        req.getSession().setAttribute("cates", cates);
        req.getSession().setAttribute("order_book", 0);
    	return null;
    }
    @RequestMapping("/book_add")
    public String book_add(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("book_add");
    	//转换为复杂类型的request对象,这里不能直接cast,需要先转换为Resolver
        MultipartResolver resolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(req);
    	
        MultipartFile file = multipartRequest.getFile("file");
    	String uri = FileUpload.upload(file,"","D:/prot/Rain/src/main/webapp/img");
    	
        Book book = new Book();
        book.setBname(multipartRequest.getParameter("bname"));
        book.setBcategory(Integer.valueOf(multipartRequest.getParameter("bcategory")));
        book.setBauthor(multipartRequest.getParameter("bauthor"));
        book.setBpublish(multipartRequest.getParameter("bpublish"));
        book.setBprice(Float.valueOf(multipartRequest.getParameter("bprice")));
        book.setBstock(Integer.valueOf(multipartRequest.getParameter("bstock")));
        book.setBinfo((multipartRequest.getParameter("binfo")));
        book.setBurl(uri);
        adminService.book_add(book);
    	return null;
    }
    @RequestMapping("/book_edit")
    public String book_edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("book_edit");
    	//转换为复杂类型的request对象,这里不能直接cast,需要先转换为Resolver
        MultipartResolver resolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(req);
        
    	MultipartFile file = multipartRequest.getFile("file");
    	String uri = FileUpload.upload(file,"","D:/prot/Rain/src/main/webapp/img");
    	
    	
        Book book = new Book();
        book.setBurl(uri);
        System.out.println(book);
        //adminService.book_edit(book);
    	return null;
    }
    @RequestMapping("/book_get")
    public String book_get(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("book_get");
    	int id = Integer.parseInt(req.getParameter("id"));
        Book book = adminService.book_get(id);
        req.getSession().setAttribute("book", book);
    	return null;
    }
    @RequestMapping("/book_del")
    public String book_del(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("book_del");
    	int bookid = Integer.parseInt(req.getParameter("id"));
        adminService.book_del(bookid);
    	return null;
    }
    @RequestMapping("/book_reverse")
    public String book_reverse(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("book_reverse");
    	List<Book> list = new ArrayList<Book>();
    	list = (List<Book>) req.getSession().getAttribute("books");
    	Collections.reverse(list);
		req.getSession().setAttribute("books", list);
		System.out.println(list.get(0)); 
		System.out.println(list.get(1)); 
		System.out.println(list.get(2)); 
		if((int)req.getSession().getAttribute("order_book")==1){
			req.getSession().setAttribute("order_book", 0);
		}else{
			req.getSession().setAttribute("order_book", 1);
		}
        return null;
    }
    @RequestMapping("/book_delAll")
    public String book_delAll(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
    	System.out.println("book_delAll");
    	String id = req.getParameter("id");
    	String[] split = id.split(",");
    	for (int i = 0; i < split.length-1; i++) {
			adminService.book_del(Integer.valueOf(split[i]));
			System.out.println("删除了"+split[i]);
		}
        return null;
    }
}
