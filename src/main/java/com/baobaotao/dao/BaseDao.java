package com.baobaotao.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

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

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
}
