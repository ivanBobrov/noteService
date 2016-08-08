package com.company.nsrv.model.note;

import com.company.nsrv.serial.NoteContentDeserializer;
import com.company.nsrv.serial.NoteContentSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonSerialize(using = NoteContentSerializer.class)
@JsonDeserialize(using = NoteContentDeserializer.class)
public class NoteContent {
    private final String title;
    private final String body;
    private final DateTime creationTime;
    private final Set<String> tagList;
    private final List<AttachInstance> attachList;

    public NoteContent(String title, String body, DateTime creationTime,
                       Set<String> tagList, List<AttachInstance> attach) {
        this.title = title;
        this.body = body;
        this.creationTime = creationTime;
        this.tagList = tagList == null ? null : tagList;
        this.attachList = attach == null ? null : attach;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public boolean hasTags() {
        return tagList != null;
    }

    public Set<String> getTags() {
        return new HashSet<String>(tagList);
    }

    public boolean hasAttach() {
        return attachList != null;
    }

    public List<AttachInstance> getAttachList() {
        return new ArrayList<AttachInstance>(attachList);
    }

    public static NoteContent createNoteContent(String title, String body) {
        return new NoteContent(title, body, DateTime.now(), null, null);
    }

    public static NoteContent createNoteContent(String body) {
        return createNoteContent("", body);
    }
}
