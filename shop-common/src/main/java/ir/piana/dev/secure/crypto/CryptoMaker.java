package ir.piana.dev.secure.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohammad Rahmati, 4/16/2017 6:24 PM
 */
public class CryptoMaker {
    private static boolean initialized = false;
    private static final
    Map<String, Cipher> cipherMap
            = new HashMap<>();

    private static synchronized void initialize()
            throws Exception {
        if (initialized)
            return;
        for (CryptoAttribute cryptoAttribute
                : CryptoAttribute.values()) {
            try {
                Cipher cipher = Cipher.getInstance(
                        cryptoAttribute.getName());
                cipherMap.put(cryptoAttribute.getName(), cipher);
            } catch (NoSuchAlgorithmException e) {
                cipherMap.clear();
                throw new Exception(String.format(
                        "key generator maker not can initialized, because %s.",
                        cryptoAttribute.getName()));
            }
        }
        initialized = true;
    }

    public static byte[] encrypt(
            byte[] raw,
            Key secretKey,
            CryptoAttribute cryptoAttribute)
            throws Exception {
        initialize();
        Cipher cipher =
                cipherMap.get(
                        cryptoAttribute.getName());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(raw);
    }

    public static byte[] encrypt(
            byte[] raw,
            Key secretKey,
            CryptoAttribute cryptoAttribute,
            IvParameterSpec ivParameterSpec)
            throws Exception {
        initialize();
        Cipher cipher =
                cipherMap.get(
                        cryptoAttribute.getName());
        if(ivParameterSpec != null)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        else
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(raw);
    }

    public static IvParameterSpec createZeroInitialVector(
            CryptoAttribute cryptoAttribute)
            throws Exception {
        initialize();
        Cipher cipher =
                cipherMap.get(
                        cryptoAttribute.getName());
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    public static byte[] decrypt(
            byte[] decrypted,
            Key secretKey,
            CryptoAttribute cryptoAttribute,
            IvParameterSpec ivParameterSpec)
            throws Exception {
        initialize();
        Cipher cipher =
                cipherMap.get(
                        cryptoAttribute.getName());
        if(ivParameterSpec != null)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        else
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(decrypted);
    }

    public static byte[] decrypt(
            byte[] decrypted,
            Key secretKey,
            CryptoAttribute cryptoAttribute)
            throws Exception {
        initialize();
        Cipher cipher =
                cipherMap.get(
                        cryptoAttribute.getName());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(decrypted);
    }
}
