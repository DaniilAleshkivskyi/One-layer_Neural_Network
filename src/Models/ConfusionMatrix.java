package Models;

import java.util.List;

public record ConfusionMatrix(int tp, int tn, int fp, int fn) {

    public static ConfusionMatrix getMatrix(List<Integer> realClasses, List<Integer> predictedClasses) {
        int tp = 0;
        int tn = 0;
        int fp = 0;
        int fn = 0;
        var i = realClasses.iterator();
        var j = predictedClasses.iterator();

        while (i.hasNext()) {
            int ansI = i.next();
            int ansJ = j.next();
            if (ansI == ansJ) {
                if(ansI == 1) {
                    tp++;
                }else{
                    tn++;
                }
            }else{
                if(ansI == 1 && ansJ == 0) {
                    fn++;
                }else {
                    fp++;
                }
            }
        }

        return new ConfusionMatrix(tp, tn, fp, fn);
    }
}
