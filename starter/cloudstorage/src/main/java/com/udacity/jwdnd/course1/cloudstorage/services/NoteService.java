package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) throws Exception {

        try{
            return noteMapper.insert(note);
        }catch (Exception e){
            throw new Exception("CREATE NOTE SERVICE ERROR");
        }

    }

    public Note getNoteByTittleAndDescription(Note note){
        return noteMapper.selectByTitleAndDescription(note.getUserid(),
                note.getNotedescription(), note.getNotetitle());

    }

    public List<Note> getUserNotes(User user){
        return noteMapper.select(user);
    }

    public Note getNoteById(int noteid, int userid){ return noteMapper.selectById(noteid, userid); }

    public int updateNote(Note note){
        return noteMapper.update(note);
    }

    public int deleteNote(int noteid, int userid){
        return noteMapper.delete(noteid, userid);
    }


}
