package com.mentoringprogram.module3.search;

public class BinarySearch {
  private BinarySearch() {}

  public static <T extends Comparable<T>> int recursiveSearch(T[] array, T value, int lowIndex, int highIndex) {
    if (highIndex >= lowIndex) {
      int midIndex = (highIndex + lowIndex) / 2;
      if (value.equals(array[midIndex])) {
        return midIndex;
      }
      if (array[midIndex].compareTo(value) < 0) {
        return recursiveSearch(array, value, midIndex + 1, highIndex);
      } else {
        return recursiveSearch(array, value, lowIndex, midIndex - 1);
      }
    }
    return -1;
  }

  public static <T extends Comparable<T>> int iterativeSearch(T[] array, T value) {
    int lowIndex = 0;
    int highIndex = array.length - 1;
    while (highIndex - lowIndex > 1) {
      int midIndex = (highIndex + lowIndex) / 2;
      if (array[midIndex].compareTo(value) < 0) {

        lowIndex = midIndex + 1;
      }
      else {
        highIndex = midIndex;
      }
    }
    if (value.equals(array[lowIndex])) {
      return lowIndex;
    }
    if (value.equals(array[highIndex])) {
      return highIndex;
    }
    return -1;
  }
}
