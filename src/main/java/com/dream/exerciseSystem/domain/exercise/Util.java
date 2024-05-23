package com.dream.exerciseSystem.domain.exercise;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.*;

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

    public static HashMap<String, String> generateHashMapFromJsonObject(JSONObject jsonObject){
        HashMap<String, String> hashMap = new HashMap<>();
        Iterator keys = jsonObject.keys();
        while(keys.hasNext()){
            String key =  (String) keys.next();
            String value = jsonObject.getString(key);
            hashMap.put(key, value);
        }
        return hashMap;
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

    public float generateFloat(float lowerBound, float upperBound) {
        return random.nextFloat() * (upperBound - lowerBound) + lowerBound;
    }

    public long generateLong(long lowerBound, long upperBound) {
        return random.nextLong() * (upperBound - lowerBound) + lowerBound;
    }
}
