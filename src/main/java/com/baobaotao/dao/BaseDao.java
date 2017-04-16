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

//DAO基类，封装PO类共同的一些操作，比如保存、更新、删除PO以及更加ID加载PO等
public class BaseDao<T> {

	private Class<T> entityClass;
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	//通过反射获取子类确定的泛型类
	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		entityClass = (Class)params[0];
	}
	
	//根据ID加载PO实例
	public T load(Serializable id) {
		return (T)getHibernateTemplate().load(entityClass,id);
	}
	
	//根据ID获取PO实例
	public T get(Serializable id) {
		return (T)getHibernateTemplate().get(entityClass,id);
	}
	
	//获取PO的所有对象
	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	//保存PO
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}
	
	//删除PO
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}
	
	//更改PO
	public void update(T entitye) {
		getHibernateTemplate().update(entitye);
	}
	
	//执行HQL查询
	public List find(String hql) {
		return this.getHibernateTemplate().find(hql);
	}
	
	//执行带参数的HQL查询
	public List find(String hql,Object... params) {
		return this.getHibernateTemplate().find(hql,params);
	}
	
	//对延迟加载的实例PO执行初始化
	public void initialize(Object entity) {
		this.getHibernateTemplate().initialize(entity);
	}

	//分页查询函数，使用hql
	public Page pageQuery(String hql, int pageNo, int pageSize, Object...values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start 1");
		//count查询
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
	
	//创建Qurey对象
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for(int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	//去除hql中的select关键字，未考虑union的情况
	private static String removerSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, "hql : " + hql + "must has a keyword 'from' ");
		return hql.substring(beginPos);
	}
	
	//去除hql中的order by子句
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
