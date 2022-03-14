package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    private UserService userService;
    private CredentialService credentialService;
    private NoteService noteService;
    private FileService fileService;
    private final String [] schemes = {"http", "https"};
    private final UrlValidator urlValidator;

    private static final Logger logger = LoggerFactory.getLogger(CredentialsController.class);

    public CredentialsController(
        UserService userService,
        CredentialService credentialService,
        NoteService noteService,
        FileService fileService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
        urlValidator = new UrlValidator(schemes);
    }


    @PostMapping
    public String createCredential( Authentication authentication, Note note, Credential credential, Model model){

        String submitError = null;


        if (!urlValidator.isValid(credential.getUrl())){
            submitError = "Invalid url at credential submission";
            model.addAttribute("submitError", submitError);
        }else{
            String username = authentication.getName();
            User user = userService.getUser(username);
            if (user == null){
                submitError = "User not found at credential submission";
                model.addAttribute("submitError", submitError);
            }else{
                credential.setUserid(user.getUserid());

                Credential createdCredential = this.credentialService.getByUrlAndUsername(user.getUserid(), credential.getUrl(), credential.getUsername());
                if (createdCredential != null){
                    submitError = "User already available.";
                    model.addAttribute("submitError", submitError);
                }else{
                    int rowsAdded = credentialService.save(credential);
                    model.addAttribute("submitSuccess", true);
                    model.addAttribute("notes", noteService.getUserNotes(user));
                    model.addAttribute("credentials", credentialService.getUserCredentials(user));
                    model.addAttribute("files", fileService.getUserFiles(user));
                }
            }
        }


        return "result";
    }

    @PostMapping("/{id}")
    public String updateNote( @PathVariable Long id, Authentication authentication, Note note, Credential credential, Model model){

        String submitError = null;

        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user == null){
            submitError = "User not found at note update";
            model.addAttribute("submitError", submitError);
        }else {
            int rowsUpdated = credentialService.updateCredential(credential);
            if (rowsUpdated < 1 ){
                submitError = "There was a problem submitting your note. Please try again";
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

    @GetMapping("/delete/{id}")
    public String deleteNote( @PathVariable int id, Authentication authentication, Note note, Credential credential,Model model){

        String submitError = null;

        String username = authentication.getName();
        User user = userService.getUser(username);

        if (user == null){
            submitError = "User not found at credential delete";
            model.addAttribute("submitError", submitError);
        }else {
            int rows = credentialService.deleteCredential(id, user.getUserid());
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
