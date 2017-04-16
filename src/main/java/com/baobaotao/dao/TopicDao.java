package com.baobaotao.dao;

import org.springframework.stereotype.Repository;

import com.baobaotao.domain.Topic;

@Repository
public class TopicDao extends BaseDao<Topic> {

	private final String GET_BOARD_DIGEST_TOPICS = "from Topic t where t.boardId = ? "
			+ "and digest > 0 order by t.lastPost desc,digest desc ";
	
	private final String GET_PAGED_TOPICS = "from Topic where boardId = ? "
			+ "order by lastPost desc";
	
	private final String QUERY_TOPIC_BY_TITLE = "from Topic where topicTitle like ? "
			+ "order by lastPost desc";
	
	//��ȡ��̳���ĳһҳ�ľ�������������ʱ��;�������������
	public Page getBoardDigestTopics(int boardId, int pageNo, int pageSize) {
		return pageQuery(GET_BOARD_DIGEST_TOPICS, pageNo, pageSize, boardId);
	}
	
	//��ȡ��̳���ĳһҳ����������
	public Page getPagedTopics(int boardId, int pageNo, int pageSize) {
		return pageQuery(GET_PAGED_TOPICS, pageNo, pageSize, boardId);
	}
	
	//��ȡ������������ģ��ƥ���������
	public Page queryTopicByTitle(String title, int pageNo, int pageSize) {
		return pageQuery(QUERY_TOPIC_BY_TITLE, pageNo, pageSize);
	}
}
