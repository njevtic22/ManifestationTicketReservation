package utility.generator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class NumberGenerator<T extends Number> implements Iterator<T> {
    protected T current;
    protected T step;
    protected T MAX_VALUE;

    protected NumberGenerator(T start, T step, T maxValue) {
        this.current = start;
        this.step = step;
        this.MAX_VALUE = maxValue;
    }

    public abstract T peakNext() throws NoSuchElementException;

    protected abstract boolean isOverflow();
    protected abstract boolean isUnderflow();

    public abstract boolean isStepPositive();
    public abstract boolean isStepNegative();
}
