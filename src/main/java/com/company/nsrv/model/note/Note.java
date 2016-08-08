package com.company.nsrv.model.note;


import com.company.nsrv.serial.NoteSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@JsonSerialize(using = NoteSerializer.class)
public class Note {
    private static AtomicInteger index = new AtomicInteger(1);
    private final Integer noteId;
    private NoteContent content;
    private final LinkedList<NoteContent> modifications = new LinkedList<NoteContent>();

    public Note(NoteContent content) {
        this.content = content;
        this.modifications.addFirst(content);
        this.noteId = index.getAndIncrement();
    }

    public Note(String title, String content) {
        NoteContent noteContent = NoteContent.createNoteContent(title, content);
        this.content = noteContent;
        this.modifications.addFirst(noteContent);
        this.noteId = index.getAndIncrement();
    }

    public Integer getNoteId() {
        return noteId;
    }

    public synchronized boolean hasAttach() {
        return content.hasAttach();
    }

    public synchronized List<AttachInstance> getAttachList() {
        return content.getAttachList();
    }

    public synchronized void updateNote(String newTitle, String newBody) {
        NoteContent newContent = NoteContent.createNoteContent(newTitle, newBody);
        this.content = newContent;
        this.modifications.addFirst(newContent);
    }

    public synchronized void updateNote(NoteContent update) {
        this.content = update;
        this.modifications.addFirst(update);
    }

    public synchronized void updateBody(String newBody) {
        String title = content.getTitle();
        NoteContent newContent = NoteContent.createNoteContent(title, newBody);
        this.content = newContent;
        this.modifications.addFirst(newContent);
    }

    public synchronized List<NoteContent> getHistory() {
        return new ArrayList<NoteContent>(modifications);
    }

    public synchronized NoteContent getNoteContent() {
        return content;
    }

    public synchronized String getNoteTitle() {
        return content.getTitle();
    }

    public synchronized DateTime getLastUpdatedTime() {
        return content.getCreationTime();
    }
}
