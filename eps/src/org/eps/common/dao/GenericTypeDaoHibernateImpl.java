package org.eps.common.dao;

import java.io.Serializable;
import java.util.List;

import org.eps.common.util.GenericTypeResolverUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public abstract class GenericTypeDaoHibernateImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericTypeDao<T, PK> {

	private Class<T> entityClass;
	
	public GenericTypeDaoHibernateImpl() {
		entityClass = GenericTypeResolverUtils.resolveTypeArgument(getClass(), GenericTypeDao.class);
	}

	@Override
	public void add(T t) {
		getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	@Override
	public void delete(T t) {
		getHibernateTemplate().delete(t);
	}

	@Override
	public T findByPrimaryKey(PK id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	public void batchDelete(PK[] ids) {
		for (PK id : ids) {
			delete(findByPrimaryKey(id));
		}
	}
	
}
