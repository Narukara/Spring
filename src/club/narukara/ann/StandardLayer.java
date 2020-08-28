package club.narukara.ann;

import java.util.InputMismatchException;

public class StandardLayer extends Layer {
	private Cell[] cells;

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

	public double[] calculate(double[] input) throws InputMismatchException {
		double[] output = new double[outputLength];
		for (int i = 0; i < outputLength; i++) {
			output[i] = cells[i].calculate(input);
		}
		return output;
	}

	@Override
	public void mutate(double step) {
		for (int i = 0; i < cells.length; i++) {
			cells[i].mutate(step);
		}
	}

	@Override
	public void rebuild() {
		for (int i = 0; i < cells.length; i++) {
			cells[i].rebuild();
		}
	}
}
