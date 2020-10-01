package club.narukara.ann;

import java.util.InputMismatchException;
import java.util.Random;

public class Cell implements Inheritable {
    private final double[] weight;// last one is bias
    private final ActivationFunction activationFunction;

    public double[] getWeight() {
        return weight;
    }

    // inputLength : the number of weight, NOT include bias
    public Cell(int inputLength, ActivationFunction activationFunction) {
        Random random = new Random();
        weight = new double[inputLength + 1];
        for (int i = 0; i <= inputLength; i++) {
            weight[i] = (random.nextDouble() - 0.5) * 2;// range : -1.0 ~ +1.0
        }
        this.activationFunction = activationFunction;
    }

    public Cell(double[] weight, ActivationFunction activationFunction) {
        this.weight = new double[weight.length];
        System.arraycopy(weight, 0, this.weight, 0, weight.length);
        this.activationFunction = activationFunction;
    }

    public double calculate(double[] input) throws InputMismatchException {
        if (input.length != weight.length - 1) {
            throw new InputMismatchException("number of inputs mismatch");
        }
        double output = weight[input.length];// output = bias, notice that (input.length == weight.length - 1)
        for (int i = 0; i < input.length; i++) {
            output += input[i] * weight[i];
        }
        return activationFunction.activation(output);
    }

    public int getInputLength() {
        return weight.length - 1;
    }

    @Override
    public void mutate(double step) {
        Random random = new Random();
        for (int i = 0; i < weight.length; i++) {
            weight[i] += (random.nextDouble() - 0.5) * 2 * step;// range: -step ~ +step
        }
    }

    @Override
    public void rebuild() {
        Random random = new Random();
        for (int i = 0; i < weight.length; i++) {
            weight[i] = (random.nextDouble() - 0.5) * 2;// range : -1.0 ~ +1.0
        }
    }
}
