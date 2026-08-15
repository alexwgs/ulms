package com.cmbccd.ulms.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密工具。
 *
 * <p>历史密码使用 {@link Util#getsecret(String)} / {@link Util#getdesecret(String)}
 * 自定义字符位移算法存储（弱加密）。此处统一迁移到 BCrypt：
 * <ul>
 *   <li>新密码一律使用 {@link #encode(String)} 生成 BCrypt 哈希；</li>
 *   <li>校验时对历史密文自动回退到旧算法，保证存量用户可登录；</li>
 *   <li>登录成功后通过 {@link #needsRehash(String)} 判断并透明升级为 BCrypt。</li>
 * </ul>
 */
public class PasswordUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordUtil() {
    }

    /**
     * 判断存储的密文是否已是 BCrypt 格式。
     */
    public static boolean isBcrypt(String storedPassword) {
        return storedPassword != null
                && (storedPassword.startsWith("$2a$")
                || storedPassword.startsWith("$2b$")
                || storedPassword.startsWith("$2y$"));
    }

    /**
     * 校验密码：BCrypt 格式走 BCrypt 比对，旧算法格式回退到自定义算法。
     */
    public static boolean matches(String rawPassword, String storedPassword) {
        if (Util.isNullorEmpty(storedPassword) || rawPassword == null) {
            return false;
        }
        if (isBcrypt(storedPassword)) {
            return ENCODER.matches(rawPassword, storedPassword);
        }
        // 兼容历史自定义算法密文
        return rawPassword.equals(Util.getdesecret(storedPassword));
    }

    /**
     * 生成 BCrypt 哈希。
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 是否需要升级为 BCrypt（即当前存储的是旧算法密文）。
     */
    public static boolean needsRehash(String storedPassword) {
        return !Util.isNullorEmpty(storedPassword) && !isBcrypt(storedPassword);
    }
}
