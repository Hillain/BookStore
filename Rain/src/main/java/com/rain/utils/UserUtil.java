package com.rain.utils;

public class UserUtil {
	public static String getRandomUserName(){
		String name = "若水用户";
		int random = 0;
		random = (int)(Math.random()*20210);
		name += String.valueOf(random);
		return name;
	}
}
