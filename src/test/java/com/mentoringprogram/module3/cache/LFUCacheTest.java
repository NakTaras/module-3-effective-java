package com.mentoringprogram.module3.cache;

import com.mentoringprogram.module3.cache.impl.LFUCache;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LFUCacheTest {

  @Test
  public void testPutConcurrency() throws InterruptedException {
    int numberOfThreads = 10;
    ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);
    LFUCache<Integer, Integer> cache = new LFUCache<>(100, 60);
    for (int i = 0; i < numberOfThreads; i++) {
      int finalI = i;
      service.execute(() -> {
        for (int j = 0; j < 10; j++) {
          cache.put(finalI * 10 + j, finalI * 10 + j);
        }
        latch.countDown();
      });
    }
    latch.await();
    assertEquals(0, cache.getCacheEvictionsNum());
    Integer cacheKey = ((int) (Math.random() * 100));
    assertEquals(cacheKey, cache.get(cacheKey));
  }

  @Test
  public void testPutWithRemovingLFUCacheConcurrency() throws InterruptedException {
    int numberOfThreads = 10;
    ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);
    LFUCache<Integer, Integer> cache = new LFUCache<>(10, 60);
    for (int i = 0; i < numberOfThreads; i++) {
      int finalI = i;
      service.execute(() -> {
        for (int j = 0; j < 10; j++) {
          cache.put(finalI * 10 + j, finalI * 10 + j);
          if (finalI == 0 && j != 0) {
            cache.get(j);
          }
        }
        latch.countDown();
      });
    }
    latch.await();
    assertEquals(90, cache.getCacheEvictionsNum());
    Integer cacheKey = ((int) (Math.random() * 9) + 1);
    assertEquals(cacheKey, cache.get(cacheKey));
  }

  @Test
  public void testGetConcurrency() throws InterruptedException {
    int numberOfThreads = 10;
    ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);
    LFUCache<Integer, Integer> cache = new LFUCache<>(10, 2);
    for (int i = 0; i < 10; i++) {
      cache.put(i, i);
    }

    for (int i = 0; i < numberOfThreads; i++) {
      service.execute(() -> {
        for (int j = 0; j < 20; j++) {
          cache.get((int) (Math.random() * 10));
          try {
            TimeUnit.SECONDS.sleep(2);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
        latch.countDown();
      });
    }
    latch.await();
  }

  @Test
  public void testGetReturnCacheValue() {
    LFUCache<String, Integer> cache = new LFUCache<>(10, 60);
    List<String> strings = List.of("zero", "one", "two", "three", "four");
    for (int i = 0; i < strings.size(); i++) {
      cache.put(strings.get(i), i);
    }
    assertEquals(Integer.valueOf(2), cache.get("two"));
  }

  @Test
  public void testGetReturnNull() {
    LFUCache<String, Integer> cache = new LFUCache<>(10, 60);
    List<String> strings = List.of("zero", "one", "two", "three", "four");
    for (int i = 0; i < strings.size(); i++) {
      cache.put(strings.get(i), i);
    }
    assertNull(cache.get("five"));
  }

  @Test
  public void testRemovingAfterTimeToLiveRemovedAllCache() throws InterruptedException {
    LFUCache<String, Integer> cache = new LFUCache<>(10, 1);
    List<String> strings = List.of("zero", "one", "two", "three", "four");
    for (int i = 0; i < strings.size(); i++) {
      cache.put(strings.get(i), i);
    }
    TimeUnit.SECONDS.sleep(2);
    assertNull(cache.get("one"));
    assertEquals(5, cache.getCacheEvictionsNum());
  }

  @Test
  public void testRemovingAfterTimeToLiveRemovedAllCacheExceptOne() throws InterruptedException {
    LFUCache<String, Integer> cache = new LFUCache<>(10, 3);
    List<String> strings = List.of("zero", "one", "two", "three", "four");
    for (int i = 0; i < strings.size(); i++) {
      cache.put(strings.get(i), i);
    }
    TimeUnit.SECONDS.sleep(2);
    cache.get("one");
    TimeUnit.SECONDS.sleep(2);
    assertEquals(Integer.valueOf(1), cache.get("one"));
    assertNull(cache.get("two"));
    assertEquals(4, cache.getCacheEvictionsNum());
  }

  @Test
  public void testRemovingLFU() {
    LFUCache<String, Integer> cache = new LFUCache<>(5, 1);
    List<String> strings = List.of("zero", "one", "two", "three", "four");
    for (int i = 0; i < strings.size(); i++) {
      cache.put(strings.get(i), i);
    }
    for (int i = 0; i < strings.size() - 1; i++) {
      cache.get(strings.get(i));
    }
    cache.put("five", 5);
    assertEquals(1, cache.getCacheEvictionsNum());
    assertNull(cache.get("four"));
    assertEquals(Integer.valueOf(5), cache.get("five"));
  }
}
