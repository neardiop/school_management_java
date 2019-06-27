package dao;

import java.sql.SQLException;

public interface PersonneDAO<T> {

	/*
	 * create method
	 * 
	 * @param obj
	 * 
	 * @return boolean
	 */
	public abstract boolean create(T obj);

	/*
	 * read method
	 * 
	 * @param id
	 * 
	 * @return T
	 */
	public abstract T read(int id);

	/*
	 * update method
	 * 
	 * @param id
	 * 
	 * @return boolean
	 */
	public abstract boolean update(T obj,int id);

	/*
	 * delete method
	 * 
	 * @param obj
	 * 
	 * @return boolean
	 */
	public abstract boolean delete(T obj);
}
