package com.sbiapiservice;



import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/sbiutils")
public class SbiAPIService {
    private SbiAPIService SbiAPIService;
    private String jsonrequeststring;
    private String RandomKey;

    @GetMapping("generateRandomKeydemo")
    public String  generateRandomKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");keygen.init(256);
        byte[] key = keygen.generateKey().getEncoded();
        String ss=Base64Encode(key);;
        return ss;
    }
    @PostMapping("aesEncrypt")
    public String aesDecryptDetails(@RequestBody SbiAPIService aesencrypt){
        this.SbiAPIService=aesencrypt;

        String s = aesencrypt.jsonrequeststring;//"ABCD";
        String skey = aesencrypt.RandomKey;//"021VWKsZ8bGGk2dm6ytMhCI4wvWP4Om9KyQQWGzUDZM=";
        byte[] byteArray1 = s.getBytes();
        String enc = encryptnew(byteArray1,skey);
        return  enc;
    }



    public String encryptnew(byte[] plaintext,String skey) {
        String encryptedText = null; byte[] cipherText = null;
        try {
            byte[] decodedKeyBytes = Base64.getDecoder().decode(skey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKeyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            IvParameterSpec ivSpec = new IvParameterSpec(Arrays.copyOfRange(decodedKeyBytes, 0, 12));
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, ivSpec.getIV());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
            cipherText = cipher.doFinal(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        encryptedText = Base64Encode(cipherText); // Assuming customBase64Encode is defined
        return encryptedText;
    }

    private byte[] Base64Decode(String input) {
        // Implement your custom Base64 decoding logic here
        return Base64.getDecoder().decode(input);
    }

    private String Base64Encode(byte[] input) {
        // Implement your custom Base64 encoding logic here
        // For example, you can use Base64.getUrlEncoder() for URL-safe encoding
        return Base64.getEncoder().encodeToString(input);
    }
}
