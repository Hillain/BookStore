package com.rain.pojo;

public class User {
    /**用户ID*/
    private int id;
    /**账号*/
    private String username;
    /**用户名*/
    private String nickname;
    /**密码*/
    private String password;
    /**电话*/
    private String pnumber;
    /**性别*/
    private String sex;
    /**地址*/
    private String addr;
    /**邮箱*/
    private String email;
    /**个性签名*/
    private String sign;
  
    public User() {
    }
    
	public User(int id, String username, String nickname, String password, String pnumber, String sex, String addr,
			String email, String sign) {
		super();
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.pnumber = pnumber;
		this.sex = sex;
		this.addr = addr;
		this.email = email;
		this.sign = sign;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPnumber() {
		return pnumber;
	}

	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", nickname=" + nickname + ", password=" + password
				+ ", pnumber=" + pnumber + ", sex=" + sex + ", addr=" + addr + ", email=" + email + ", sign=" + sign
				+ "]";
	}
}
