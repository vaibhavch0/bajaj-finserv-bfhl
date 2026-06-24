package com.bfhl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for BfhlController.
 * Verifies the HTTP layer, JSON serialization, and error handling end-to-end.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BfhlControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // ── Example A ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /bfhl — Example A returns 200 with correct body")
    void testExampleA() throws Exception {
        String body = objectMapper.writeValueAsString(
                Map.of("data", List.of("a", "1", "334", "4", "R", "$")));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", containsInRelativeOrder("1")))
                .andExpect(jsonPath("$.even_numbers", containsInRelativeOrder("334", "4")))
                .andExpect(jsonPath("$.alphabets", containsInRelativeOrder("A", "R")))
                .andExpect(jsonPath("$.special_characters", containsInRelativeOrder("$")))
                .andExpect(jsonPath("$.sum", is("339")))
                .andExpect(jsonPath("$.concat_string", is("Ra")));
    }

    // ── Example B ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /bfhl — Example B returns 200 with correct body")
    void testExampleB() throws Exception {
        String body = objectMapper.writeValueAsString(
                Map.of("data", List.of("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", containsInRelativeOrder("5")))
                .andExpect(jsonPath("$.even_numbers", containsInRelativeOrder("2", "4", "92")))
                .andExpect(jsonPath("$.alphabets", containsInRelativeOrder("A", "Y", "B")))
                .andExpect(jsonPath("$.special_characters", containsInRelativeOrder("&", "-", "*")))
                .andExpect(jsonPath("$.sum", is("103")))
                .andExpect(jsonPath("$.concat_string", is("ByA")));
    }

    // ── Example C ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /bfhl — Example C returns 200 with correct body")
    void testExampleC() throws Exception {
        String body = objectMapper.writeValueAsString(
                Map.of("data", List.of("A", "ABCD", "DOE")));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.odd_numbers", hasSize(0)))
                .andExpect(jsonPath("$.even_numbers", hasSize(0)))
                .andExpect(jsonPath("$.alphabets", containsInRelativeOrder("A", "ABCD", "DOE")))
                .andExpect(jsonPath("$.special_characters", hasSize(0)))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("EoDdCbAa")));
    }

    // ── Error Handling ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /bfhl — missing 'data' field returns 400 with is_success false")
    void testMissingDataField() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.error", containsString("Validation failed")));
    }

    @Test
    @DisplayName("POST /bfhl — malformed JSON returns 400 with is_success false")
    void testMalformedJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)));
    }

    @Test
    @DisplayName("POST /bfhl — empty data array returns 200 with zeroed response")
    void testEmptyDataArray() throws Exception {
        String body = objectMapper.writeValueAsString(Map.of("data", List.of()));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.sum", is("0")))
                .andExpect(jsonPath("$.concat_string", is("")));
    }

    @Test
    @DisplayName("GET /bfhl — returns 200 with operation_code 1")
    void testGetOperationCode() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code", is(1)));
    }

    @Test
    @DisplayName("GET / — returns 200 with welcome message")
    void testGetRoot() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", containsString("running")));
    }

    @Test
    @DisplayName("POST /bfhl — unsupported Content-Type returns 415")
    void testUnsupportedMediaType() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("some plain text"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.error", containsString("Unsupported Content-Type")));
    }

    @Test
    @DisplayName("GET /health — returns 200 with status OK")
    void testGetHealthStatus() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")));
    }
}
