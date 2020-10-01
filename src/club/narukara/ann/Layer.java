package club.narukara.ann;

import java.util.InputMismatchException;

public abstract class Layer implements Inheritable {
    protected int inputLength, outputLength;

    abstract public double[][] getParameter();

    public int getInputLength() {
        return inputLength;
    }

    public int getOutputLength() {
        return outputLength;
    }

    abstract public double[] calculate(double[] input) throws InputMismatchException;

}
