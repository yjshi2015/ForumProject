package com.baobaotao.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.baobaotao.domain.User;
/**
 * User����Dao
 */
@Repository
public class UserDao extends BaseDao<User> {
	private final String GET_USER_BY_USERNAME = "from User u where u.userName = ?";
	
	private final String QUERY_USER_BY_USERNAME = "from User u where u.userName like ?";
	
    /**
     * �����û�����ѯUser����
     * @param userName �û���
     * @return ��ӦuserName��User������������ڣ�����null��
     */
	public User getUserByUserName(String userName){
	    List<User> users = (List<User>)getHibernateTemplate().find(GET_USER_BY_USERNAME,userName);
	    if (users.size() == 0) {
			return null;
		}else{
			return users.get(0);
		}
    }
	
	/**
	 * �����û���Ϊģ����ѯ��������ѯ������ǰ׺ƥ���User����
	 * @param userName �û�����ѯ����
	 * @return �û���ǰ׺ƥ�������User����
	 */
	public List<User> queryUserByUserName(String userName){
	    return (List<User>)getHibernateTemplate().find(QUERY_USER_BY_USERNAME,userName+"%");
	}

}
