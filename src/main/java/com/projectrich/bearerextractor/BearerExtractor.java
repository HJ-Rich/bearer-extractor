package com.projectrich.bearerextractor;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class BearerExtractor {

    private static final String AUTHORIZATION = "Authorization";
    private static final Pattern VALID_TOKEN_PATTERN =
            Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

    /**
     * Extract Bearer Token from Authorization Header
     *
     * @param request HttpServletRequest
     * @return Bearer Token String without prefix 'Bearer '
     * @throws BearerTokenNotFoundException  when there is no valid bearer token in HttpServletRequest
     * @throws BearerTokenMalformedException when bearer token contains invalid characters
     * @see com.projectrich.bearerextractor.BearerExtractor#VALID_TOKEN_PATTERN
     * @see <a href="https://shorturl.at/NPU04">DefaultBearerTokenResolver</a>
     */
    public static String resolve(final HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);

        if (Objects.isNull(authorization) || doesNotStartsWithBearer(authorization)) {
            throw new BearerTokenNotFoundException();
        }

        Matcher matcher = VALID_TOKEN_PATTERN.matcher(authorization);
        if (!matcher.matches()) {
            throw new BearerTokenMalformedException();
        }

        return matcher.group("token");
    }

    private static boolean doesNotStartsWithBearer(final String authorization) {
        return !authorization.toLowerCase().startsWith("bearer ");
    }
}
