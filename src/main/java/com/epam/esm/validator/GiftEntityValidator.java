package com.epam.esm.validator;

import com.epam.esm.controller.UpdatingField;

import java.util.Arrays;

public class GiftEntityValidator {
    private static final String ID_REGEX = "^[1-9]\\d{0,18}$";
    private static final String NAME_REGEX = "^.{1,50}$";
    private static final String CERTIFICATE_DESCRIPTION_REGEX = "^.{1,250}$";
    private static final String INT_REGEX = "^[1-9]\\d{0,9}$";
    private static final String TAG_SPLITERATOR = ",";

    private GiftEntityValidator() {
    }

    public static boolean correctId(long... ids) {
        return Arrays.stream(ids).allMatch(id -> String.valueOf(id).matches(ID_REGEX));
    }

    public static boolean correctTagName(String tagName) {
        return tagName.matches(NAME_REGEX);
    }

    public static boolean correctCertificateDescription(String description) {
        return description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }

    public static boolean correctUpdateFieldParameters(UpdatingField.FieldName fieldName, String fieldValue) {
        if (fieldName == UpdatingField.FieldName.NAME) {
            return fieldValue != null && fieldValue.matches(NAME_REGEX);
        }
        if (fieldName == UpdatingField.FieldName.DESCRIPTION) {
            return fieldValue != null && fieldValue.matches(CERTIFICATE_DESCRIPTION_REGEX);
        }
        if (fieldName == UpdatingField.FieldName.PRICE || fieldName == UpdatingField.FieldName.DURATION) {
            return fieldValue != null && fieldValue.matches(INT_REGEX);
        }
        return false;
    }

    public static boolean correctTagNames(String tagNames) {
        return Arrays.stream(tagNames.split(TAG_SPLITERATOR)).allMatch(GiftEntityValidator::correctTagName);
    }
}
