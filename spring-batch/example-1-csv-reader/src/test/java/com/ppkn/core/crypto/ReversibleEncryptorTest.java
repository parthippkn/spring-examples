package com.ppkn.core.crypto;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.ppkn.core.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class ReversibleEncryptorTest {

    @Autowired
    private ReversibleEncryptor reversibleEncryptor;

    @Test
    public void testReversibleEncryptorEncryption() {

	String encryptedText = reversibleEncryptor.encrypt("password");
	System.out.println("\n\n encryptedText : " + encryptedText + "\n");

	String decryptedText = reversibleEncryptor.decrypt(encryptedText);
	System.out.println("decryptedText : " + decryptedText + "\n\n");

	assertTrue("password".equals(decryptedText));

    }
    

    @Test
    public void testExistingReversibleEncryptorEncryption() {

	String encryptedText = "80d2cc491e293b673990929d336fbf1a1a08636c521b9f2276d992ca6da024f7;fd77b98c89091fb9";
	System.out.println("\n\n encryptedText : " + encryptedText + "\n");

	String decryptedText = reversibleEncryptor.decrypt(encryptedText);
	System.out.println("decryptedText : " + decryptedText + "\n\n");

	assertTrue("password".equals(decryptedText));

    }
    
}
