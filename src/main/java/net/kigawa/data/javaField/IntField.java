package net.kigawa.data.javaField;

import net.kigawa.data.exception.DatabaseException;

public class IntField extends DataField
{
    private Integer integer;

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
