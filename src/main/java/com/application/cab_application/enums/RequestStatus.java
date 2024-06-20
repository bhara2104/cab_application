package com.application.cab_application.enums;

public enum RequestStatus {
    WAITING(1),
    ACCEPTED(2),
    STARTED(3),
    ENDED(4),
    CANCELLED(5);

    private final int code;

    RequestStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RequestStatus fromCode(int code) {
        for (RequestStatus requestStatus : RequestStatus.values()) {
            if (requestStatus.getCode() == code)
                return requestStatus;
        }
        throw new IllegalArgumentException("Unknown code " + code);
    }

}
