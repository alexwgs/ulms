package com.cmbccd.ulms.common.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link Util} 通用工具方法单元测试。
 */
public class UtilTest {

    @Test
    void secretRoundTrip() {
        assertEquals("111111", Util.getdesecret(Util.getsecret("111111")));
        assertEquals("admin123", Util.getdesecret(Util.getsecret("admin123")));
    }

    @Test
    void camelToUnderline() {
        assertEquals("case_status", Util.camel4underline("caseStatus"));
        assertEquals("approve_level", Util.camel4underline("approveLevel"));
        assertEquals("", Util.camel4underline(""));
    }

    @Test
    void underlineToCamel() {
        assertEquals("caseStatus", Util.underlineToCamel("case_status"));
        assertEquals("approveLevel", Util.underlineToCamel("approve_level"));
    }

    @Test
    void isIntegerDetection() {
        assertTrue(Util.isInteger("123"));
        assertTrue(Util.isInteger("-5"));
        assertTrue(Util.isInteger("+7"));
        assertFalse(Util.isInteger("1.5"));
        assertFalse(Util.isInteger("abc"));
        assertFalse(Util.isInteger(""));
    }

    @Test
    void retainTwoDecimalRounding() {
        assertEquals(3.14, Util.retainTwoDecimal(new BigDecimal("3.14159")), 0.0001);
        assertEquals(2.50, Util.retainTwoDecimal(new BigDecimal("2.5")), 0.0001);
    }

    @Test
    void nullOrEmptyCheck() {
        assertTrue(Util.isNullorEmpty((String) null));
        assertTrue(Util.isNullorEmpty(""));
        assertFalse(Util.isNullorEmpty("a"));
    }
}
