package com.dream.exerciseSystem.domain.exercise;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
    private final Random random = new Random();

    public static List<String> generateStringListFromJsonObject(JSONArray jsonArray) {
        List<String> values = new ArrayList<>();
        for (Object o : jsonArray) {
            String value = (String) o;
            values.add(value);
        }
        return values;
    }

    public int[] generateIntArray(int start, int end, int num) {
        int[] result = new int[num];
        for (int i = 0; i < num; i++) {
            result[i] = random.nextInt(end - start + 1) + start;
        }

        return result;
    }

    public double generateDouble(double lowerBound, double upperBound) {
        return random.nextDouble() * (upperBound - lowerBound) + lowerBound;
    }
}
