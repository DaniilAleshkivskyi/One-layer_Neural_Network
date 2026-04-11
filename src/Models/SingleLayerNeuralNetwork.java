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

            List<MyVector> allData = new ArrayList<>();
            for (var entry : inputsAndLabels.entrySet()) {
                int lbl = entry.getKey().equals(p.label) ? 1 : 0;
                for (MyVector v : entry.getValue()) {
                    allData.add(new MyVector(v.values(), lbl));
                }
            }

            p.train(allData, maxEpochs, alpha, beta, true);
        }
    }

    public String predict(MyVector vector) {
        var i = neurons.iterator();
        HashMap<String,Perceptron> nets = new HashMap<>();
        while (i.hasNext()) {
            Perceptron p = i.next();
            int res = p.predict(vector.values());
            if(res == 1){
                nets.put(p.label,p);
            }
        }
        if (nets.isEmpty()){
            return "No prediction found";
        }
        double maxNet = 0;
        Perceptron perceptron = null;
        for (var p : nets.values()){
            if (maxNet < p.lastNet){
                maxNet = p.lastNet;
                perceptron = p;
            }
        }
        return perceptron.label;
    }
}
