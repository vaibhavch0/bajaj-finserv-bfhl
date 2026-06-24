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
@RequestMapping("/bfhl")
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
    @PostMapping
    public ResponseEntity<BfhlResponse> process(@Valid @RequestBody BfhlRequest request) {
        BfhlResponse response = bfhlService.process(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /bfhl
     * Friendly message for browser/curl GET requests.
     */
    @GetMapping
    public ResponseEntity<java.util.Map<String, String>> info() {
        return ResponseEntity.ok(java.util.Map.of(
                "message", "BFHL API is running. Use POST /bfhl with a JSON body: {\"data\": [...]}",
                "status", "UP"
        ));
    }
}
