package com.example.config.log.mdc;

public enum MdcKey {

    ENVIRONMENT("env"),
    USERNAME("usrName"),
    AUDIT_USERNAME("auditUsrName"),
    TRACE_ID("sollicitationID"), //TODO
    REQUEST_URL("resourceURI"),
    REQUEST_METHOD("resourceAction"),
    REMOTE_ADDRESS("src"),
    RESPONSE_TIME("responseTime"),
    STATUS_CODE("statusCode"),
    STATUS_HTTP_CODE("statusHttpCode");

    private final String label;

    MdcKey(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}