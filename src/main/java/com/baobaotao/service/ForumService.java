package com.baobaotao.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baobaotao.dao.*;
import com.baobaotao.domain.*;

@Service
public class ForumService {
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private PostDao postDao;
	
	

	/**
	 * ����һ����������,�û����ּ�10����̳��������������1
	 * @param topic
	 */
	public void addTopic(Topic topic) {
		Board board = (Board) boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() + 1);	
		topicDao.save(topic);
		//topicDao.getHibernateTemplate().flush();
		
		topic.getMainPost().setTopic(topic);
		MainPost post = topic.getMainPost();
		post.setCreateTime(new Date());
		post.setUser(topic.getUser());
		post.setPostTitle(topic.getTopicTitle());
		post.setBoardId(topic.getBoardId());
		postDao.save(topic.getMainPost());
		
		User user = topic.getUser();	
		user.setCredit(user.getCredit() + 10);
		userDao.update(user);
	}
	
    
	/**
	 * ɾ��һ�����������û����ּ�50����̳�������������1��ɾ��
	 * ���������й��������ӡ�
	 * @param topicId Ҫɾ����������ID
	 */
	public void removeTopic(int topicId) {   
		Topic topic = topicDao.get(topicId);

		// ����̳��������������1
		Board board = boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() - 1);

		// ������������û��۳�50����
		User user = topic.getUser();
		user.setCredit(user.getCredit() - 50);

		// ɾ���������������������
		topicDao.remove(topic);
		postDao.deleteTopicPosts(topicId);
	}
	
	/**
	 * ���һ���ظ����ӣ��û����ּ�5�֣��������ӻظ�����1���������ظ�ʱ��
	 * @param post
	 */
	public void addPost(Post post){
		postDao.save(post);
		
		User user = post.getUser();
		user.setCredit(user.getCredit() + 5);
		userDao.update(user);
		
		Topic topic = topicDao.get(post.getTopic().getTopicId());
		topic.setReplies(topic.getReplies() + 1);
		topic.setLastPost(new Date());
		//topic����Hibernate�ܹ�״̬��������ʾ����
		//topicDao.update(topic);
	}
	
	/**
	 * ɾ��һ���ظ������ӣ�����ظ����ӵ��û����ּ�20���������Ļظ�����1
	 * @param postId
	 */
	public void removePost(int postId){
		Post post = postDao.get(postId);
		postDao.remove(post);
		
		Topic topic = topicDao.get(post.getTopic().getTopicId());
		topic.setReplies(topic.getReplies() - 1);
		
		User user =post.getUser();
		user.setCredit(user.getCredit() - 20);
		
		//topic����Hibernate�ܹ�״̬��������ʾ����
		//topicDao.update(topic);
		//userDao.update(user);
	}
	
	

	/**
	 * ����һ���µ���̳���
	 * 
	 * @param board
	 */
	public void addBoard(Board board) {
		boardDao.save(board);
	}
	
	/**
	 * ɾ��һ�����
	 * @param boardId
	 */
	public void removeBoard(int boardId){
		Board board = boardDao.get(boardId);
		boardDao.remove(board);
	}
	
	/**
	 * ��������Ϊ����������
	 * @param topicId ������Ŀ��������ID
	 * @param digest �������� ��ѡ��ֵΪ1��2��3
	 */
	public void makeDigestTopic(int topicId){
		Topic topic = topicDao.get(topicId);
		topic.setDigest(Topic.DIGEST_TOPIC);
		User user = topic.getUser();
		user.setCredit(user.getCredit() + 100);
		//topic ����Hibernate�ܹ�״̬��������ʾ����
		//topicDao.update(topic);
		//userDao.update(user);
	}
	
    /**
     * ��ȡ���е���̳���
     * @return
     */
    public List<Board> getAllBoards(){
        return boardDao.loadAll();
    }	
	
	/**
	 * ��ȡ��̳���ĳһҳ�������������ظ�ʱ�併������
	 * @param boardId
	 * @return
	 */
    public Page getPagedTopics(int boardId,int pageNo,int pageSize){
		return topicDao.getPagedTopics(boardId,pageNo,pageSize);
    }
    
    /**
     * ��ȡͬ����ÿһҳ���ӣ������ظ�ʱ�併������
     * @param boardId
     * @return
     */
    public Page getPagedPosts(int topicId,int pageNo,int pageSize){
        return postDao.getPagedPosts(topicId,pageNo,pageSize);
    }    
    

	/**
	 * ���ҳ����а����������title��������
	 * 
	 * @param title
	 *            �����ѯ����
	 * @return �������title��������
	 */
	public Page queryTopicByTitle(String title,int pageNo,int pageSize) {
		return topicDao.queryTopicByTitle(title,pageNo,pageSize);
	}
	
	/**
	 * ����boardId��ȡBoard����
	 * 
	 * @param boardId
	 */
	public Board getBoardById(int boardId) {
		return boardDao.get(boardId);
	}

	/**
	 * ����topicId��ȡTopic����
	 * @param topicId
	 * @return Topic
	 */
	public Topic getTopicByTopicId(int topicId) {
		return topicDao.get(topicId);
	}
	
	/**
	 * ��ȡ�ظ����ӵĶ���
	 * @param postId
	 * @return �ظ����ӵĶ���
	 */
	public Post getPostByPostId(int postId){
		return postDao.get(postId);
	}
    
	/**
	 * ���û���Ϊ��̳���Ĺ���Ա
	 * @param boardId  ��̳���ID
	 * @param userName ��Ϊ��̳������û���
	 */
	public void addBoardManager(int boardId,String userName){
	   	User user = userDao.getUserByUserName(userName);
	   	if(user == null){
	   		throw new RuntimeException("�û���Ϊ"+userName+"���û������ڡ�");
	   	}else{
            Board board = boardDao.get(boardId);
            user.getManBoards().add(board);
            userDao.update(user);
	   	}
	}
	
	/**
	 * ����������
	 * @param topic
	 */
	public void updateTopic(Topic topic){
		topicDao.update(topic);
	}
	
	/**
	 * ���Ļظ����ӵ�����
	 * @param post
	 */
	public void updatePost(Post post){
		postDao.update(post);
	}
	
}
