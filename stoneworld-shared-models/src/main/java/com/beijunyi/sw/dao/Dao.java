package com.beijunyi.sw.dao;

import java.util.List;
import java.util.Map;

public interface Dao<T> {

  long count();

  List<T> listAll();

  List<T> listBy(String key, Object value);

  List<T> listBy(Map<String, Object> properties);

  T get(int id);

  T getBy(String key, Object value);

  T getBy(Map<String, Object> properties);

  QueryResult<T> query(QueryRequest request);

  T save(T entry);

  void delete(int id);

  void delete(T entry);

  T create(T entry);

  T update(T entry);


}