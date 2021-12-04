package com.rain.pojo;

public class Category {
    private int id;
    private String lx;
    private int state;
    private String descr;
    
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Category(int id, String lx, int state, String descr) {
		super();
		this.id = id;
		this.lx = lx;
		this.state = state;
		this.descr = descr;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", lx=" + lx + ", state=" + state + ", descr=" + descr + "]";
	}
	public Category() {}
}
