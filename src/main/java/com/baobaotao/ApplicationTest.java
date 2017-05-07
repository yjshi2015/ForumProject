package com.baobaotao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationTest {

	public static void main(String[] args) {
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("com/baobaotao/baobaotao-dao.xml");
		System.out.println(ctx);
	}
}
