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
        double a = (double) correct / realClasses.size();
        double p = calculatePrecision(cM);
        double r = calculateRecall(cM);
        double f = calculateF_Measure(p,r);
        return new MetricsModel(a,p,r,f,String.format("===Measurements===\nAccuracy = %f\nPrecision = %f\nRecall = %f\nF_Measurement = %f\n__________________",a,p,r,f));
    }

    private static double calculatePrecision(ConfusionMatrix cM) {
        return  (double) cM.tp() / (cM.tp() + cM.fp());
    }
    private static double calculateRecall(ConfusionMatrix cM) {
        return (double) cM.tp() / (cM.tp() + cM.fn());
    }

    private static double calculateF_Measure(double p,double r) {
        return (2 * p * r)/(p + r);
    }
}
