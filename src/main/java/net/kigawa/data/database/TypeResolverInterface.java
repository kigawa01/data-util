package net.kigawa.data.database;

import net.kigawa.data.javatype.JavaDataInterface;

public interface TypeResolverInterface
{
    DatabaseTypeField resolveType(JavaDataInterface javaDataInterface);
}
