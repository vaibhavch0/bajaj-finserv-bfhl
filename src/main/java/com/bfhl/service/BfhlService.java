package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;

/**
 * Service interface defining the contract for BFHL data processing.
 */
public interface BfhlService {

    /**
     * Processes the incoming data array and returns categorized results.
     *
     * @param request the incoming request containing the data array
     * @return the processed response with categorized data
     */
    BfhlResponse process(BfhlRequest request);
}
