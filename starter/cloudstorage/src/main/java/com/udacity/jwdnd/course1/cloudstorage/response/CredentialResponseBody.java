package com.udacity.jwdnd.course1.cloudstorage.response;

public class CredentialResponseBody {

  private Integer credentialid;
  private String url;
  private String username;
  private String decryptedPass;
  private String encryptedPassword;
  private Integer userid;

  public CredentialResponseBody(Integer credentialid, String url, String username,
      String decryptedPass, String encryptedPassword, Integer userid) {
    this.credentialid = credentialid;
    this.url = url;
    this.username = username;
    this.decryptedPass = decryptedPass;
    this.encryptedPassword = encryptedPassword;
    this.userid = userid;
  }

  public Integer getCredentialid() {
    return credentialid;
  }

  public void setCredentialid(Integer credentialid) {
    this.credentialid = credentialid;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDecryptedPass() {
    return decryptedPass;
  }

  public void setDecryptedPass(String decryptedPass) {
    this.decryptedPass = decryptedPass;
  }

  public String getEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(String encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }

  public Integer getUserid() {
    return userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }
}
