package com.mentoringprogram.module3.cache;

import java.time.LocalTime;

public class ValueWrapper<V> {
  private int frequency;
  private LocalTime updateTime;
  private final V value;

  public ValueWrapper(V value) {
    this.value = value;
    this.frequency = 1;
    this.updateTime = LocalTime.now();
  }

  public V getValue() {
    return value;
  }

  public int getFrequency() {
    return frequency;
  }

  public void incrementFrequency() {
    this.frequency++;
  }

  public LocalTime getUpdateTime() {
    return updateTime;
  }

  public void updateTime() {
    this.updateTime = LocalTime.now();
  }
}
