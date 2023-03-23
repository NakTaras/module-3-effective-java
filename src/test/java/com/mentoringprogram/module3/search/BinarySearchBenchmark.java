package com.mentoringprogram.module3.search;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BinarySearchBenchmark {

  @State(Scope.Thread)
  public static class MyState {

    @Setup(Level.Trial)
    public void doSetup() {
      sum = 0;
      System.out.println("Do Setup");
    }

    @TearDown(Level.Trial)
    public void doTearDown() {
      System.out.println("Do TearDown");
    }

    public int a = 1;
    public int b = 2;
    public int sum ;
  }

  @Benchmark
  @BenchmarkMode(Mode.All)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public void testMethod(MyState state, Blackhole blackhole) {
    state.sum = state.a + state.b;
    blackhole.consume(state.sum);
  }

  public static void main(String[] args) throws IOException, RunnerException {
    org.openjdk.jmh.Main.main(args);
  }

}
