package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.response.CredentialResponseBody;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private EncryptionService encryptionService;

    private static final Logger logger = LoggerFactory.getLogger(CredentialService.class);

    public int save(Credential credential){

        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        credential.setKey(encodedSalt);
        credential.setPassword(encryptedPassword);

        return  this.credentialMapper.insert(credential);
    }
    public int deleteCredential(int credential, int userid){
        return credentialMapper.delete(credential, userid);
    }

    public List<CredentialResponseBody> getUserCredentials(User user){

        List<Credential> select = this.credentialMapper.select(user);
        List<CredentialResponseBody> credentials = new ArrayList<>();
        select.stream().forEach(credential -> {
            credentials.add(
                new CredentialResponseBody(
                    credential.getCredentialid(),
                    credential.getUrl(),
                    credential.getUsername(),
                    encryptionService.decryptValue(credential.getPassword(), credential.getKey()),
                    credential.getPassword(),
                    credential.getUserid()
                )
            );
        }


        );

        return credentials;
    }

    public int updateCredential(Credential credential){

        logger.info(credential.toString());
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        credential.setKey(encodedSalt);
        credential.setPassword(encryptedPassword);

        return  this.credentialMapper.update(credential);
    }

}
