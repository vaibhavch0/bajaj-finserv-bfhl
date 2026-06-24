package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of BfhlService.
 * Processes the input array and categorizes elements into:
 * - Even numbers
 * - Odd numbers
 * - Alphabets (converted to uppercase)
 * - Special characters
 * Computes sum of all numbers and the alternating-caps reversed concat string.
 */
@Service
public class BfhlServiceImpl implements BfhlService {

    // ─── User Identity Constants ────────────────────────────────────────────────
    // Update these values with your actual details before submission
    private static final String USER_ID      = "vaibhav_choudhary_22082005";
    private static final String EMAIL        = "vaibhav0909.be23@chitkara.edu.in";
    private static final String ROLL_NUMBER  = "2310990909";

    @Override
    public BfhlResponse process(BfhlRequest request) {
        List<String> data = request.getData();

        List<String> evenNumbers      = new ArrayList<>();
        List<String> oddNumbers       = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        long         totalSum         = 0;

        // ── Collect individual alpha chars for concat_string (in input order) ──
        // We iterate through every element; for numeric elements add to sum,
        // for alphabetic elements collect characters, otherwise special chars.
        List<Character> allAlphaChars = new ArrayList<>();

        for (String element : data) {
            if (isNumeric(element)) {
                long num = Long.parseLong(element);
                totalSum += num;
                if (num % 2 == 0) {
                    evenNumbers.add(element);
                } else {
                    oddNumbers.add(element);
                }
            } else if (isAlphabetic(element)) {
                // Store as uppercase in alphabets array
                alphabets.add(element.toUpperCase());
                // Collect individual chars (original case) for concat logic
                for (char c : element.toCharArray()) {
                    allAlphaChars.add(c);
                }
            } else {
                // Mixed or special — could be a mix of alpha + special, or purely special
                // Per the problem spec, elements that are not purely numeric and not purely
                // alphabetic are treated as special characters.
                specialChars.add(element);
            }
        }

        // ── Build concat_string ──────────────────────────────────────────────────
        // Reverse the collected alphabetical characters, then apply alternating caps
        // starting with uppercase at index 0 of the reversed sequence.
        String concatString = buildConcatString(allAlphaChars);

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(totalSum))
                .concatString(concatString)
                .build();
    }

    // ── Helpers ─────────────────────────────────────────────────────────────────

    /**
     * Returns true if the element string represents a valid integer (including negatives).
     */
    private boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        int start = 0;
        if (s.charAt(0) == '-') {
            if (s.length() == 1) return false;
            start = 1;
        }
        for (int i = start; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Returns true if ALL characters in the element are alphabetic.
     */
    private boolean isAlphabetic(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Builds the concat_string:
     * 1. Take all individual alpha characters collected in input order.
     * 2. Reverse the list.
     * 3. Apply alternating caps: index 0 → uppercase, index 1 → lowercase, etc.
     *
     * Example: chars [A, B, C, D, D, O, E] → reversed [E, O, D, D, C, B, A]
     *          alternating: E(upper) o(lower) D(upper) d(lower) C(upper) b(lower) A(upper) → "EoDdCbAa"
     */
    private String buildConcatString(List<Character> chars) {
        if (chars.isEmpty()) return "";

        // Reverse
        List<Character> reversed = new ArrayList<>(chars);
        java.util.Collections.reverse(reversed);

        // Alternating caps
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            if (i % 2 == 0) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
