package Helpers;

import Models.MetricsModel;

import java.util.List;

public class EvaluationMetrics {
    public static MetricsModel measureAccuracy(List<Integer> realClasses,List<Integer> predictedClasses) {
        int correct = 0;
        var i = realClasses.iterator();
        var j = predictedClasses.iterator();
        while (i.hasNext()) {
            if (i.next() == j.next()) {
                correct++;
            }
        }
        return new MetricsModel((double) correct / realClasses.size() * 100 ,String.format("%d/%d", correct, realClasses.size()));

    }
}
