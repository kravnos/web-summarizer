package com.websummarizer.Web.Summarizer.utils;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
@Data
public class RSAKeyProperties {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public RSAKeyProperties(){
        KeyPair keyPair = KeyGeneratorUtility.generateRsaKey();
        this.publicKey= (RSAPublicKey) keyPair.getPublic();
        this.privateKey= (RSAPrivateKey) keyPair.getPrivate();
    }
}