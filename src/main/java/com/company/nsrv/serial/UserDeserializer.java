package com.company.nsrv.serial;


import com.company.nsrv.model.user.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {
    private static final String FIELD_NAME_USERNAME = "username";

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode usernameNode = node.get(FIELD_NAME_USERNAME);
        if (usernameNode == null) {
            return null;
        }

        String username = usernameNode.asText();
        return new User(username);
    }
}

