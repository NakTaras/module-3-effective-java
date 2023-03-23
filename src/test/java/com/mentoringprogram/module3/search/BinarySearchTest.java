package com.mentoringprogram.module3.search;

import org.junit.Assert;
import org.junit.Test;

public class BinarySearchTest {

    Integer[] arr = {1, 2, 4, 9, 15, 36, 41, 92, 111, 264, 657, 853};

    @Test
    public void testRecursiveSearch_ReturnElementPosition() {
        int actualPosition = BinarySearch.recursiveSearch(arr, 41, 0, arr.length - 1);
        int expectedPosition = 6;
        Assert.assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void testRecursiveSearch_CannotFindElement() {
        int actualPosition = BinarySearch.recursiveSearch(arr, 5, 0, arr.length - 1);
        int expectedPosition = -1;
        Assert.assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void testIterativeSearch_ReturnElementPosition() {
        int actualPosition = BinarySearch.iterativeSearch(arr, 41);
        int expectedPosition = 6;
        Assert.assertEquals(expectedPosition, actualPosition);
    }

    @Test
    public void testIterativeSearch_CannotFindElement() {
        int actualPosition = BinarySearch.iterativeSearch(arr, 5);
        int expectedPosition = -1;
        Assert.assertEquals(expectedPosition, actualPosition);
    }
}
