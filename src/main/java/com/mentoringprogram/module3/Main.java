package com.mentoringprogram.module3;


import com.mentoringprogram.module3.cache.Cache;
import com.mentoringprogram.module3.cache.impl.LFUCache;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    List<String> strings = List.of("zero", "one", "two", "three", "four");
    Cache<String, Integer> stringCache = new LFUCache<>(4, 3);
    for (int i = 0; i < strings.size(); i++) {
      stringCache.put(strings.get(i), i);
    }
    strings.forEach(stringCache::get);
    System.out.println("Value gotten from cache: " + stringCache.get("two"));
    System.out.println("Value gotten from cache: " + stringCache.get("two"));

    TimeUnit.SECONDS.sleep(2);

    System.out.println("Value gotten from cache: " + stringCache.get("one"));
    System.out.println("Value gotten from cache: " + stringCache.get("three"));

    TimeUnit.SECONDS.sleep(2);

    System.out.println("Value gotten from cache: " + stringCache.get("one"));
    System.out.println("Value gotten from cache: " + stringCache.get("four"));
    stringCache.put("five", 5);
    System.out.println(stringCache.getStatistic());


//    Integer[] arr = {1, 2, 4, 9, 15, 36, 41, 92, 111, 264, 657, 853};
//    System.out.println(BinarySearch.recursiveSearch(arr, 41, 0, arr.length - 1));
//    System.out.println(arr[BinarySearch.recursiveSearch(arr, 41, 0, arr.length - 1)]);


  }
}
