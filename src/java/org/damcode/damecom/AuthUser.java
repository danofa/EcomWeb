package org.damcode.damecom;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class AuthUser {

    public static byte[] createSalt(){
        Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return salt;
    }
    
    public static byte[] hashPassword(char[] pass, byte[] salt){
        byte[] hashedPass = null;
        SecretKeyFactory key = null;
        
        PBEKeySpec spec = new PBEKeySpec(pass, salt, 5000, 256);
        
        try {
            key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            hashedPass = key.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AuthUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashedPass;
    }
    
    public static byte[] hashPassword(String pass, byte[] salt){
        return hashPassword(pass.toCharArray(), salt);
    }
    
}
