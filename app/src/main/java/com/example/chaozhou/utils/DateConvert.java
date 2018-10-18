package com.example.chaozhou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//转换时间的工具类
public class DateConvert {
	
	public static Date toDate(String source) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpleDateFormat.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = simpleDateFormat.format(date);
		return time;
	}
	
	public static void main(String[] args) throws ParseException {
		DateConvert convert = new DateConvert();
		Date date = convert.toDate("2018-03-31 20:44:25");
		String time = convert.toString(new Date());
		System.out.println(time);
		System.out.println(date.toString());
	}

}
