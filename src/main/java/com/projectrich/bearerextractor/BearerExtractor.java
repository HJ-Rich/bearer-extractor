package com.projectrich.bearerextractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

public class BearerExtractor {

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
            Pattern.CASE_INSENSITIVE);

    /**
     * Extract Bearer Token from Authorization Header
     *
     * @param request HttpServletRequest
     * @return Bearer Token String without prefix 'Bearer '
     * @see <a href="https://shorturl.at/NPU04">DefaultBearerTokenResolver</a>
     */
    public static String resolve(final HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            throw new BearerTokenNotFoundException();
        }

        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            throw new BearerTokenMalformedException();
        }

        return matcher.group("token");
    }
}
