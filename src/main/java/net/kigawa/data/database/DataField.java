package net.kigawa.data.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(
        ElementType.FIELD
)
public @interface DataField
{
    String minLength = null;
    String maxLength = null;
    Object defaultValue = null;
    FieldOption[] options = null;
}
