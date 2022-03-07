package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid)" +
            "VALUES(#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    Note selectById(int noteid, int userid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> select(User user);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, " +
            "notedescription = #{notedescription}" +
            "WHERE noteid = #{noteid}")
    int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    int delete(int noteid, int userid);

}
