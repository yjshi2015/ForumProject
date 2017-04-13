package com.baobaotao.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

//所有PO类的父类
//实现Serializable接口，以便JVM可以序列化PO实例
public class BaseDomain implements Serializable {

	//统一的tostring方法
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
