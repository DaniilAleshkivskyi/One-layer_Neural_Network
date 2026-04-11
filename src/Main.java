import Helpers.DataLoader;
import Helpers.EvaluationMetrics;
import Helpers.TextDivider;
import Models.Perceptron.MyVector;
import Models.Perceptron.Perceptron;
import Models.SingleLayerNeuralNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        var data = DataLoader.loadFromFolder("src/resources_grouped/",80);
        System.in.read();

        HashMap<String, List<MyVector>> trainData = new HashMap<>();
        HashMap<String, List<MyVector>> testData  = new HashMap<>();

        for (var entry : data.entrySet()) {
            trainData.put(entry.getKey(), entry.getValue().get(0));
            testData.put(entry.getKey(),  entry.getValue().get(1));
        }


        List<Perceptron> neurons = new ArrayList<>();
        List<Double> alphas = new ArrayList<>();
        List<Double> betas = new ArrayList<>();

        for (String lang : trainData.keySet()) {
            Perceptron p = new Perceptron(26, 1, 0.1, 0.1,lang);
            neurons.add(p);
            alphas.add(0.01);
            betas.add(0.01);
        }

        System.in.read();
        SingleLayerNeuralNetwork network = new SingleLayerNeuralNetwork(neurons, alphas, betas);
        network.trainLayer(trainData, 50);

        System.out.println("\n===== TEST RESULTS =====\n");
        List<Integer> labels = new ArrayList<>();
        List<Integer> guessed = new ArrayList<>();
        for (var entry : testData.entrySet()) {
            guessed.clear();
            labels.clear();
            String realLang = entry.getKey();
            List<MyVector> vectors = entry.getValue();
            int correct = 0;
            for (MyVector v : vectors) {
                String predicted = network.predict(v);
                labels.add(v.label());
                if (predicted.equals(realLang)) {
                    correct++;
                    guessed.add(1);
                }else  {
                    guessed.add(0);
                }
            }

            double accuracy = (double) correct / vectors.size() * 100;
            System.out.printf("%s: %.2f%% (%d/%d)%n", realLang, accuracy, correct, vectors.size());
            System.out.println(EvaluationMetrics.measureAccuracy(labels,guessed).info());

        }

        System.out.println("\nEnter text for prediction(or '\\x' for exiting):");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("\\x")) break;
            if (input.isBlank()) continue;

            MyVector vector = TextDivider.divide(input, 0);
            String predicted = network.predict(vector);
            System.out.println("Predicted language: " + predicted);
            System.out.println("Enter text:");
        }
    }
}
