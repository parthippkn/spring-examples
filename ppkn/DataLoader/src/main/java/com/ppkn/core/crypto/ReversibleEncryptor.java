package com.ppkn.core.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

import com.ppkn.core.config.AppConfigReader;

@Component
public class ReversibleEncryptor {

    private static final String DELIM = "-";

    @Autowired
    private AppConfigReader appConfigReader;

    public String encrypt(String textToEncrypt) {

	final String password = appConfigReader.getApplicationCryptoKey();
	final String salt = KeyGenerators.string().generateKey();

	TextEncryptor encryptor = Encryptors.text(password, salt);
	String encryptedText = encryptor.encrypt(textToEncrypt);
	encryptedText = encryptedText + DELIM + salt;

	return encryptedText;
    }

    public String decrypt(String encryptedText) {

	String array[] = encryptedText.split(DELIM);
	final String password = appConfigReader.getApplicationCryptoKey();
	final String salt = array[1];

	TextEncryptor decryptor = Encryptors.text(password, salt);
	String decryptedText = decryptor.decrypt(array[0]);

	return decryptedText;
    }
}
