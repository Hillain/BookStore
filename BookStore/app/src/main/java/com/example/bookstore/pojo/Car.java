package com.example.bookstore.pojo;



public class Car {
    private int id;
    private int bid;
    private String bname;
    private String bnumber;
    private String total;
    private String burl;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", bid=" + bid +
                ", bname='" + bname + '\'' +
                ", bnumber='" + bnumber + '\'' +
                ", total='" + total + '\'' +
                ", burl='" + burl + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBnumber() {
        return bnumber;
    }

    public void setBnumber(String bnumber) {
        this.bnumber = bnumber;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBurl() {
        return burl;
    }

    public void setBurl(String burl) {
        this.burl = burl;
    }
}
