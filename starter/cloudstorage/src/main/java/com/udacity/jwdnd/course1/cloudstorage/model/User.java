package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
    private Integer userid;
    private String username;
    private char[] salt;
    private char[] password;
    private String firstname;
    private String lastname;

    public User() {
    }

    public User(Integer userid, String username, String salt, String password, String firstname, String lastname) {
        this.userid = userid;
        this.username = username;
        if(salt != null)
            this.salt = salt.toCharArray();
        if(password != null)
            this.password = password.toCharArray();
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return String.valueOf(this.salt);
    }

    public void setSalt(String salt) {
        this.salt = salt.toCharArray();
    }

    public String getPassword() {
        if (password != null)
            return String.valueOf(password);
        return null;
    }

    public void setPassword(String password) {

        this.password = password.toCharArray();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
