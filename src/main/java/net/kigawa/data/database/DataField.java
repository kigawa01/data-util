package net.kigawa.data.database;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(
        ElementType.FIELD
)
public @interface DataField
{
}
