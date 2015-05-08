package com.beijunyi.sw.dao;

import java.util.Map;

public final class QueryRequest {
  private final Integer from;
  private final Integer size;
  private final String sortKey;
  private final String sortDir;
  private final Boolean findTotal;
  private final Map<String, String> matchMap;
  private final Map<String, String> containMap;
  private final Map<String, String> gtMap;
  private final Map<String, String> ltMap;

  public QueryRequest(Integer from, Integer size, String sortKey, String sortDir, Boolean findTotal, Map<String, String> matchMap, Map<String, String> containMap, Map<String, String> gtMap, Map<String, String> ltMap) {
    this.from = from;
    this.size = size;
    this.sortKey = sortKey;
    this.sortDir = sortDir;
    this.findTotal = findTotal;
    this.matchMap = matchMap;
    this.containMap = containMap;
    this.gtMap = gtMap;
    this.ltMap = ltMap;
  }

  public Integer getFrom() {
    return from;
  }

  public Integer getSize() {
    return size;
  }

  public String getSortKey() {
    return sortKey;
  }

  public String getSortDir() {
    return sortDir;
  }

  public Boolean getFindTotal() {
    return findTotal;
  }

  public Map<String, String> getMatchMap() {
    return matchMap;
  }

  public Map<String, String> getContainMap() {
    return containMap;
  }

  public Map<String, String> getGtMap() {
    return gtMap;
  }

  public Map<String, String> getLtMap() {
    return ltMap;
  }
}
