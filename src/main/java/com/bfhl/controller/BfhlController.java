package com.bfhl.controller;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller exposing the BFHL endpoint.
 * POST /bfhl — processes an array of strings and returns categorized data.
 */
@RestController
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * POST /bfhl
     * Accepts a JSON body with a "data" array of strings.
     * Returns categorized numbers, alphabets, special chars, sum, and concat_string.
     *
     * @param request validated request body
     * @return 200 OK with BfhlResponse
     */
    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> process(@Valid @RequestBody BfhlRequest request) {
        BfhlResponse response = bfhlService.process(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /bfhl
     * Returns operation code as per BFHL requirements.
     */
    @GetMapping("/bfhl")
    public ResponseEntity<java.util.Map<String, Object>> getOperationCode() {
        return ResponseEntity.ok(java.util.Map.of("operation_code", 1));
    }

    /**
     * GET /health
     * Returns service health status.
     */
    @GetMapping("/health")
    public ResponseEntity<java.util.Map<String, String>> health() {
        return ResponseEntity.ok(java.util.Map.of("status", "OK"));
    }

    /**
     * GET /
     * Root endpoint to prevent "No static resource" errors when visiting the primary URL.
     */
    @GetMapping("/")
    public ResponseEntity<java.util.Map<String, String>> welcome() {
        return ResponseEntity.ok(java.util.Map.of(
                "message", "BFHL REST API is running. Send a POST request to /bfhl with a JSON body: {\"data\": [...]}",
                "get_endpoint", "/bfhl"
        ));
    }
}
