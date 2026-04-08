package Models.Perceptron;

import Helpers.EvaluationMetrics;
import Models.MetricsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {

    private int dimension;
    private double[] weights;
    private double threshold;
    private double alpha;
    private double beta;
    public String label;
    public double lastNet;
    private MetricsModel metricsModel;

    public Perceptron(int dimension, double threshold, double alpha, double beta) {
        this.dimension = dimension;
        this.threshold = threshold;
        this.alpha = alpha;
        this.beta = beta;
        Random rand = new Random();
        this.weights = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            this.weights[i] = rand.nextDouble(-1,1);
        }
    }
    public Perceptron(int dimension, double threshold, double alpha, double beta,String label){
        this.dimension = dimension;
        this.threshold = threshold;
        this.alpha = alpha;
        this.beta = beta;
        Random rand = new Random();
        this.weights = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            this.weights[i] = rand.nextDouble(-1,1);
        }
        this.label = label;
    }

    //net = w * input - threshold
    public int predict(List<Double> values) {
        double net = 0.0;
        var iter = values.iterator();
        for (int i = 0; i < weights.length; i++) {
            net += weights[i] * iter.next();
        }
        net -= threshold;
        lastNet = net;
        return net >= 0 ? 1 : 0;
    }

    public List<Double> train(List<MyVector> vectors, int epochs, Double learningRate,Double beta, boolean showResults) {
        if (learningRate == null) {
            learningRate = alpha;
        }

        boolean mistake = true;
        int epochsCount = 0;
        List<Integer> guessed = new ArrayList<>();
        List<Double> accuracyHistory = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();
        while (mistake && epochsCount < epochs) {
            guessed.clear();
            mistake = false;
            var iter = vectors.iterator();
            for (int i = 0; i < vectors.size(); i++) {
                MyVector vector = iter.next();
                labels.add(vector.label());
                List<Double> values = vector.values();
                int net = predict(values);
                int error = vector.label() - net;
                guessed.add(net);

                if (error != 0) {
                    mistake = true;
                    var valuesIter = values.iterator();
                    for (int j = 0; j < weights.length; j++) {
                        weights[j] = weights[j] + error * learningRate * valuesIter.next();
                    }
                    threshold -= error * beta;
                }
            }

            if (showResults) {
                System.out.print("Weights: [");
                for (int i = 0; i < weights.length; i++) {
                    System.out.printf("%.4f%s", weights[i], i < weights.length - 1 ? ", " : "");
                }
                System.out.println("]");
            }

            epochsCount++;

            MetricsModel result = EvaluationMetrics.measureAccuracy(guessed, labels);
            accuracyHistory.add(result.precision());

            if (showResults) {
                System.out.printf("Epoch %d: %.1f%%%n", epochsCount,result.precision());
            }

            if (!mistake) break;
        }

        return accuracyHistory;
    }

    public List<Double> train(List<MyVector> vectors, int epochs, Double learningRate, boolean showResults) {
        return train(vectors, epochs, learningRate, beta, showResults);
    }
}