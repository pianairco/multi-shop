package ir.piana.dev.secure;

import ir.piana.dev.secure.crypto.CryptoAttribute;
import ir.piana.dev.secure.crypto.CryptoMaker;
import ir.piana.dev.secure.util.Base64Converter;
import ir.piana.dev.secure.util.HexConverter;
import ir.piana.dev.secure.key.SecretKeyAlgorithm;
import ir.piana.dev.secure.key.SecretKeyMaker;

import javax.crypto.SecretKey;

/**
 * @author MJ.Rahmati 4/26/2022-12:18 PM
 */
public class Aes128Cryptor {
    /**
     * mode=?1;data=?2;key=?3
     * ?1: null|empty|enc=encryption *** dec=decryption
     */
    /*public static void main(String[] args) throws Exception {
        String data = System.getenv("data");
        String key = System.getenv("key");
        String mode = System.getenv("mode");

        if (mode == null || mode.isEmpty() || mode.equalsIgnoreCase("enc")) {
            System.out.println(encryptEcbPkcs5Padding(data, key));
        } else if(mode.equalsIgnoreCase("dec")) {
            System.out.println(decryptEcbPkcs5Padding(data, key));
        }
    }*/

    public static String encryptEcbPkcs5Padding(String data, String key) throws Exception {
        SecretKey secretKey = SecretKeyMaker
                .make(HexConverter.fromHexString(key), SecretKeyAlgorithm.AES_128);
        String hexData = HexConverter.toHexString(data.getBytes(), true);

        byte[] encrypted = CryptoMaker.encrypt(
                HexConverter.fromHexString(hexData),
                secretKey,
                CryptoAttribute.AES_ECB_PKCS_5_PADDING);

        String hexEncoded = HexConverter.toHexString(encrypted, true);
        return Base64Converter.toBase64String(hexEncoded.getBytes());
    }

    public static String decryptEcbPkcs5Padding(String encryptedData, String key) throws Exception {
        SecretKey secretKey = SecretKeyMaker
                .make(HexConverter.fromHexString(key), SecretKeyAlgorithm.AES_128);
        String hex = Base64Converter.fromBase64(encryptedData);

        byte[] decrypted = CryptoMaker.decrypt(
                HexConverter.fromHexString(hex),
                secretKey,
                CryptoAttribute.AES_ECB_PKCS_5_PADDING);

        String hexEncoded = new String(decrypted);
        return hexEncoded;
    }
}
