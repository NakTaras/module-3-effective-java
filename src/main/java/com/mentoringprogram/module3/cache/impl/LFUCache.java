package com.mentoringprogram.module3.cache.impl;

import com.mentoringprogram.module3.cache.Cache;
import com.mentoringprogram.module3.cache.ValueWrapper;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LFUCache<K, V> implements Cache<K, V> {

  private final int cacheSize;
  private final int timeToLive;
  private final Map<K, ValueWrapper<V>> cache = new HashMap<>();
  private final List<Long> timesForPutting = new LinkedList<>();
  private int cacheEvictionsNum = 0;

  public LFUCache(int cacheSize, int timeToLive) {
    this.cacheSize = cacheSize;
    this.timeToLive = timeToLive;
  }

  @Override
  public synchronized void put(K cacheKey, V cacheValue) {
    LocalTime startTime = LocalTime.now();
    removeAfterTimeToLive();

    if (cache.size() == cacheSize) {
      K lfuCachedValue = findLFU();
      System.out.println("Cache key " + lfuCachedValue
          + " removed.");
      cache.remove(lfuCachedValue);
      cacheEvictionsNum++;
    }

    ValueWrapper<V> valueWrapper = new ValueWrapper<>(cacheValue);
    cache.put(cacheKey, valueWrapper);
    System.out.println("Cache key " + cacheKey
        + " inserted.");
    LocalTime endTime = LocalTime.now();
    timesForPutting.add(ChronoUnit.MICROS.between(startTime, endTime));
  }

  @Override
  public synchronized V get(K cacheKey) {
    removeAfterTimeToLive();

    if (cache.containsKey(cacheKey)) {
      ValueWrapper<V> valueWrapper = cache.get(cacheKey);
      valueWrapper.incrementFrequency();
      valueWrapper.updateTime();
      System.out.println("Cache key " + cacheKey
          + " used.");
      return valueWrapper.getValue();
    }
    return null;
  }

  @Override
  public String getStatistic() {
    return "Average time spent for putting new values into the cache: " + Double.valueOf(getAverageTimeForPutting()).longValue() + " microseconds\n"
        + "Number of cache evictions: " + getCacheEvictionsNum();
  }

  private K findLFU() {
    K lfuCachedKey = null;
    int minFrequency = Integer.MAX_VALUE;

    for (Map.Entry<K, ValueWrapper<V>> entry : cache.entrySet()) {
      if (entry.getValue().getFrequency() < minFrequency) {
        minFrequency = entry.getValue().getFrequency();
        lfuCachedKey = entry.getKey();
      }
    }

    return lfuCachedKey;
  }

  private void removeAfterTimeToLive() {
    List<K> cachedKeysToRemove = cache.entrySet().stream()
        .filter(cacheEntry -> ChronoUnit.SECONDS.between(cacheEntry.getValue().getUpdateTime(), LocalTime.now()) > timeToLive)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

    for (K cachedKey : cachedKeysToRemove) {
      cache.remove(cachedKey);
      cacheEvictionsNum++;
      System.out.println("Cache key " + cachedKey
          + " removed.");
    }
  }

  public double getAverageTimeForPutting() {
    return timesForPutting.stream().mapToDouble(Long::doubleValue).average().orElse(0);
  }

  public int getCacheEvictionsNum() {
    return cacheEvictionsNum;
  }
}
