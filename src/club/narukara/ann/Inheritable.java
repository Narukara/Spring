package club.narukara.ann;

public interface Inheritable extends Cloneable {
	public void mutate(double step);

	public void rebuild();
}
