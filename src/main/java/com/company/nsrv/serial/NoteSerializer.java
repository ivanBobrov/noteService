package com.company.nsrv.serial;


import com.company.nsrv.model.note.Note;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class NoteSerializer extends StdSerializer<Note> {
    private static final String FIELD_NAME_NOTE = "note";
    private static final String FIELD_NAME_NOTE_ID = "noteId";

    public NoteSerializer() {
        this(null);
    }

    public NoteSerializer(Class<Note> t) {
        super(t);
    }

    @Override
    public void serialize(Note value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeObjectField(FIELD_NAME_NOTE, value.getNoteContent());
        Integer noteId = value.getNoteId();
        if (noteId != null) {
            gen.writeNumberField(FIELD_NAME_NOTE_ID, noteId);
        }

        gen.writeEndObject();
    }
}
