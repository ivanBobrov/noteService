package com.company.nsrv;


import com.company.nsrv.config.Configuration;
import com.company.nsrv.config.IConfiguration;
import com.company.nsrv.model.AccountManager;
import com.company.nsrv.model.note.Note;
import com.company.nsrv.model.note.NoteContent;
import com.company.nsrv.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotesController {
    private final Logger log = LoggerFactory.getLogger(NotesController.class);

    @Autowired
    IConfiguration configuration;

    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = accountManager.getUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody User user) {
        if (accountManager.containsUser(user.getUsername())) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        accountManager.addUser(user);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String userName) {
        if (userName.isEmpty() || !accountManager.containsUser(userName)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        accountManager.removeUser(userName);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<List<Note>> getNotesList(@PathVariable String userName) {
        if (!userName.isEmpty()) {
            User user = accountManager.getUser(userName);
            if (user != null) {
                List<Note> notes = user.getNotesList();
                if (notes != null) {
                    return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<List<Note>>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{userName}/{noteId}/history")
    public ResponseEntity<List<NoteContent>> getNoteModifications(@PathVariable String userName,
                                                                  @PathVariable int noteId) {
        User user = accountManager.getUser(userName);
        if (user == null) {
            return new ResponseEntity<List<NoteContent>>(HttpStatus.NOT_FOUND);
        }

        Note note = user.getNote(noteId);
        if (note == null) {
            return new ResponseEntity<List<NoteContent>>(HttpStatus.NOT_FOUND);
        }

        List<NoteContent> history = note.getHistory();
        return new ResponseEntity<List<NoteContent>>(history, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userName}/{noteId}", method = RequestMethod.GET)
    public ResponseEntity<Note> getNote(@PathVariable String userName, @PathVariable int noteId) {
        User user = accountManager.getUser(userName);
        if (user == null) {
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }

        Note note = user.getNote(noteId);
        if (note == null) {
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Note>(note, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.POST)
    public ResponseEntity<Integer> addNote(@PathVariable String userName, @RequestBody NoteContent content) {
        User user = accountManager.getUser(userName);
        if (user == null) {
            return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
        }

        Note note = new Note(content);
        Integer id = user.addNewNote(note);
        return new ResponseEntity<Integer>(id, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{userName}/{noteId}", method = RequestMethod.PUT)
    public ResponseEntity<Integer> updateNote(@PathVariable String userName,
                                              @PathVariable int noteId,
                                              @RequestBody NoteContent update) {

        User user = accountManager.getUser(userName);
        if (user == null) {
            return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
        }

        Note note = user.getNote(noteId);
        if (note == null) {
            return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
        }

        note.updateNote(update);
        return new ResponseEntity<Integer>(note.getNoteId(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{userName}/{noteId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteNote(@PathVariable String userName, @PathVariable int noteId) {
        User user = accountManager.getUser(userName);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        boolean success = user.removeNote(noteId);
        if (!success) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        };

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/buildinfo")
    public ResponseEntity<IConfiguration> getBuildInfo() {
        return new ResponseEntity<IConfiguration>(configuration, HttpStatus.OK);
    }
}
