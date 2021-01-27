package com.epam.esm.controller;

/**
 * Class represents field of updating entity
 */
public class UpdatingField {
    private FieldName fieldName;
    private String fieldValue;

    public UpdatingField(FieldName fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public FieldName getFieldName() {
        return fieldName;
    }

    public void setFieldName(FieldName fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * The enum Field name.
     */
    public enum FieldName {
        NAME, DESCRIPTION, PRICE, DURATION
    }
}
