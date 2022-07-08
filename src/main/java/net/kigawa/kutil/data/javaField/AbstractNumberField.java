package net.kigawa.kutil.data.javaField;

import java.math.BigInteger;

public abstract class AbstractNumberField extends AbstractDataField
{
    private BigInteger min;
    private BigInteger max;

    public AbstractNumberField(BigInteger min, BigInteger max)
    {
        this.min = min;
        this.max = max;
    }

    public void setMax(BigInteger max)
    {
        this.max = max;
    }

    public void setMin(BigInteger min)
    {
        this.min = min;
    }

    public BigInteger getMax()
    {
        return max;
    }

    public BigInteger getMin()
    {
        return min;
    }
}
