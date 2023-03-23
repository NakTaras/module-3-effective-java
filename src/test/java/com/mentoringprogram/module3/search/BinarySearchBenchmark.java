package com.mentoringprogram.module3.search;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2)
public class BinarySearchBenchmark {

  @Param({"10", "100", "1000", "10000", "100000"})
  int size;
  Integer numberToFind;
  Integer[] arr;

  @Setup
  public void setup() {
    arr = new Integer[size];
    for (int i = 0; i < size; i++) {
      arr[i] = i;
    }
    numberToFind = ((int) (Math.random() * size));
  }

  @Benchmark
  public int testRecursiveSearch() {
    return BinarySearch.recursiveSearch(arr, numberToFind, 0, arr.length - 1);
  }

  @Benchmark
  public void testIterativeSearch() {
    BinarySearch.iterativeSearch(arr, numberToFind);
  }

  public static void main(String[] args) throws IOException, RunnerException {
    org.openjdk.jmh.Main.main(args);
  }

}
