package com.company.nsrv.serial;


import com.company.nsrv.config.IConfiguration;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ConfigurationSerializer extends StdSerializer<IConfiguration> {
    private static final String FIELD_NAME_VERSION = "version";
    private static final String FIELD_NAME_TIME = "buildTime";

    public ConfigurationSerializer() {
        this(null);
    }

    public ConfigurationSerializer(Class<IConfiguration> t) {
        super(t);
    }

    @Override
    public void serialize(IConfiguration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField(FIELD_NAME_VERSION, value.getVersion());
        gen.writeStringField(FIELD_NAME_TIME, value.getBuildTime());

        gen.writeEndObject();
    }
}


