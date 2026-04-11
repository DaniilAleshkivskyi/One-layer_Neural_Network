package Models;

import Models.Perceptron.MyVector;
import Models.Perceptron.Perceptron;

import java.util.*;

public record SingleLayerNeuralNetwork(List<Perceptron> neurons,
                                       List<Double> alphas,
                                       List<Double> betas) {

    public void trainLayer(HashMap<String,List<MyVector>> inputsAndLabels) {
        trainLayer(inputsAndLabels,50);
    }

    public void trainLayer(HashMap<String, List<MyVector>> inputsAndLabels, int maxEpochs) {
        var a = alphas.iterator();
        var b = betas.iterator();
        for (Perceptron p : neurons) {
            double alpha = a.next();
            double beta = b.next();
            System.out.println("===============\n===============\n===============\nPERCEPTRON " + p.name + "\n===============\n===============\n===============\n");
            List<MyVector> allData = new ArrayList<>();
            for (var entry : inputsAndLabels.entrySet()) {
                int lbl = entry.getKey().equals(p.name) ? 1 : 0;
                for (MyVector v : entry.getValue()) {
                    allData.add(new MyVector(v.values(), lbl));
                }
            }

            p.train(allData, maxEpochs, alpha, beta, true);
        }
    }

    public String predict(MyVector vector) {
        HashMap<String, Perceptron> activated = new HashMap<>();

        for (Perceptron p : neurons) {
            int res = p.predict(vector.values());
            if (res == 1) {
                activated.put(p.name, p);
            }
        }
        Collection<Perceptron> candidates = activated.isEmpty() ? neurons : activated.values();

        double maxNet = Double.NEGATIVE_INFINITY;
        Perceptron best = null;
        for (Perceptron p : candidates) {
            if (p.lastNet > maxNet) {
                maxNet = p.lastNet;
                best = p;
            }
        }
        return best.name;
    }
}
