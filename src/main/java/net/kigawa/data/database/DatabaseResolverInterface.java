package net.kigawa.data.database;

import net.kigawa.data.javaConstraint.JavaConstraint;
import net.kigawa.data.javaField.JavaField;

public interface DatabaseResolverInterface
{
    DatabaseField resolveField(JavaField javaField);
    DatabaseConstraint resolveConstraint(JavaConstraint javaConstraint);

    boolean canResolveConstraint(JavaConstraint javaConstraint);
    boolean canResolveField(JavaField javaField);

}
