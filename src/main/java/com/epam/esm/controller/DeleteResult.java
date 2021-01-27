package com.epam.esm.controller;

/**
 * Class represents the result of deleting project entity from DB
 */
class DeleteResult {

    /**
     * Result of Entity deleting
     */
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
