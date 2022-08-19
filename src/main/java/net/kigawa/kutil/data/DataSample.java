package net.kigawa.kutil.data;

import net.kigawa.kutil.data.annotation.Entity;
import net.kigawa.kutil.data.annotation.NotNull;
import net.kigawa.kutil.data.annotation.PrimaryKey;
import net.kigawa.kutil.data.annotation.UniqueKey;

@Entity
public class DataSample
{
    @PrimaryKey
    private int id;
    @UniqueKey
    @NotNull
    private String name;
}
