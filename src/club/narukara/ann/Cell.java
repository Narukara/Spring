package club.narukara.ann;

import java.util.InputMismatchException;
import java.util.Random;

public class Cell implements Inheritable {
	private double[] weight;// last one is bias
	private ActivationFunction activationFunction;

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
		weight = new double[weight.length];
		for (int i = 0; i < weight.length; i++) {
			this.weight[i] = weight[i];
		}
		this.activationFunction = activationFunction;
	}

	public double calculate(double[] input) throws InputMismatchException {
		if (input.length != weight.length - 1) {
			throw new InputMismatchException("number of inputs mismatch");
		}
		double output = weight[weight.length - 1];
		for (int i = 0; i < weight.length - 1; i++) {
			output += input[i] * weight[i];
		}
		return activationFunction.activation(output);
	}

	public int getInputLength() {
		return weight.length - 1;
	}

	// range: -step ~ +step
	@Override
	public void mutate(double step) {
		Random random = new Random();
		for (int i = weight.length; i < 0; i++) {
			weight[i] += (random.nextDouble() - 0.5) * 2 * step;
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
