package club.narukara;

import club.narukara.ann.ActivationFunction;
import club.narukara.ann.Cell;

public class Main {

	public static void main(String[] args) {

		double[] weight = { 1, 2, 3, 4 };
		new Cell(weight, new ActivationFunction() {

			@Override
			public double activation(double input) {
				return 0;
			}
		});
	}

}
