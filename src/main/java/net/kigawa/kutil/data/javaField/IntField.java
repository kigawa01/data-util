package net.kigawa.kutil.data.javaField;

import net.kigawa.kutil.data.exception.DatabaseException;

import java.math.BigInteger;

public class IntField extends AbstractNumberField
{
    private Integer integer;

    private IntField()
    {
        super(new BigInteger(Integer.toString(Integer.MIN_VALUE)),
                new BigInteger(Integer.toString(Integer.MAX_VALUE)));
    }

    @Override
    public Object getValueObject()
    {
        return integer;
    }

    @Override
    public void setValueObject(Object data)
    {
        if (!(data instanceof Integer)) throw new DatabaseException("intField can set integer");
        integer = (Integer) data;
    }
}
