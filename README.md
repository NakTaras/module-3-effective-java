# module-3-effective-java

## Lectures:

- Programming Foundations: Algorithms

## Books:

- "Effective Java" Joshua Bloch
- “Algorithms” Jeff Ericson
- “Getting Started with Google Guava” Bill Bejeck

## Sites:

- guava cache tutorial
- cache management
- design patterns
- data structures and algorithms 

## Task
Please, complete the following task.

<b> The total mark is 7:  4 points for regular homework and 3 points for the extra mile. </b>

Let's take as the base of our homework "Effective Java" book by Joshua Bloch.

Implement cache service. Cache entries (objects) – simple custom class with one String field. Your cache service should have 2 methods: get and put.

Your cache service should fit next requirements:

- <b>+++</b> Max Size = 100 000;
- <b>+++</b> Eviction policy;
- <b>+++</b> Time-based on last access (5 seconds);
- <b>+++</b> Removal listener;
- <b>+++</b> Just add to log of removed entry;
- <b>+++</b> Give statistic to user;
- <b>+++</b> Average time spent for putting new values into the cache;
- <b>+++</b> Number of cache evictions;
- Support concurrency.

This task should be implemented in two ways:

1. <b>+++</b> Simple Java (2 points) (Strategy: LFU);
2. Guava (1 point) (Strategy: LRU).

Don't forget about good tests, checkstyle and other staff that already included into your build phase.

<b> The total mark for it is 4 points.</b>

Extra-mile:

Implement binary search algorithm:

- recursively (0.25 points)
- iteratively (0.25 points)
- discover implementation effectiveness (using unit tests and benchmarks) and provide description of results in readme.md (discover complexity, provide time comparison, and explore which approach is more suitable in different situations).

1. Implement merge-sort algorithm with desired complexity of O(N log(N)). Provide unit and benchmark tests. (0.5 point)
2. Implement insertion-sort algorithm with desired complexity of O (N^2) in worst case and space complexity O (1). Provide unit and benchmark tests. (0.5 point)
3. Integrate sort implementations into binary search implementation in the most efficient way using appropriate software design and patterns. Use parametrized tests for testing similar cases in different sort implementations. Use suggestions from “Effective java”. (0.5 point + 0.25 for appropriate code style)
4. Implement binary tree bypass algorithm. Become familiar with binary tree as data structure and its implementation\usage in JDK. (0.25 points)