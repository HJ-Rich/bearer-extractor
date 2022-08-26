package com.projectrich.bearerextractor;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

class BearerExtractorTest {

    private static final String VALID_JWT_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
            + "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ."
            + "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @DisplayName("Throws BearerTokenNotFoundException when no Authorization Header exists")
    @Test
    void should_fail_when_no_authorization_header_exists() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when & then
        assertThatCode(() -> BearerExtractor.resolve(request))
                .isInstanceOf(BearerTokenNotFoundException.class);
    }

    @DisplayName("Throws BearerTokenNotFoundException when bearer token is empty")
    @ValueSource(strings = {"", " ",})
    @ParameterizedTest
    void should_fail_when_bearer_token_is_empty(final String emptyBearerToken) {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, emptyBearerToken);

        // when & then
        assertThatCode(() -> BearerExtractor.resolve(request))
                .isInstanceOf(BearerTokenNotFoundException.class);
    }

    @DisplayName("Throws BearerTokenMalformedException when bearer token is malformed")
    @ValueSource(strings = {"bearer token.with.invalid.character.!", "bearer token.with.invalid.character.#"})
    @ParameterizedTest
    void should_fail_when_bearer_token_is_malformed(final String malformedBearerToken) {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, malformedBearerToken);

        // when & then
        assertThatCode(() -> BearerExtractor.resolve(request))
                .isInstanceOf(BearerTokenMalformedException.class);
    }

    @DisplayName("Does not throw any exception when bearer token is valid")
    @Test
    void should_success_when_bearer_token_is_valid() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, VALID_JWT_TOKEN);

        // when & then
        assertThatCode(() -> BearerExtractor.resolve(request))
                .doesNotThrowAnyException();
    }
}
