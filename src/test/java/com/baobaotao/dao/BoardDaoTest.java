package com.baobaotao.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import com.baobaotao.domain.Board;
import com.baobaotao.test.dataset.util.XlsDataSetBeanFactory;

public class BoardDaoTest extends BaseDaoTest{

	//ע����̳���Dao
	@SpringBean("boardDao")
	private BoardDao boardDao;

	//��Ӳ��԰��
	@Test
	@ExpectedDataSet("BaobaoTao.ExpectedBoards.xls")
	public void addBoard() throws Exception {

		//ͨ��xlsdatasetbeanfactory���ݼ��󶨹�������ʵ�����
		List<Board> boards = XlsDataSetBeanFactory.createBeans(BoardDaoTest.class, "BaobaoTao.SaveBoards.xls", "t_board", Board.class);

		//�־û�boardʵ��
		for(Board board : boards) {
			boardDao.save(board);
		}
	}

	/**
	 * ɾ��һ�����
	 * @param boardId
	 */

	@Test
	@DataSet("BaobaoTao.Boards.xls")//׼������ 
	@ExpectedDataSet("BaobaoTao.ExpectedBoards.xls")
	public void removeBoard(){
		Board board = boardDao.get(7);
		boardDao.remove(board);
	}

	@Test
	@DataSet("BaobaoTao.Boards.xls")//׼������ 
	public void getBoard(){
		Board board = boardDao.load(1);
		assertNotNull(board);
		assertThat(board.getBoardName(), Matchers.containsString("����"));
	}
}
