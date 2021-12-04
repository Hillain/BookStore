package com.example.bookstore.pojo;

public class Category {
    private int id;
    private String lx;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", lx='" + lx + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getlx() {
        return lx;
    }

    public void setlx(String lx) {
        this.lx = lx;
    }
}
