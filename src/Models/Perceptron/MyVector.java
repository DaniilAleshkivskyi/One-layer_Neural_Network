package Models.Perceptron;

import java.util.List;

public record MyVector(List<Double> values,
                       int label) {
    public MyVector(List<Double> values, int label){
        this.values = values;
        this.label = label;
    }
    public boolean isLabelEqual(MyVector oVector){
        return label == oVector.label;
    }

}
