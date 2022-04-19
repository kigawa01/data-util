package net.kigawa.data.database;

public class PrimaryKeyException extends DatabaseException
{
    public PrimaryKeyException()
    {
    }

    public PrimaryKeyException(String message)
    {
        super(message);
    }
}
