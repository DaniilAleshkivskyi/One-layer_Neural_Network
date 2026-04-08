package Models.Perceptron;

import java.util.List;

public record MyVector(List<Double> values,
                       String label) {
    public MyVector(List<Double> values, String label){
        this.values = values;
        this.label = label;
    }
    public boolean isLabelEqual(MyVector oVector){
        return label.equals(oVector.label);
    }
    public boolean isLabelEqual(String oLabel){
        return label.equals(oLabel);
    }

}
