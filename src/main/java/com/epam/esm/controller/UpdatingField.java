package com.epam.esm.controller;

public class UpdatingField {
    private FieldName fieldName;
    private String fieldValue;

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

    public enum FieldName {
        NAME, DESCRIPTION, PRICE, DURATION
    }
}
