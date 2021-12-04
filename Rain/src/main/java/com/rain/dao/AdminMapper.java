package com.rain.dao;

import java.util.List;

import com.rain.pojo.Admin;
import com.rain.pojo.Book;
import com.rain.pojo.Category;

public interface AdminMapper {
	public Admin login(Admin admin);
	public List<Category> getAllCategory(); 
	public void cate_add(Category category);
	public void cate_edit(Category category);
	public Category cate_get(int id);
	public void cate_del(int id);
	public List<Book> book_find();
	public void book_add(Book book);
	public void book_edit(Book book);
	public Book book_get(int id);
	public void book_del(int id);
}
