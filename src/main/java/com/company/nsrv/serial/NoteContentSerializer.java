package com.company.nsrv.serial;


import com.company.nsrv.model.note.AttachInstance;
import com.company.nsrv.model.note.NoteContent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class NoteContentSerializer extends StdSerializer<NoteContent> {
    private static final String FIELD_NAME_CONTENT = "content";
    private static final String FIELD_NAME_TITLE = "title";
    private static final String FIELD_NAME_ATTACH = "attach";
    private static final String FIELD_NAME_TAGS = "tags";
    private static final String FIELD_NAME_TIME = "time";

    public NoteContentSerializer() {
        this(null);
    }

    public NoteContentSerializer(Class<NoteContent> t) {
        super(t);
    }

    @Override
    public void serialize(NoteContent value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField(FIELD_NAME_TITLE, value.getTitle());
        gen.writeStringField(FIELD_NAME_CONTENT, value.getBody());
        gen.writeStringField(FIELD_NAME_TIME, value.getCreationTime().toString());


        if (value.hasAttach()) {
            List<AttachInstance> attachList = value.getAttachList();
            gen.writeArrayFieldStart(FIELD_NAME_ATTACH);
            for (AttachInstance attach : attachList) {
                gen.writeBinary(attach.getBytes());
            }
            gen.writeEndArray();
        }


        if (value.hasTags()) {
            Set<String> tags = value.getTags();
            gen.writeArrayFieldStart(FIELD_NAME_TAGS);
            for (String tag : tags) {
                gen.writeString(tag);
            }
            gen.writeEndArray();
        }

        gen.writeEndObject();
    }
}
