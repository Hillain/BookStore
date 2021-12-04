package com.example.bookstore.pojo;

import java.io.Serializable;

public class Book  implements Serializable {
    private int bookid;
    private String bname;
    private String bauthor;
    private String bpublish;
    private Float bprice;
    private int bstock;
    private int bcategory;
    private String binfo;

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", bname='" + bname + '\'' +
                ", bauthor='" + bauthor + '\'' +
                ", bpublish='" + bpublish + '\'' +
                ", bprice=" + bprice +
                ", bstock=" + bstock +
                ", bcategory=" + bcategory +
                ", binfo='" + binfo + '\'' +
                ", burl='" + burl + '\'' +
                '}';
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getBkname() {
        return bname;
    }

    public void setBkname(String bkname) {
        this.bname = bkname;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getBpublish() {
        return bpublish;
    }

    public void setBpublish(String bpublish) {
        this.bpublish = bpublish;
    }

    public Float getBprice() {
        return bprice;
    }

    public void setBprice(Float bprice) {
        this.bprice = bprice;
    }

    public int getBstock() {
        return bstock;
    }

    public void setBstock(int bstock) {
        this.bstock = bstock;
    }

    public int getBcategory() {
        return bcategory;
    }

    public void setBcategory(int bcategory) {
        this.bcategory = bcategory;
    }

    public String getBinfo() {
        return binfo;
    }

    public void setBinfo(String binfo) {
        this.binfo = binfo;
    }

    public String getBurl() {
        return burl;
    }

    public void setBurl(String burl) {
        this.burl = burl;
    }

    private String burl;
}