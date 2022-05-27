package net.kigawa.data.database;

import net.kigawa.data.javatype.JavaDataInterface;

public interface TypeResolverInterface
{
    DatabaseType resolveType(JavaDataInterface javaDataInterface);
}
