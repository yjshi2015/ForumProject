package com.baobaotao.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

//����PO��ĸ���
//ʵ��Serializable�ӿڣ��Ա�JVM�������л�POʵ��
public class BaseDomain implements Serializable {

	//ͳһ��tostring����
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
