package net.kigawa.data.mysql;

import net.kigawa.data.database.UrlOption;

public class UseSSLOpt extends UrlOption {
    private final boolean ssl;

    public UseSSLOpt(boolean ssl) {
        this.ssl = ssl;
    }

    @Override
    public String getSql() {
        return "useSSL=" + ssl;
    }
}
