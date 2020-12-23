package com.epam.esm.validator;

public class GiftEntityValidator {
    private static final String ID_REGEX = "^[1-9]\\d{0,18}$";
    private static final String NAME_REGEX = "^.{1,50}$";
    private static final String CERTIFICATE_DESCRIPTION_REGEX = "^.{1,250}$";
    private static final String INT_REGEX = "^[1-9]\\d{0,9}$";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_DURATION = "duration";

    public static boolean correctId(String id) {
        return id.matches(ID_REGEX);
    }

    public static boolean correctTagName(String tagName) {
        return tagName.matches(NAME_REGEX);
    }

    public static boolean correctCertificateDescription(String description) {
        return description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }

    public static boolean correctUpdateFieldParameters(String fieldName, String fieldValue) {
        if (fieldName != null && fieldName.equals(FIELD_NAME)) {
            return fieldValue != null && fieldValue.matches(NAME_REGEX);
        }
        if (fieldName != null && fieldName.equals(FIELD_DESCRIPTION)) {
            return fieldValue != null && fieldValue.matches(CERTIFICATE_DESCRIPTION_REGEX);
        }
        if (fieldName != null && (fieldName.equals(FIELD_PRICE) || fieldName.equals(FIELD_DURATION))) {
            return fieldValue != null && fieldValue.matches(INT_REGEX);
        }
        return false;
    }
}
