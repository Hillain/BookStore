package com.rain.service.impl;

import com.rain.dao.BookMapper;

import com.rain.pojo.*;
import com.rain.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;


    @Override
    public List<Book> selectBookList() {
        return bookMapper.selectBookList();
    }

    @Override
    public List<Category> selectBookCate() {
        return bookMapper.selectBookCate();
    }

    @Override
    public List<Book> selectBookListbyid(String a) {
        return bookMapper.selectBookListbyid(a);
    }

    @Override
    public List<Car> selectCar(int a) {
        return bookMapper.selectCar(a);
    }

    @Override
    public int deletecar(int b) {
        return bookMapper.deletecar(b);
    }
    @Override
    public int insertcar(Car car) {
        try {
            return bookMapper.insertcar(car);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int insertorder(Order order) {

            return bookMapper.insertorder(order);

    }

    @Override
    public List<Order> selectOrder(int b) {
      return   bookMapper.selectOrder(b);
    }

    @Override
    public int deleteorder(int b) {
       return bookMapper.deleteorder(b);
    }

    @Override
    public int clearcar(int b) {
        return bookMapper.clearcar(b);
    }

}
