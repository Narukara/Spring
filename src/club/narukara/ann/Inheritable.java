package club.narukara.ann;

public interface Inheritable extends Cloneable {
    void mutate(double step);

    void rebuild();
}
