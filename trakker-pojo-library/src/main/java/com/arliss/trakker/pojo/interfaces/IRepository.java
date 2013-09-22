package com.arliss.trakker.pojo.interfaces;

import com.arliss.trakker.pojo.library.Ticket;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/17/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IRepository<T> {
    List<Ticket> getAll();
    void create(T item);
    Boolean contains(T item);
}
