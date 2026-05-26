package com.example.demo.global.auth;

import com.example.demo.domain.member.dto.AuthTokenResponse;
import com.example.demo.domain.member.entity.Member;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String TOKEN_TYPE = "Bearer";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String secret;
    private final long accessTokenValidityInSeconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds
    ) {
        this.secret = secret;
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }

    public AuthTokenResponse generateAccessToken(Member member) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(accessTokenValidityInSeconds);

        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", member.getId().toString());
        payload.put("email", member.getEmail());
        payload.put("nickname", member.getNickname());
        payload.put("iat", now.getEpochSecond());
        payload.put("exp", expiresAt.getEpochSecond());

        String unsignedToken = encodeJson(header) + "." + encodeJson(payload);
        String accessToken = unsignedToken + "." + sign(unsignedToken);

        return new AuthTokenResponse(
                TOKEN_TYPE,
                accessToken,
                accessTokenValidityInSeconds,
                member.getId(),
                member.getEmail(),
                member.getNickname()
        );
    }

    public boolean validateToken(String token) {
        try {
            Map<String, Object> claims = parseClaims(token);
            long expiration = asLong(claims.get("exp"));
            return expiration > Instant.now().getEpochSecond();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    public AuthMemberPrincipal getPrincipal(String token) {
        Map<String, Object> claims = parseClaims(token);
        return new AuthMemberPrincipal(
                Long.valueOf(String.valueOf(claims.get("sub"))),
                String.valueOf(claims.get("email")),
                String.valueOf(claims.get("nickname"))
        );
    }

    private Map<String, Object> parseClaims(String token) {
        String[] tokenParts = token.split("\\.");
        if (tokenParts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT format");
        }

        String unsignedToken = tokenParts[0] + "." + tokenParts[1];
        String expectedSignature = sign(unsignedToken);
        if (!MessageDigest.isEqual(
                expectedSignature.getBytes(StandardCharsets.UTF_8),
                tokenParts[2].getBytes(StandardCharsets.UTF_8)
        )) {
            throw new IllegalArgumentException("Invalid JWT signature");
        }

        try {
            byte[] payloadBytes = Base64.getUrlDecoder().decode(tokenParts[1]);
            return objectMapper.readValue(payloadBytes, new TypeReference<>() {
            });
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid JWT payload", exception);
        }
    }

    private String encodeJson(Map<String, Object> values) {
        try {
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(objectMapper.writeValueAsBytes(values));
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to encode JWT", exception);
        }
    }

    private String sign(String unsignedToken) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
            byte[] signature = mac.doFinal(unsignedToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signature);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to sign JWT", exception);
        }
    }

    private long asLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(String.valueOf(value));
    }
}
