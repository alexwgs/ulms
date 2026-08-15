package com.cmbccd.ulms.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link PasswordUtil} 单元测试：BCrypt 加解密、历史算法兼容、空值处理。
 */
public class PasswordUtilTest {

    @Test
    void bcryptEncodeAndMatches() {
        String hash = PasswordUtil.encode("111111");
        assertTrue(PasswordUtil.isBcrypt(hash));
        assertTrue(PasswordUtil.matches("111111", hash));
        assertFalse(PasswordUtil.matches("wrong", hash));
        assertFalse(PasswordUtil.needsRehash(hash));
    }

    @Test
    void bcryptSaltProducesDifferentHashes() {
        assertNotEquals(PasswordUtil.encode("111111"), PasswordUtil.encode("111111"));
    }

    @Test
    void legacyAlgorithmCompat() {
        // 模拟历史弱加密存储的密文
        String legacy = Util.getsecret("111111");
        assertFalse(PasswordUtil.isBcrypt(legacy));
        assertTrue(PasswordUtil.needsRehash(legacy));
        assertTrue(PasswordUtil.matches("111111", legacy));
        assertFalse(PasswordUtil.matches("222222", legacy));
    }

    @Test
    void matchesHandlesNullOrEmpty() {
        assertFalse(PasswordUtil.matches("111111", null));
        assertFalse(PasswordUtil.matches("111111", ""));
        assertFalse(PasswordUtil.matches(null, "$2a$10$abcdefghijklmnopqrstuv"));
        assertFalse(PasswordUtil.needsRehash(null));
        assertFalse(PasswordUtil.needsRehash(""));
        assertFalse(PasswordUtil.isBcrypt(null));
    }

    @Test
    void roundTripOfLegacySecret() {
        assertEquals("111111", Util.getdesecret(Util.getsecret("111111")));
    }
}
