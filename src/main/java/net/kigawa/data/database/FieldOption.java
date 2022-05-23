package net.kigawa.data.database;

public interface FieldOption
{

    String getSql();

    public static class NotNull implements FieldOption
    {
        @Override
        public String getSql()
        {
            return "NOT NULL";
        }
    }

    public static class AutoIncrement implements FieldOption
    {
        @Override
        public String getSql()
        {
            return "AUTO_INCREMENT";
        }
    }

    public static class Unique implements FieldOption
    {
        @Override
        public String getSql()
        {
            return "UNIQUE";
        }
    }
}
