package Helpers;

import Models.ConfusionMatrix;
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
        ConfusionMatrix cM = ConfusionMatrix.getMatrix(realClasses, predictedClasses);
        double p = calculatePrecision(cM);
        double r = calculateRecall(cM);
        double f = calculateF_Measure(p,r);
        return new MetricsModel(p,r,f,String.format("===Measurements===\n%d/%d\nP = %d\nR = %d\nF = %d\n__________________", correct, realClasses.size(),p,r,f));

    }

    private static double calculatePrecision(ConfusionMatrix cM) {
        return  (double) cM.pp() / (cM.pp() + cM.np());
    }
    private static double calculateRecall(ConfusionMatrix cM) {
        return (double)  cM.pp() / (cM.pp() + cM.pn());
    }

    private static double calculateF_Measure(double p,double r) {
        return (2 * p * r)/(p + r);
    }
}
