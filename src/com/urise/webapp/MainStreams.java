package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        List<Integer> listInt = Arrays.asList(3, 2, 4, 1, 1, 3, 1);
        int[] integerNumber = {1, 2, 2, 1, 4, 6};
        System.out.println(oddOrEven(listInt));
        System.out.println(minValue(integerNumber));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, ((left, right) -> left * 10 + right));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream()
                .filter(streamNumber -> streamNumber % 2 != sum % 2)
                .collect(Collectors.toList());
    }
}