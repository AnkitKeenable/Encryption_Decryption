package org.example;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DESService {

    public static final String KEY_STRING ="Sa12oQINnpE" ;
    private static final String IV_STRING = "JRIIFNbi0C4";
    private final SecretKey key;
    private Cipher encCipher;
    private Cipher decCipher;

    public DESService() throws Exception {
        this.key = generateKey();
        initCiphers();
    }

    private void initCiphers() throws Exception {
        encCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encCipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(decoder(IV_STRING)));

        decCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(decoder(IV_STRING)));
    }

    public byte[] encrypt(String message) throws Exception {
        return encCipher.doFinal(message.getBytes());
    }

    public String decrypt(byte[] encryptedMessage) throws Exception {
        return new String(decCipher.doFinal(encryptedMessage));
    }

    public static SecretKey generateKey() {
        return new SecretKeySpec(decoder(KEY_STRING), "DES");
    }

    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decoder(String data) {
        return Base64.getDecoder().decode(data);
    }
}
