package com.epam.esm.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeUtilityTest {

    @Test
    void getCurrentDateIso() {
        String regex = "^202\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d+$";
        assertTrue(DateTimeUtility.getCurrentDateIso().matches(regex));
    }
}