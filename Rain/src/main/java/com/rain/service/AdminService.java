package com.rain.service;

import java.util.List;

import com.rain.pojo.Admin;
import com.rain.pojo.Book;
import com.rain.pojo.Category;

public interface AdminService {
    Admin login(Admin admin);
    List<Category> getAllCategory();
	void cate_add(Category category);
	void cate_edit(Category category);
	Category cate_get(int id);
	void cate_del(int id);
	List<Book> book_find();
	void book_add(Book book);
	void book_edit(Book book);
	Book book_get(int id);
	void book_del(int id);
}
