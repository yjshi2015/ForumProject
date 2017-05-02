package com.baobaotao.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baobaotao.dao.LoginLogDao;
import com.baobaotao.dao.UserDao;
import com.baobaotao.domain.LoginLog;
import com.baobaotao.domain.User;
import com.baobaotao.exception.UserExistException;

/**
 * �û�����������������ѯ�û���ע���û��������û��Ȳ���
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginLogDao loginLogDao;

	
	/**
	 * ע��һ�����û�,����û����Ѿ����ڴ��׳�UserExistException���쳣
	 * @param user 
	 */
	public void register(User user) throws UserExistException{
		User u = this.getUserByUserName(user.getUserName());
		if(u != null){
		    throw new UserExistException("�û����Ѿ�����");
		}else{
		    user.setCredit(100);
            user.setUserType(1);
            userDao.save(user);
		}
	}
	
	/**
     * �����û�
     * @param user 
     */
    public void update(User user){
        userDao.update(user);
    }
	

	   /**
     * �����û���/�����ѯ User����
     * @param userName �û���
     * @return User
     */
    public User getUserByUserName(String userName){
        return userDao.getUserByUserName(userName);
    }
	
	
	/**
	 * ����userId����User����
	 * @param userId
	 * @return
	 */
	public User getUserById(int userId){
		return userDao.get(userId);
	}
	
	/**
	 * ���û��������������û����ܹ���¼
	 * @param userName ����Ŀ���û����û���
	 */
	public void lockUser(String userName){
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_LOCK);
	    userDao.update(user);
	}
	
	/**
	 * ����û�������
	 * @param userName �������Ŀ���û����û���
	 */
	public void unlockUser(String userName){
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_UNLOCK);
		userDao.update(user);
	}
	
	
	/**
	 * �����û���Ϊ������ִ��ģ����ѯ���� 
	 * @param userName ��ѯ�û���
	 * @return �����û���ǰ��ƥ���userName���û�
	 */
	public List<User> queryUserByUserName(String userName){
		return userDao.queryUserByUserName(userName);
	}
	
	/**
	 * ��ȡ�����û�
	 * @return �����û�
	 */
	public List<User> getAllUsers(){
		return userDao.loadAll();
	}
	
	/**
	 * ��½�ɹ�
	 * @param user
	 */
	public void loginSuccess(User user) {
		user.setCredit( 5 + user.getCredit());
		LoginLog loginLog = new LoginLog();
		loginLog.setUser(user);
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(new Date());
        userDao.update(user);
        loginLogDao.save(loginLog);
	}	
	
}
