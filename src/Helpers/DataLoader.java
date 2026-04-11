package Helpers;

import Models.Perceptron.MyVector;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class DataLoader {

    public static HashMap<String, List<List<MyVector>>> loadFromFolder(String folderPath,int trainPercentage) throws IOException {
        HashMap<String, List<List<MyVector>>> result = new HashMap<>();
        File folder = new File(folderPath);
        System.out.println("----Dividing Info----");
        for (File file : folder.listFiles()) {
            if (!file.isFile()  || !file.getName().contains("txt")) continue;
            String label = file.getName().replace(".txt", "");
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            Collections.shuffle(lines);

            int splitIndex = (int) (lines.size() * (trainPercentage / 100.0));
            List<String> trainLines = lines.subList(0, splitIndex);
            List<String> testLines  = lines.subList(splitIndex, lines.size());

            List<MyVector> trainVectors = new ArrayList<>();
            List<MyVector> testVectors  = new ArrayList<>();

            for (String line : trainLines) {
                if (!line.isBlank()) trainVectors.add(TextDivider.divide(line, 1));
            }
            for (String line : testLines) {
                if (!line.isBlank()) testVectors.add(TextDivider.divide(line, 1));
            }

            System.out.println("-----------------------\nLang : " + label);
            System.out.println("Total Vectors : " + (trainVectors.size() + testVectors.size()));
            System.out.println("Train Vectors : " + trainVectors.size());
            System.out.println("Test Vectors : " + testVectors.size());
            System.out.println(folderPath + " - " + file.getName());

            result.put(label, List.of(trainVectors, testVectors));
        }


        return result;
    }
}