package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for BfhlServiceImpl.
 * Covers all examples from the problem statement and edge cases.
 */
class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
    }

    // ── Helper ───────────────────────────────────────────────────────────────────

    private BfhlRequest request(String... items) {
        BfhlRequest req = new BfhlRequest();
        req.setData(Arrays.asList(items));
        return req;
    }

    // ── Example A ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example A: [\"a\", \"1\", \"334\", \"4\", \"R\", \"$\"]")
    void testExampleA() {
        BfhlResponse response = service.process(request("a", "1", "334", "4", "R", "$"));

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).containsExactly("1");
        assertThat(response.getEvenNumbers()).containsExactly("334", "4");
        assertThat(response.getAlphabets()).containsExactly("A", "R");
        assertThat(response.getSpecialCharacters()).containsExactly("$");
        assertThat(response.getSum()).isEqualTo("339");
        assertThat(response.getConcatString()).isEqualTo("Ra");
    }

    // ── Example B ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example B: [\"2\", \"a\", \"y\", \"4\", \"&\", \"-\", \"*\", \"5\", \"92\", \"b\"]")
    void testExampleB() {
        BfhlResponse response = service.process(request("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).containsExactly("5");
        assertThat(response.getEvenNumbers()).containsExactly("2", "4", "92");
        assertThat(response.getAlphabets()).containsExactly("A", "Y", "B");
        assertThat(response.getSpecialCharacters()).containsExactly("&", "-", "*");
        assertThat(response.getSum()).isEqualTo("103");
        assertThat(response.getConcatString()).isEqualTo("ByA");
    }

    // ── Example C ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example C: [\"A\", \"ABCD\", \"DOE\"]")
    void testExampleC() {
        BfhlResponse response = service.process(request("A", "ABCD", "DOE"));

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getAlphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(response.getSpecialCharacters()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEqualTo("EoDdCbAa");
    }

    // ── Edge Cases ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Empty data array returns zeroed response")
    void testEmptyData() {
        BfhlResponse response = service.process(request());

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getAlphabets()).isEmpty();
        assertThat(response.getSpecialCharacters()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Only special characters")
    void testOnlySpecialChars() {
        BfhlResponse response = service.process(request("!", "@", "#", "$"));

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getEvenNumbers()).isEmpty();
        assertThat(response.getAlphabets()).isEmpty();
        assertThat(response.getSpecialCharacters()).containsExactly("!", "@", "#", "$");
        assertThat(response.getSum()).isEqualTo("0");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Only numbers — even and odd split correctly")
    void testOnlyNumbers() {
        BfhlResponse response = service.process(request("2", "3", "10", "7", "100"));

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getOddNumbers()).containsExactly("3", "7");
        assertThat(response.getEvenNumbers()).containsExactly("2", "10", "100");
        assertThat(response.getAlphabets()).isEmpty();
        assertThat(response.getSpecialCharacters()).isEmpty();
        assertThat(response.getSum()).isEqualTo("122");
        assertThat(response.getConcatString()).isEmpty();
    }

    @Test
    @DisplayName("Single alphabetic character")
    void testSingleAlpha() {
        BfhlResponse response = service.process(request("z"));

        assertThat(response.getAlphabets()).containsExactly("Z");
        assertThat(response.getConcatString()).isEqualTo("Z");
    }

    @Test
    @DisplayName("Zero is treated as even number")
    void testZeroIsEven() {
        BfhlResponse response = service.process(request("0"));

        assertThat(response.getEvenNumbers()).containsExactly("0");
        assertThat(response.getOddNumbers()).isEmpty();
        assertThat(response.getSum()).isEqualTo("0");
    }

    @Test
    @DisplayName("Response always has userId, email and rollNumber set")
    void testUserIdentityFieldsPresent() {
        BfhlResponse response = service.process(request("x"));

        assertThat(response.getUserId()).isNotBlank();
        assertThat(response.getEmail()).isNotBlank();
        assertThat(response.getRollNumber()).isNotBlank();
    }
}
