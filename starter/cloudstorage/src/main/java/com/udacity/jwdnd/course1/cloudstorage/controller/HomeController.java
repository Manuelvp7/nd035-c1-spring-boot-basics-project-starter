package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {

    private NoteService noteService;
    private UserService userService;
    private FileService fileService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;


    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    public HomeController(NoteService noteService,
        UserService userService,
        FileService fileService,
        CredentialService credentialService,
        EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getMapping(Authentication authentication, Note note, Credential credential, Model model){

        String submitError = null;
        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user == null){
            submitError = "User not found at note submission";
        }else{
            model.addAttribute("notes", noteService.getUserNotes(user));
            model.addAttribute("credentials", credentialService.getUserCredentials(user));
            model.addAttribute("files", fileService.getUserFiles(user));
        }

        return "home";
    }


    @PostMapping("/notes")
    public String notesPostRequest(Authentication authentication, Note note, Credential credential, Model model) throws Exception {

        String submitError = null;

        String username = authentication.getName();
        User user = userService.getUser(username);


        if (user == null){
            submitError = "User not found at note submission";
            model.addAttribute("submitError", submitError);
        }else{

            try{
                note.setUserid(user.getUserid());
                Note createdNote = noteService.getNoteByTittleAndDescription(note);
                if (createdNote != null){
                    submitError = "Note already available";
                    model.addAttribute("submitError", submitError);
                }else{

                    int rowsAdded = noteService.createNote(note);
                    if (rowsAdded < 1 ){
                        submitError = "There was a problem submitting your note. Please try again";
                        model.addAttribute("submitError", submitError);
                    }else{
                        model.addAttribute("submitSuccess", true);
                        model.addAttribute("notes", noteService.getUserNotes(user));
                        model.addAttribute("credentials", credentialService.getUserCredentials(user));
                        model.addAttribute("files", fileService.getUserFiles(user));
                    }
                }

            }catch (Exception e){

                if (note.getNotedescription().length() > 1000){
                    submitError = "Note can't be saved as description exceed 1000 characters";
                    model.addAttribute("submitError", submitError);

                } else{
                    submitError = "There was a problem submitting your note. Please try again";
                    model.addAttribute("submitError", submitError);
                }
            }
        }

        return "result";
    }

    @PostMapping("/notes/{id}")
    public String updateNote( @PathVariable Long id, Authentication authentication, Note note, Credential credential, Model model){

        String submitError = null;

        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user == null){
            submitError = "User not found at note update";
            model.addAttribute("submitError", submitError);
        }else {
            try{
                int rowsUpdates = noteService.updateNote(note);
                if (rowsUpdates < 1 ){
                    submitError = "There was a problem submitting your note. Please try again";
                    model.addAttribute("submitError", submitError);
                }else{
                    model.addAttribute("submitSuccess", true);
                    model.addAttribute("notes", noteService.getUserNotes(user));
                    model.addAttribute("credentials", credentialService.getUserCredentials(user));
                    model.addAttribute("files", fileService.getUserFiles(user));
                }
            }catch (Exception e){
                submitError = "There was a problem submitting your note. Please try again";
                model.addAttribute("submitError", submitError);
            }

        }

        return "result";
    }



    @GetMapping("/notes/delete/{id}")
    public String deleteNote( @PathVariable int id, Authentication authentication, Note note, Credential credential, Model model){

        String submitError = null;

        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user == null){
            submitError = "User not found at note delete";
            model.addAttribute("submitError", submitError);
        }else {
            int rows = noteService.deleteNote(id, user.getUserid());
            if (rows < 1){
                submitError = "There was a problem deleting your note. Please try again";
                model.addAttribute("submitError", submitError);
            }else{
                model.addAttribute("submitSuccess", true);
                model.addAttribute("notes", noteService.getUserNotes(user));
                model.addAttribute("credentials", credentialService.getUserCredentials(user));
                model.addAttribute("files", fileService.getUserFiles(user));
            }
        }

        return "result";
    }

}
