package com.cmbccd.ulms.common.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    @Test
    void buildOrderByClauseValid() {
        assertEquals("data_time desc", Util.buildOrderByClause("dataTime", "desc"));
        assertEquals("data_time asc", Util.buildOrderByClause("dataTime", "asc"));
        assertEquals("data_time asc", Util.buildOrderByClause("dataTime", null));
        assertEquals("data_time asc", Util.buildOrderByClause("data_time", "ASC"));
    }

    @Test
    void buildOrderByClauseRejectsInjection() {
        assertNull(Util.buildOrderByClause("dataTime; DROP TABLE X", "desc"));
        assertNull(Util.buildOrderByClause("dataTime)", "desc"));
        assertNull(Util.buildOrderByClause("dataTime", "desc; DROP TABLE X"));
        assertNull(Util.buildOrderByClause("", "desc"));
        assertNull(Util.buildOrderByClause(null, "desc"));
        assertNull(Util.buildOrderByClause("data time", "desc"));
    }

    @Test
    void buildOrderByClauseOrderTypeTrimAndCase() {
        assertEquals("data_time desc", Util.buildOrderByClause("dataTime", " DESC "));
        assertEquals("data_time asc", Util.buildOrderByClause("dataTime", " Asc "));
        assertEquals("data_time asc", Util.buildOrderByClause("dataTime", ""));
        assertNull(Util.buildOrderByClause("dataTime", "random"));
    }

    @Test
    void isValidSqlIdentifierDetection() {
        assertTrue(Util.isValidSqlIdentifier("CUS_FLOW_CASE"));
        assertTrue(Util.isValidSqlIdentifier("case_id"));
        assertTrue(Util.isValidSqlIdentifier("FLOW_STATUS"));
        assertFalse(Util.isValidSqlIdentifier("CUS_FLOW_CASE; DROP TABLE X"));
        assertFalse(Util.isValidSqlIdentifier("case-id"));
        assertFalse(Util.isValidSqlIdentifier(""));
        assertFalse(Util.isValidSqlIdentifier(null));
    }
}
