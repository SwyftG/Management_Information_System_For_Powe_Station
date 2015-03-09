package org.eps.common.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericTypeDao<T, PK extends Serializable> {

	void add(T t);
	
	void update(T t);

	void delete(T t);

	T findByPrimaryKey(PK id);
	
	List<T> findAll();
	
	void batchDelete(PK[] ids);

}
