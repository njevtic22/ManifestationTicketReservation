package utility.generator;

import java.util.NoSuchElementException;

public class LongGenerator extends NumberGenerator<Long> {
    public LongGenerator() {
        this(0L);
    }

    public LongGenerator(Long start) {
        this(start, 1L);
    }

    public LongGenerator(Long start, Long step) {
        this(start, step, Long.MAX_VALUE);
    }

    public LongGenerator(Long start, Long step, Long maxValue) {
        super(start, step, maxValue);
    }

    @Override
    public Long peakNext() throws NoSuchElementException {
        if (!hasNext())
            throw new NoSuchElementException("Long generator has no more elements.");
        return current + step;
    }

    @Override
    protected boolean isOverflow() {
        // step is positive
        return current > Long.MAX_VALUE - step;
    }

    @Override
    protected boolean isUnderflow() {
        // step is negative
        return current < Long.MIN_VALUE - step;
    }

    @Override
    public boolean isStepPositive() {
        return step > 0L;
    }

    @Override
    public boolean isStepNegative() {
        return !isStepPositive();
    }

    @Override
    public boolean hasNext() {
        if (isStepPositive())
            return !isOverflow();
        else
            return !isUnderflow();
    }

    @Override
    public Long next() throws NoSuchElementException {
        if (!hasNext())
            throw new NoSuchElementException("Long generator has no more elements.");
        return ++current;
    }
}