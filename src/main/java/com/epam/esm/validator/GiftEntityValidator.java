package com.epam.esm.validator;

public class GiftEntityValidator {
    private static final String ID_REGEX = "^[1-9]\\d{0,18}$";
    private static final String NAME_REGEX = "^.{1,50}$";
    private static final String CERTIFICATE_DESCRIPTION_REGEX = "^.{1,250}$";

    public static boolean correctId(String id) {
        return id.matches(ID_REGEX);
    }

    public static boolean correctTagName(String tagName) {
        return tagName.matches(NAME_REGEX);
    }

    public static boolean correctCertificateDescription(String description) {
        return description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }
}
