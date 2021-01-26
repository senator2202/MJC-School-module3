package com.epam.esm.validator;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class - validator. Validates DTO objects and their fields.
 */
public class GiftEntityValidator {

    /**
     * The constant TAG_SPLITERATOR.
     */
    public static final String TAG_SPLITERATOR = ",";
    private static final String ID_REGEX = "^[1-9]\\d{0,18}$";
    private static final String NAME_REGEX = "^.{1,50}$";
    private static final String CERTIFICATE_DESCRIPTION_REGEX = "^.{1,250}$";
    private static final String POSITIVE_INT_REGEX = "^[1-9]\\d{0,9}$";
    private static final String PRICE = "price";
    private static final String NAME = "name";
    private static final String CREATE_DATE = "create-date";
    private static final String LAST_UPDATE_DATE = "last-update-date";
    private static final String DURATION = "duration";
    private static final String DESC = "desc";
    private static final String ASC = "asc";

    private GiftEntityValidator() {
    }

    /**
     * Method validates parameters, that must be either null or match regular expressions
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @param sortType    the sort type
     * @param direction   the direction
     * @param limit       the limit
     * @param offset      the offset
     * @return the boolean
     */
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
                correctOptionalNotNegativeIntValue(limit) &&
                correctOptionalNotNegativeIntValue(offset);
    }

    /**
     * Method checks if sortType has correct value
     */
    private static boolean correctSortType(String sortType) {
        return sortType == null || sortType.equals(PRICE) || sortType.equals(NAME) || sortType.equals(CREATE_DATE)
                || sortType.equals(LAST_UPDATE_DATE) || sortType.equals(DURATION);
    }

    /**
     * Method checks if sort direction has correct value
     */
    private static boolean correctDirection(String direction) {
        return direction == null || direction.equals(ASC) || direction.equals(DESC);
    }

    /**
     * Method checks if Integer value either null or > 0
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean correctOptionalNotNegativeIntValue(Integer value) {
        return value == null || value == 0 || String.valueOf(value).matches(POSITIVE_INT_REGEX);
    }

    /**
     * Correct ids boolean.
     *
     * @param ids the ids
     * @return the boolean
     */
    public static boolean correctId(long... ids) {
        return Arrays.stream(ids).allMatch(id -> String.valueOf(id).matches(ID_REGEX));
    }

    /**
     * Correct tag name boolean.
     *
     * @param tagName the tag name
     * @return the boolean
     */
    public static boolean correctTagName(String tagName) {
        return tagName != null && tagName.matches(NAME_REGEX);
    }

    /**
     * Correct certificate description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public static boolean correctCertificateDescription(String description) {
        return description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }

    /**
     * Correct update field parameters boolean.
     *
     * @param fieldName  the field name
     * @param fieldValue the field value
     * @return the boolean
     */
    public static boolean correctUpdateFieldParameters(UpdatingField.FieldName fieldName, String fieldValue) {
        if (fieldName == UpdatingField.FieldName.NAME) {
            return fieldValue != null && fieldValue.matches(NAME_REGEX);
        }
        if (fieldName == UpdatingField.FieldName.DESCRIPTION) {
            return fieldValue != null && fieldValue.matches(CERTIFICATE_DESCRIPTION_REGEX);
        }
        if (fieldName == UpdatingField.FieldName.PRICE || fieldName == UpdatingField.FieldName.DURATION) {
            return fieldValue != null && fieldValue.matches(POSITIVE_INT_REGEX);
        }
        return false;
    }

    /**
     * Correct tag names boolean.
     *
     * @param tagNames the tag names
     * @return the boolean
     */
    public static boolean correctTagNames(String tagNames) {
        return Arrays.stream(tagNames.split(TAG_SPLITERATOR)).allMatch(GiftEntityValidator::correctTagName);
    }

    /**
     * Correct tag boolean.
     *
     * @param tag the tag
     * @return the boolean
     */
    public static boolean correctTag(TagDTO tag) {
        return tag != null && tag.getName() != null && tag.getName().matches(NAME_REGEX);
    }

    /**
     * Correct gift certificate boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     */
    public static boolean correctGiftCertificate(GiftCertificateDTO certificate) {
        return correctCertificateName(certificate.getName()) &&
                correctOptionalDescription(certificate.getDescription()) &&
                correctOptionalPrice(certificate.getPrice()) &&
                correctOptionalDuration(certificate.getDuration()) &&
                correctTags(certificate.getTags());
    }

    private static boolean correctCertificateName(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    /**
     * Correct optional certificate name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean correctOptionalCertificateName(String name) {
        return name == null || name.matches(NAME_REGEX);
    }


    /**
     * Correct optional description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public static boolean correctOptionalDescription(String description) {
        return description == null || description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }

    /**
     * Correct optional price boolean.
     *
     * @param price the price
     * @return the boolean
     */
    public static boolean correctOptionalPrice(BigDecimal price) {
        return price == null || price.doubleValue() > 0;
    }

    /**
     * Correct optional duration boolean.
     *
     * @param duration the duration
     * @return the boolean
     */
    public static boolean correctOptionalDuration(Integer duration) {
        return duration == null || String.valueOf(duration).matches(POSITIVE_INT_REGEX);
    }

    /**
     * Correct tags boolean.
     *
     * @param tags the tags
     * @return the boolean
     */
    public static boolean correctTags(List<TagDTO> tags) {
        return tags == null || tags.stream().filter(Objects::nonNull).allMatch(GiftEntityValidator::correctTag);
    }
}
