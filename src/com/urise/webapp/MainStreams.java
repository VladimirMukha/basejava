package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        List<Integer> listInt = Arrays.asList(3, 2, 4, 2, 1, 1);
        int[] integerNumber = {1, 2, 2, 1, 4, 6};
        MainStreams mainStreams = new MainStreams();
        System.out.println(mainStreams.oddOrEven(listInt));
        System.out.println(mainStreams.minValue(integerNumber));
    }

    private int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((left, right) -> left * 10 + right
        ).getAsInt();
    }

    private List<Integer> oddOrEven(List<Integer> integers) {
        int oddOrEven = integers.stream().mapToInt(Integer::intValue).sum();
        return integers.stream().filter(oddOrEven % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 == 1).collect(Collectors.toList());
    }
}