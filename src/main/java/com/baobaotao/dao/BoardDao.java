package com.baobaotao.dao;

import java.util.Iterator;

import org.springframework.stereotype.Repository;

import com.baobaotao.domain.Board;

//��չbasedao,��ȷ�����͵���δboard
@Repository
public class BoardDao extends BaseDao<Board> {

	protected final String GET_BOARD_NUM = "select count(1) from Board";
	
	//��ȡ��̳�����Ŀ
	public long getBoardNum() {
		Iterator it = getHibernateTemplate().iterate(GET_BOARD_NUM);
		return (long) it.next();
	}
}
