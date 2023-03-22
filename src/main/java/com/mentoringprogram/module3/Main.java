package com.mentoringprogram.module3;


import com.mentoringprogram.module3.cache.Cache;
import com.mentoringprogram.module3.cache.impl.LFUCache;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    List<String> strings = List.of("one", "two", "three", "four");
    Cache<String> stringCache = new LFUCache<>(4, 3);
    strings.forEach(stringCache::put);
    strings.forEach(stringCache::get);
    stringCache.get("two");
    stringCache.get("two");

    TimeUnit.SECONDS.sleep(2);

    stringCache.get("one");
    stringCache.get("three");

    TimeUnit.SECONDS.sleep(2);

    stringCache.get("one");
    stringCache.get("four");
    stringCache.put("five");
    System.out.println(stringCache.getStatistic());
  }
}
