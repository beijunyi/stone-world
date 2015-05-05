package com.beijunyi.sw.dao;

import java.util.List;

public final class QueryResult<T> {
  private final long total;
  private final List<T> data;

  public QueryResult(long total, List<T> data) {
    this.total = total;
    this.data = data;
  }

  public long getTotal() {
    return total;
  }

  public List<T> getData() {
    return data;
  }
}
