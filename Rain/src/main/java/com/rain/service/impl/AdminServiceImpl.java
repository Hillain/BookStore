package com.rain.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.rain.dao.AdminMapper;
import com.rain.pojo.Admin;
import com.rain.pojo.Book;
import com.rain.pojo.Category;
import com.rain.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Resource
	private AdminMapper dao;
	@Override
	public Admin login(Admin admin) {
		return dao.login(admin);
	}
	@Override
	public List<Category> getAllCategory() {
		return dao.getAllCategory();
	}
	@Override
	public void cate_add(Category category) {
		dao.cate_add(category);
	}
	@Override
	public void cate_edit(Category category) {
		dao.cate_edit(category);
	}
	@Override
	public Category cate_get(int id) {
		return dao.cate_get(id);
	}
	@Override
	public void cate_del(int id) {
		dao.cate_del(id);
	}
	@Override
	public List<Book> book_find() {
		return dao.book_find();
	}
	@Override
	public void book_add(Book book) {
		dao.book_add(book);
	}
	@Override
	public void book_edit(Book book) {
		dao.book_edit(book);
	}
	@Override
	public Book book_get(int id) {
		return dao.book_get(id);
	}
	@Override
	public void book_del(int id) {
		dao.book_del(id);
	}

}
