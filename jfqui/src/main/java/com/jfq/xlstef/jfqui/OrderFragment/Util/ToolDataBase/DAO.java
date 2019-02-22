package com.jfq.xlstef.jfqui.OrderFragment.Util.ToolDataBase;

import java.util.List;

/**
 * Created by ${胡小丽} on 2019/01/12
 */
public interface DAO<T> {
    List<T> queryAll();
    List<T>  queryAction(String selection, String[] selectionArgs);
    void delite();
    void insert(T t);
    void update(T t);
}