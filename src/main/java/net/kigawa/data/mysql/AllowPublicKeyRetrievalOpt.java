package net.kigawa.data.mysql;

import net.kigawa.data.database.UrlOption;

public class AllowPublicKeyRetrievalOpt extends UrlOption {
    private final boolean ssl;

    public AllowPublicKeyRetrievalOpt(boolean ssl) {
        this.ssl = ssl;
    }

    @Override
    public String getSql() {
        return "allowPublicKeyRetrieval=" + ssl;
    }
}
