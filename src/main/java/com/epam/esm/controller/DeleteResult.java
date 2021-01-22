package com.epam.esm.controller;

class DeleteResult {
    private boolean result;

    public DeleteResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
