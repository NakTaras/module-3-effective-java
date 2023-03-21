package com.mentoringprogram.module3.cache;

public interface Cache<T> {
  T put(T t);
  T get();
}
