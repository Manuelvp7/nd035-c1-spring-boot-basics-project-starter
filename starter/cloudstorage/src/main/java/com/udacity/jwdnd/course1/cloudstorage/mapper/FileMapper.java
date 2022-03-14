package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface FileMapper {

  @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata )" +
      "VALUES(#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
  @Options(useGeneratedKeys = true, keyProperty = "fileId")
  int insert(File file);

  @Select("SELECT * FROM FILES WHERE userid = #{userid}")
  List<File> select(User user);

  @Select("SELECT * FROM FILES WHERE filename LIKE #{filename} AND userid = #{userid}")
  File selectByFileName(String filename, int userid);

  @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
  File selectById(int fileId, int userid);

  @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
  int delete(int fileId, int userid);

}
