package com.company.nsrv.config;


import java.util.Properties;

public class Configuration implements IConfiguration {
    private static final String PROPERTY_NAME_TIMESTAMP = "timestamp";
    private static final String PROPERTY_NAME_VERSION = "version";
    private static final String VERSION_DEFAULT = "undefined";
    private static final String TIMESTAMP_DEFAULT = "0";

    private final String version;
    private final String buildTime;

    public Configuration() {
        this.version = VERSION_DEFAULT;
        this.buildTime = TIMESTAMP_DEFAULT;
    }

    public Configuration(Properties properties) {
        this.version = properties.getProperty(PROPERTY_NAME_VERSION, VERSION_DEFAULT);
        this.buildTime = properties.getProperty(PROPERTY_NAME_TIMESTAMP, TIMESTAMP_DEFAULT);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getBuildTime() {
        return buildTime;
    }
}
