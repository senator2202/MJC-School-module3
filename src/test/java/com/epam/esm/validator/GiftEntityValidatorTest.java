package com.epam.esm.validator;

import com.epam.esm.controller.UpdatingField;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftEntityValidatorTest {

    static Stream<Arguments> correctIdArgs() {
        return Stream.of(
                Arguments.of(true, new long[]{1L}),
                Arguments.of(false, new long[]{0L}),
                Arguments.of(false, new long[]{-22L}),
                Arguments.of(true, new long[]{22L, 155L}),
                Arguments.of(false, new long[]{22L, -22L})
        );
    }

    static Stream<Arguments> correctTagNameArgs() {
        return Stream.of(
                Arguments.of("Book", true),
                Arguments.of("", false),
                Arguments.of("111111111111111111111111111111111111111111111111111111111111111111111111111111111", false)
        );
    }

    static Stream<Arguments> correctCertificateDescriptionArgs() {
        return Stream.of(
                Arguments.of("Description", true),
                Arguments.of("", false),
                Arguments.of("111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "11111111111111111111111111111111111111111", false)
        );
    }

    static Stream<Arguments> correctUpdateFieldParametersArgs() {
        return Stream.of(
                Arguments.of(UpdatingField.FieldName.NAME, "Book", true),
                Arguments.of(UpdatingField.FieldName.NAME, "", false),
                Arguments.of(UpdatingField.FieldName.NAME, null, false),
                Arguments.of(UpdatingField.FieldName.NAME, "11111111111111111111111111111111111111111111111111111" +
                        "1111111111111111111", false),
                Arguments.of(UpdatingField.FieldName.DESCRIPTION, "Description", true),
                Arguments.of(UpdatingField.FieldName.DESCRIPTION, "", false),
                Arguments.of(UpdatingField.FieldName.DESCRIPTION, null, false),
                Arguments.of(UpdatingField.FieldName.DESCRIPTION, "1111111111111111111111111111111111111111111111111" +
                        "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                        "1111111111111111111111111111111111111111111111111111111111111111111111111111111", false),
                Arguments.of(UpdatingField.FieldName.PRICE, "123", true),
                Arguments.of(UpdatingField.FieldName.PRICE, "-123", false),
                Arguments.of(UpdatingField.FieldName.PRICE, "", false),
                Arguments.of(UpdatingField.FieldName.PRICE, null, false),
                Arguments.of(UpdatingField.FieldName.DURATION, "123", true),
                Arguments.of(UpdatingField.FieldName.DURATION, "-123", false),
                Arguments.of(UpdatingField.FieldName.DURATION, "", false),
                Arguments.of(UpdatingField.FieldName.DURATION, null, false)
        );
    }

    static Stream<Arguments> correctTagNamesArgs() {
        return Stream.of(
                Arguments.of("Book,English", true),
                Arguments.of("English,111111111111111111111111111111111111111111111111111111111111111111111", false)
        );
    }

    @ParameterizedTest
    @MethodSource("correctIdArgs")
    void correctId(boolean result, long... ids) {
        assertEquals(GiftEntityValidator.correctId(ids), result);
    }

    @ParameterizedTest
    @MethodSource("correctTagNameArgs")
    void correctTagName(String tagName, boolean result) {
        assertEquals(GiftEntityValidator.correctTagName(tagName), result);
    }

    @ParameterizedTest
    @MethodSource("correctCertificateDescriptionArgs")
    void correctCertificateDescription(String description, boolean result) {
        assertEquals(GiftEntityValidator.correctCertificateDescription(description), result);
    }

    @ParameterizedTest
    @MethodSource("correctUpdateFieldParametersArgs")
    void correctUpdateFieldParameters(UpdatingField.FieldName fieldName, String fieldValue, boolean result) {
        assertEquals(GiftEntityValidator.correctUpdateFieldParameters(fieldName, fieldValue), result);
    }

    @ParameterizedTest
    @MethodSource("correctTagNamesArgs")
    void correctTagNames(String tagNames, boolean result) {
        assertEquals(GiftEntityValidator.correctTagNames(tagNames), result);
    }
}