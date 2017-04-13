package com.baobaotao.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

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

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
}
