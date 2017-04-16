package com.baobaotao.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;

//DAO���࣬��װPO�๲ͬ��һЩ���������籣�桢���¡�ɾ��PO�Լ�����ID����PO��
public class BaseDao<T> {

	private Class<T> entityClass;
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	//ͨ�������ȡ����ȷ���ķ�����
	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		entityClass = (Class)params[0];
	}
	
	//����ID����POʵ��
	public T load(Serializable id) {
		return (T)getHibernateTemplate().load(entityClass,id);
	}
	
	//����ID��ȡPOʵ��
	public T get(Serializable id) {
		return (T)getHibernateTemplate().get(entityClass,id);
	}
	
	//��ȡPO�����ж���
	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	//����PO
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}
	
	//ɾ��PO
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}
	
	//����PO
	public void update(T entitye) {
		getHibernateTemplate().update(entitye);
	}
	
	//ִ��HQL��ѯ
	public List find(String hql) {
		return this.getHibernateTemplate().find(hql);
	}
	
	//ִ�д�������HQL��ѯ
	public List find(String hql,Object... params) {
		return this.getHibernateTemplate().find(hql,params);
	}
	
	//���ӳټ��ص�ʵ��POִ�г�ʼ��
	public void initialize(Object entity) {
		this.getHibernateTemplate().initialize(entity);
	}

	//��ҳ��ѯ������ʹ��hql
	public Page pageQuery(String hql, int pageNo, int pageSize, Object...values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start 1");
		//count��ѯ
		String countQueryString = "select count(1) " +removerSelect(removeOrders(hql));
		List countList = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (long) countList.get(0);
		
		if(totalCount < 1) 
			return new Page();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}
	
	//����Qurey����
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for(int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	//ȥ��hql�е�select�ؼ��֣�δ����union�����
	private static String removerSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, "hql : " + hql + "must has a keyword 'from' ");
		return hql.substring(beginPos);
	}
	
	//ȥ��hql�е�order by�Ӿ�
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern pattern = Pattern.compile("order\\s*by[\\W|\\w|\\S|\\s]*",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(hql);
		StringBuffer sBuffer = new StringBuffer();
		while(matcher.find()) {
			matcher.appendReplacement(sBuffer, "");
		}
		matcher.appendTail(sBuffer);
		return sBuffer.toString();
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public Session getSession() {
		return SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory()
				, true);
	}
}
