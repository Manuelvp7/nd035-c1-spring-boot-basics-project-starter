package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Autowired
  FileMapper fileMapper;

  private static final Logger logger = LoggerFactory.getLogger(FileService.class);

  public int saveFile(MultipartFile multipartFile, User user)  throws IOException, SQLException {

      File file = new File(
          multipartFile.getOriginalFilename(),
          multipartFile.getContentType(),
          String.valueOf(multipartFile.getSize()),
          user.getUserid(),
          multipartFile.getBytes()
      );

      return fileMapper.insert(file);
  }

  public List<File> getUserFiles(User user){
    List<File> files = this.fileMapper.select(user);
    return files;
  }

  public File selectByFileName(int id, String filename){
    return this.fileMapper.selectByFileName(filename, id);
  }

  public File getFile(int id, int userid){
    return fileMapper.selectById(id,userid);

  }

  public int deleteFile(int id, int userid){

    return fileMapper.delete(id, userid);
  }


}
