package com.baobaotao.service;

import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.junit.Before;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import com.baobaotao.domain.Board;
import com.baobaotao.domain.Topic;
import com.baobaotao.domain.User;
import com.baobaotao.test.dataset.util.XlsDataSetBeanFactory;

public class ForumServiceTest extends BaseServiceTest {

	@SpringBean("forumService")
	private ForumService forumService;
	@SpringBean("userService")
	private UserService userService;
	
	//在测试初始化时，消除hibernate二级缓存，避免影响测试
	@Before
	public void init() {
		SessionFactory sf = hibernateTemplate.getSessionFactory();
		Map<String, CollectionMetadata> roleMap = sf.getAllCollectionMetadata();
		for(String roleName : roleMap.keySet()) {
			sf.evictCollection(roleName);
		}
		Map<String, ClassMetadata> entityMap = sf.getAllClassMetadata();
		for(String entityName : entityMap.keySet()) {
			sf.evictEntity(entityName);
		}
		sf.evictQueries();
	}
	
	//测试新增版块功能
	@Test
	@DataSet("BaobaoTao.DataSet.xls")
	public void addBoard() throws Exception {
		Board board = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"BaobaoTao.DataSet.xls", "t_board", Board.class);
		forumService.addBoard(board);
		Board boardDb = forumService.getBoardById(board.getBoardid());
		assertThat(boardDb.getBoardName(),equalTo("育儿"));
	}
	
	//测试新增一个主题帖子
	@Test
	@DataSet("BaobaoTao.DataSet.xls")
	public void addTopic() throws Exception {
		Topic topic = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"BaobaoTao.DataSet.xls", "t_topic", Topic.class);
		User user = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
				"BaobaoTao.DataSet.xls", "t_user", User.class);
		topic.setUser(user);
		forumService.addTopic(topic);
		Board boardDb = forumService.getBoardById(1);
		User userDb = userService.getUserByUserName("tom");
		assertThat(boardDb.getTopicNum(), is(1));
		assertThat(userDb.getCredit(), is(110));
		assertThat(topic.getTopicId(), greaterThan(0));
	}
}
