package com.mentoringprogram.module3.cache;

public interface Cache<T> {
  void put(T cacheObject);
  T get(T cacheObject);
  String getStatistic();
}
