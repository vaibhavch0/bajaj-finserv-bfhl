package com.bfhl.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Generic error response DTO returned on validation or processing failures.
 */
public class ErrorResponse {

    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("error")
    private String error;

    private ErrorResponse() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ErrorResponse r = new ErrorResponse();

        public Builder isSuccess(boolean v) { r.isSuccess = v; return this; }
        public Builder error(String v)      { r.error = v; return this; }

        public ErrorResponse build() { return r; }
    }

    @JsonIgnore
    public boolean isSuccess() { return isSuccess; }
    public String getError()   { return error; }
}
