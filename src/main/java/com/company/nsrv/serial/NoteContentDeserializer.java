package com.company.nsrv.serial;


import com.company.nsrv.model.note.AttachInstance;
import com.company.nsrv.model.note.NoteContent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.*;

public class NoteContentDeserializer extends StdDeserializer<NoteContent> {
    private static final String FIELD_NAME_CONTENT = "content";
    private static final String FIELD_NAME_TITLE   = "title";
    private static final String FIELD_NAME_ATTACH  = "attach";
    private static final String FILED_NAME_TAGS    = "tags";

    public NoteContentDeserializer() {
        this(null);
    }

    public NoteContentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public NoteContent deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode contentNode = node.get(FIELD_NAME_CONTENT);
        JsonNode titleNode = node.get(FIELD_NAME_TITLE);
        JsonNode attachListNode = node.get(FIELD_NAME_ATTACH);
        JsonNode tagsNode = node.get(FILED_NAME_TAGS);
        if (contentNode == null) {
            return null;
        }

        String content = contentNode.asText();
        String title = titleNode != null ? titleNode.asText() : "";

        List<AttachInstance> attachList = null;
        if (attachListNode != null && attachListNode.isArray()) {
            ArrayNode attachListArrayNode = (ArrayNode) attachListNode;
            attachList = new ArrayList<AttachInstance>(attachListArrayNode.size());
            for (JsonNode attachNode : attachListArrayNode) {
                attachList.add(new AttachInstance(attachNode.binaryValue()));
            }
        }

        Set<String> tags = null;
        if (tagsNode != null && tagsNode.isArray()) {
            ArrayNode tagsArrayNode = (ArrayNode) tagsNode;
            tags = new HashSet<String>(tagsArrayNode.size());
            for (JsonNode tagNode : tagsArrayNode) {
                tags.add(tagNode.asText());
            }
        }

        return new NoteContent(title, content, DateTime.now(), tags, attachList);
    }

    private static Byte[] boxBytes(byte[] array) {
        Byte[] box = new Byte[array.length];
        for (int i = 0; i < box.length; i++) {
            box[i] = array[i];
        }

        return box;
    }
}
