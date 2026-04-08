package Models;

import Models.Perceptron.MyVector;
import Models.Perceptron.Perceptron;

import java.util.*;

public record SingleLayerNeuralNetwork(List<Perceptron> neurons,
                                       List<Double> alphas,
                                       List<Double> betas) {

    public void trainLayer(List<MyVector> inputsAndLabels) {
        trainLayer(inputsAndLabels,50);
    }

    public void trainLayer(List<MyVector> inputsAndLabels,int maxEpochs) {
        var i = neurons.iterator();
        var a = alphas.iterator();
        var b = betas.iterator();
        while (i.hasNext()) {
            Perceptron p = i.next();
            double alpha = a.next();
            double beta = b.next();
            p.train(inputsAndLabels,maxEpochs,alpha,beta,true);
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
