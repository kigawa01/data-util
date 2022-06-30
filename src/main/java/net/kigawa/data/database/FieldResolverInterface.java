package net.kigawa.data.database;

import net.kigawa.data.javatype.JavaField;

public interface FieldResolverInterface
{
    DatabaseField resolveType(JavaField javaField);

    JavaField resolveType(DatabaseField databaseField);

    boolean canResolve(JavaField javaField);

    boolean canResolve(DatabaseField databaseField);
}
