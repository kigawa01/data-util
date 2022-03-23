package net.kigawa.data.keytype;

public class PrimaryKey extends KeyType {
    public static String NAME = "PRIMARY KEY";

    @Override
    public boolean equals(String name) {
        return NAME.equals(name);
    }
}
