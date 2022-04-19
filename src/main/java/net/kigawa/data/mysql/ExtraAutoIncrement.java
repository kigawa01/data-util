package net.kigawa.data.mysql;

import net.kigawa.data.database.Extra;

/**
 * @deprecated
 */
public class ExtraAutoIncrement extends Extra
{
    @Override
    public String getSql()
    {
        return "AUTO_INCREMENT";
    }
}
