package com.cmbccd.ulms.common.util;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link AESUtil} 解密单元测试：与前端约定的密钥/IV 保持一致，验证加解密往返。
 */
public class AESUtilTest {

    @Test
    void decryptMatchesKnownCipher() throws Exception {
        String plain = "111111";
        String secret = "cmbccdWH-wei6130";
        String iv = "1234567890123456";

        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        String encrypted = Base64.getEncoder()
                .encodeToString(cipher.doFinal(plain.getBytes("UTF-8")));

        assertEquals(plain, AESUtil.decrypt(encrypted));
    }
}
