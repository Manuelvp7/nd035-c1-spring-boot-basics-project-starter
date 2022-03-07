package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.google.common.net.HttpHeaders;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.io.IOException;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FilesController {

  @Autowired
  private FileService fileService;

  @Autowired
  private UserService userService;

  @Autowired
  private CredentialService credentialService;

  @Autowired
  private NoteService noteService;


  @GetMapping
  public String getFiles(Authentication authentication, Note note, Credential credential, Model model){
    String submitError = null;

    String username = authentication.getName();
    User user = userService.getUser(username);

    if (user == null){
      submitError = "User not found at credential submission";
      model.addAttribute("submitError", submitError);
    }else{

      model.addAttribute("submitSuccess", true);
      model.addAttribute("notes", noteService.getUserNotes(user));
      model.addAttribute("credentials", credentialService.getUserCredentials(user));
      model.addAttribute("files", fileService.getUserFiles(user));

    }

    return "home";
  }

  @PostMapping
  public String saveFile( Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Note note, Credential credential, Model model)
      throws IOException, SQLException {

    String submitError = null;

    String username = authentication.getName();
    User user = userService.getUser(username);

    if (user == null){
      submitError = "User not found at file submission";
      model.addAttribute("submitError", submitError);
    }if (file.isEmpty()){
      submitError = "Empty file at file submission";
      model.addAttribute("submitError", submitError);
    }else{

      int rowsAdded = fileService.saveFile(file, user);
      model.addAttribute("submitSuccess", true);
      model.addAttribute("notes", noteService.getUserNotes(user));
      model.addAttribute("credentials", credentialService.getUserCredentials(user));
      model.addAttribute("files", fileService.getUserFiles(user));
    }

    return "result";

  }

  @GetMapping("/{id}")
  public ResponseEntity<Resource> getFile( @PathVariable int id, Authentication authentication, Note note, Credential credential, Model model)
      throws Exception {

    String submitError = null;

    String username = authentication.getName();
    User user = userService.getUser(username);

    if (user == null){
      submitError = "User not found at credential submission";
      model.addAttribute("submitError", submitError);
    }else{
      try{
        File file = fileService.getFile(id, user.getUserid());
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(file.getContenttype()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
            .body(new ByteArrayResource(file.getFiledata()));
      }catch(Exception e) {
        throw new Exception("Error downloading file");
      }
    }

    return  null;
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
      int rows = fileService.deleteFile(id, user.getUserid());
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

    return "home";
  }


}
