package com.rain.service;

import com.rain.pojo.Book;
import com.rain.pojo.Car;
import com.rain.pojo.Category;
import com.rain.pojo.Order;

import java.util.List;

public interface BookService {



    List<Book> selectBookList();

    List<Category> selectBookCate();

    List<Book> selectBookListbyid(String a);

    List<Car> selectCar(int a);

    int deletecar(int b);
    int insertcar(Car car);

    int insertorder(Order order);

    List<Order> selectOrder(int b);

    int deleteorder(int b);

    int clearcar(int b);
}
