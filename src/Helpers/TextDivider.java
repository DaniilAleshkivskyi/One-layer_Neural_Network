package Helpers;

import Models.Perceptron.MyVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextDivider {
    public static MyVector divide(String inputs,int label) {
        String lettersOnly = inputs.replaceAll("[^\\p{L}]", "").toLowerCase();
        int totalLetters = lettersOnly.length();

        String latinOnly = inputs.replaceAll("[^a-zA-Z]", "").toLowerCase();
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < latinOnly.length(); i++) {
            char c = latinOnly.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        List<Double> values = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            values.add(totalLetters > 0 ? (double) map.getOrDefault(c, 0) / totalLetters : 0.0);
        }

        return new MyVector(values, label);
    }
}
