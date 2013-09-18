package com.arliss.trakker.pojo.interfaces;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/17/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IRepository<T> {
    T getAll();
    void create(T item);
}
