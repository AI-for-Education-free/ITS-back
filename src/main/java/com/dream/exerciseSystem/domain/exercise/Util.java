package com.dream.exerciseSystem.domain.exercise;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<String> generateStringListFromJsonObject(JSONArray jsonArray) {
        List<String> values = new ArrayList<>();
        for (Object o : jsonArray) {
            String value = (String) o;
            values.add(value);
        }
        return values;
    }
}
