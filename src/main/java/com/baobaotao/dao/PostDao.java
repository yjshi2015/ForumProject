package com.baobaotao.dao;

import org.springframework.stereotype.Repository;

import com.baobaotao.domain.Post;

/**
 * Post��DAO��
 *
 */
@Repository
public class PostDao extends BaseDao<Post> {
	
	protected final String GET_PAGED_POSTS = "from Post where topic.topicId =? order by createTime desc"; 

	protected final String DELETE_TOPIC_POSTS = "delete from Post where topic.topicId=?";
	
	public Page getPagedPosts(int topicId, int pageNo, int pageSize) {
		return pageQuery(GET_PAGED_POSTS,pageNo,pageSize,topicId);
	}
    
	/**
	 * ɾ�������µ���������
	 * @param topicId ����ID
	 */
	public void deleteTopicPosts(int topicId) {
		getHibernateTemplate().bulkUpdate(DELETE_TOPIC_POSTS,topicId);
	}	
}