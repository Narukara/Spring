package club.narukara.ann;

import java.util.InputMismatchException;

public class StandardLayer extends Layer {
    private final Cell[] cells;

    public StandardLayer(Cell[] cells) {
        inputLength = cells[0].getInputLength();
        outputLength = cells.length;
        this.cells = cells;
    }

    public StandardLayer(int inputLength, int num, ActivationFunction activationFunction) {
        this.inputLength = inputLength;
        outputLength = num;
        cells = new Cell[num];
        for (int i = 0; i < num; i++) {
            cells[i] = new Cell(inputLength, activationFunction);
        }
    }

    @Override
    public double[][] getParameter() {
        double[][] parameter = new double[outputLength][];
        for (int i = 0; i < outputLength; i++) {
            parameter[i] = cells[i].getWeight();
        }
        return parameter;
    }

    public double[] calculate(double[] input) throws InputMismatchException {
        double[] output = new double[outputLength];
        for (int i = 0; i < outputLength; i++) {
            output[i] = cells[i].calculate(input);
        }
        return output;
    }

    @Override
    public void mutate(double step) {
        for (Cell cell : cells) {
            cell.mutate(step);
        }
    }

    @Override
    public void rebuild() {
        for (Cell cell : cells) {
            cell.rebuild();
        }
    }
}
