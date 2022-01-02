package net.kigawa.data.cmd;

import net.kigawa.data.sql.AbstractSqlCmd;

import java.util.Arrays;

public class SqlCmd extends AbstractSqlCmd<SqlCmd> {

    public SqlCmd(String... command) {
        cmd.addAll(Arrays.asList(command));
    }
}
