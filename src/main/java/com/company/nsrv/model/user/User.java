package com.company.nsrv.model.user;


import com.company.nsrv.model.note.Note;
import com.company.nsrv.serial.UserDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@JsonDeserialize(using = UserDeserializer.class)
public class User {
    private final String username;
    private final Map<Integer, Note> notes = new ConcurrentHashMap<Integer, Note>();

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Integer addNewNote(Note newNote) {
        Integer noteId = newNote.getNoteId();
        notes.put(noteId, newNote);

        return noteId;
    }

    public Note getNote(Integer noteId) {
        if (noteId == null || noteId <= 0) {
            return null;
        }

        return notes.get(noteId);
    }

    public List<Note> getNotesList() {
        return new ArrayList<Note>(notes.values());
    }

    public boolean removeNote(Integer noteId) {
        if (noteId == null || noteId <= 0) {
            return false;
        }

        return notes.remove(noteId) == null ? false : true;
    }
}
