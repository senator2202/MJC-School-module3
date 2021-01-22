package com.epam.esm.validator;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.TagDTO;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static boolean correctTag(TagDTO tag) {
        return tag.getName() != null && tag.getName().matches(NAME_REGEX);
    }

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

    public static boolean correctOptionalCertificateName(String name) {
        return name == null || name.matches(NAME_REGEX);
    }


    public static boolean correctOptionalDescription(String description) {
        return description == null || description.matches(CERTIFICATE_DESCRIPTION_REGEX);
    }

    public static boolean correctOptionalPrice(BigDecimal price) {
        return price == null || price.doubleValue() > 0;
    }

    public static boolean correctOptionalDuration(Integer duration) {
        return duration == null || String.valueOf(duration).matches(INT_REGEX);
    }

    public static boolean correctTags(List<EntityModel<TagDTO>> tags) {
        return tags == null ||
                tags.stream()
                        .map(EntityModel::getContent)
                        .filter(Objects::nonNull)
                        .allMatch(GiftEntityValidator::correctTag);
    }
}
