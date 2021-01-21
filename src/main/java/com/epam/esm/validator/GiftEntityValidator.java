package com.epam.esm.validator;

import com.epam.esm.controller.UpdatingField;

import java.util.Arrays;

public class GiftEntityValidator {
    private static final String ID_REGEX = "^[1-9]\\d{0,18}$";
    private static final String NAME_REGEX = "^.{1,50}$";
    private static final String CERTIFICATE_DESCRIPTION_REGEX = "^.{1,250}$";
    private static final String INT_REGEX = "^[1-9]\\d{0,9}$";
    public static final String TAG_SPLITERATOR = ",";
    private static final String PRICE = "price";
    private static final String NAME = "name";
    private static final String CREATE_DATE = "create-date";
    private static final String LAST_UPDATE_DATE = "last-update-date";
    private static final String DURATION = "duration";
    private static final String DESC = "desc";
    private static final String ASC = "asc";

    private GiftEntityValidator() {
    }

    public static boolean correctOptionalParameters(String name,
                                                    String description,
                                                    String tagNames,
                                                    String sortType,
                                                    String direction,
                                                    Integer limit,
                                                    Integer offset) {
        if (name != null && !name.matches(NAME_REGEX)) {
            return false;
        }
        if (description != null && !description.matches(CERTIFICATE_DESCRIPTION_REGEX)) {
            return false;
        }
        if (tagNames != null && !Arrays.stream(tagNames.split(TAG_SPLITERATOR)).allMatch(t -> t.matches(NAME_REGEX))) {
            return false;
        }
            return correctSortType(sortType) &&
                    correctDirection(direction) &&
                    correctOptionalIntValue(limit) &&
                    correctOptionalIntValue(offset);
    }

    private static boolean correctSortType(String sortType) {
        return sortType == null || sortType.equals(PRICE) || sortType.equals(NAME) || sortType.equals(CREATE_DATE)
                || sortType.equals(LAST_UPDATE_DATE) || sortType.equals(DURATION);
    }

    private static boolean correctDirection(String direction) {
        return direction == null || direction.equals(ASC) || direction.equals(DESC);
    }

    public static boolean correctOptionalIntValue(Integer value) {
        return value == null || String.valueOf(value).matches(INT_REGEX);
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
