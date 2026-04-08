package Models;

import java.util.List;

public record ConfusionMatrix(int pp, int pn, int np, int nn) {

    public static ConfusionMatrix getMatrix(List<Integer> realClasses, List<Integer> predictedClasses) {
        int pp = 0;
        int pn = 0;
        int np = 0;
        int nn = 0;
        var i = realClasses.iterator();
        var j = predictedClasses.iterator();

        while (i.hasNext()) {
            int ansI = i.next();
            int ansJ = j.next();
            if (ansI == ansJ) {
                if(ansI == 1) {
                    pp++;
                }else{
                    nn++;
                }
            }else{
                if(ansI == 1 & ansJ == 0) {
                    pn++;
                }else {
                    pn++;
                }
            }
        }

        return new ConfusionMatrix(pp,pn,np,nn);
    }
}
