package Helpers;

import Models.Perceptron.MyVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class TextDivider {
    public static MyVector divide(String inputs, int label) {
        String latinOnly = inputs.toLowerCase().replaceAll("[^a-z]", "");
        int totalLatinLetters = latinOnly.length();

        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : latinOnly.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        List<Double> values = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            double probability = (totalLatinLetters > 0) ? (double) map.getOrDefault(c, 0) / totalLatinLetters : 0.0;
            values.add(probability);
        }
        return new MyVector(values, label);
    }
}