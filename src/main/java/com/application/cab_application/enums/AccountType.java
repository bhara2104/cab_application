package com.application.cab_application.enums;

public enum AccountType {
    RIDER(1),
    DRIVER(2);

    private final int code;

    AccountType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AccountType fromCode(int code) {
        for (AccountType status : AccountType.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
