package net.kigawa.kutil.data.exception;

public class DatabaseException extends RuntimeException
{
    public DatabaseException(String message)
    {
        super(message);
    }

    public DatabaseException(Throwable throwable)
    {
        super(throwable);
    }
}
