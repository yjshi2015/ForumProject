package com.baobaotao.dao;

import java.util.Iterator;

import org.springframework.stereotype.Repository;

import com.baobaotao.domain.Board;

//扩展basedao,并确定泛型的类未board
@Repository
public class BoardDao extends BaseDao<Board> {

	protected final String GET_BOARD_NUM = "select count(1) from Board";
	
	//获取论坛版块数目
	public long getBoardNum() {
		Iterator it = getHibernateTemplate().iterate(GET_BOARD_NUM);
		return (long) it.next();
	}
}
