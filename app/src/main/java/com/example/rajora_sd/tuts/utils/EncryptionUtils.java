package com.example.rajora_sd.tuts.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by rajora_sd on 08-06-2017.
 */

public class EncryptionUtils {

    public void encryptedWrite(File file, Context context) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(context.getCacheDir()+"/"+file.getName().substring(0, file.getName().lastIndexOf('.')) + ".enc");
        SecretKeySpec sks = new SecretKeySpec("1234567812345678".getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        int b;
        byte[] d = new byte[8];
        while((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        cos.flush();
        cos.close();
        fis.close();
    }

}
