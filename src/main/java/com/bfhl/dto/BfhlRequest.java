package com.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Request DTO for the BFHL API.
 * Accepts a JSON body with a "data" array of strings.
 */
public class BfhlRequest {

    @NotNull(message = "data field must not be null")
    @JsonProperty("data")
    private List<String> data;

    public BfhlRequest() {}

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
