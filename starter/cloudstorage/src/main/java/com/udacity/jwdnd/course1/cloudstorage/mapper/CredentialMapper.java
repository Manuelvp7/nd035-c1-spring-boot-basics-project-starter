package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid )" +
            "VALUES(#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE url LIKE #{url} AND username LIKE #{username} AND userid = #{userid}")
    Credential selectByUrlAndUsername(int userid, String url, String username);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    Credential selectById(int noteid, int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> select(User user);

    @Update("UPDATE CREDENTIALS SET "+
            "url = #{url}, " +
            "username = #{username}," +
            "key = #{key}," +
            "password = #{password}" +
            "WHERE credentialid = #{credentialid}")
    int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    int delete(int credentialid, int userid);

}
