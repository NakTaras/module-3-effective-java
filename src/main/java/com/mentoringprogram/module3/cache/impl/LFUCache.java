package com.mentoringprogram.module3.cache.impl;

import com.mentoringprogram.module3.cache.Cache;
import com.mentoringprogram.module3.cache.Pair;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LFUCache<T> implements Cache<T> {

  private final int cacheSize;
  private final int timeToLive;
  private final Map<T, Pair> cache = new HashMap<>();
  private final List<Long> timesForPutting = new LinkedList<>();
  private int cacheEvictionsNum = 0;

  public LFUCache(int cacheSize, int timeToLive) {
    this.cacheSize = cacheSize;
    this.timeToLive = timeToLive;
  }

  @Override
  public void put(T cacheObject) {
    LocalTime startTime = LocalTime.now();
    removeAfterTimeToLive();

    if (cache.size() == cacheSize) {
      T lfuCachedValue = findLFU();
      System.out.println("Cache block " + lfuCachedValue
          + " removed.");
      cache.remove(lfuCachedValue);
      cacheEvictionsNum++;
    }

    Pair newPair = new Pair();
    cache.put(cacheObject, newPair);
    System.out.println("Cache block " + cacheObject
        + " inserted.");
    LocalTime endTime = LocalTime.now();
    timesForPutting.add(ChronoUnit.MICROS.between(startTime, endTime));
  }

  @Override
  public T get(T cacheObject) {
    removeAfterTimeToLive();

    if (cache.containsKey(cacheObject)) {
      Pair pair = cache.get(cacheObject);
      pair.incrementFrequency();
      pair.updateTime();
      System.out.println("Cache block " + cacheObject
          + " used.");
    }
    return null;
  }

  @Override
  public String getStatistic() {
    return "Average time spent for putting new values into the cache: " + Double.valueOf(getAverageTimeForPutting()).longValue() + " microseconds\n"
        + "Number of cache evictions: " + getCacheEvictionsNum();
  }

  private T findLFU() {
    T lfuCachedObject = null;
    int minFrequency = Integer.MAX_VALUE;

    for (Map.Entry<T, Pair> entry : cache.entrySet()) {
      if (entry.getValue().getFrequency() < minFrequency) {
        minFrequency = entry.getValue().getFrequency();
        lfuCachedObject = entry.getKey();
      }
    }

    return lfuCachedObject;
  }

  private void removeAfterTimeToLive() {
    List<T> cachedObjectsToRemove = cache.entrySet().stream()
        .filter(cacheEntry -> ChronoUnit.SECONDS.between(cacheEntry.getValue().getUpdateTime(), LocalTime.now()) > timeToLive)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

    for (T cachedObject : cachedObjectsToRemove) {
      cache.remove(cachedObject);
      cacheEvictionsNum++;
      System.out.println("Cache block " + cachedObject
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
