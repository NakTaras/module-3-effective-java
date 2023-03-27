package com.mentoringprogram.module3.cache;

public interface Cache<K, V> {
  void put(K cacheKey, V cacheValue);
  V get(K cacheKey);
  String getStatistic();
}
