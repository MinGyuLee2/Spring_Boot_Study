package com.example.demo.global.auth.oauth;

import com.example.demo.domain.member.exception.MemberErrorCode;
import com.example.demo.domain.member.exception.MemberException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthClient {

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String clientId;
    private final String clientSecret;
    private final String defaultRedirectUri;

    public KakaoOAuthClient(
            @Value("${kakao.client-id}") String clientId,
            @Value("${kakao.client-secret}") String clientSecret,
            @Value("${kakao.redirect-uri}") String defaultRedirectUri
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.defaultRedirectUri = defaultRedirectUri;
    }

    public KakaoUserInfo getUserInfo(String code, String redirectUri) {
        if (clientId == null || clientId.isBlank()) {
            throw new MemberException(MemberErrorCode.KAKAO_OAUTH_FAILED);
        }

        try {
            String kakaoAccessToken = requestAccessToken(code, resolveRedirectUri(redirectUri));
            return requestUserInfo(kakaoAccessToken);
        } catch (MemberException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new MemberException(MemberErrorCode.KAKAO_OAUTH_FAILED);
        }
    }

    private String requestAccessToken(String code, String redirectUri) throws Exception {
        List<String> parameters = new ArrayList<>();
        parameters.add(formParameter("grant_type", "authorization_code"));
        parameters.add(formParameter("client_id", clientId));
        parameters.add(formParameter("redirect_uri", redirectUri));
        parameters.add(formParameter("code", code));
        if (clientSecret != null && !clientSecret.isBlank()) {
            parameters.add(formParameter("client_secret", clientSecret));
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(KAKAO_TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(String.join("&", parameters)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new MemberException(MemberErrorCode.KAKAO_OAUTH_FAILED);
        }

        JsonNode jsonNode = objectMapper.readTree(response.body());
        JsonNode accessToken = jsonNode.get("access_token");
        if (accessToken == null || accessToken.asText().isBlank()) {
            throw new MemberException(MemberErrorCode.KAKAO_OAUTH_FAILED);
        }
        return accessToken.asText();
    }

    private KakaoUserInfo requestUserInfo(String kakaoAccessToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(KAKAO_USER_INFO_URL))
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new MemberException(MemberErrorCode.KAKAO_OAUTH_FAILED);
        }

        JsonNode root = objectMapper.readTree(response.body());
        String providerUserId = root.path("id").asText();
        JsonNode kakaoAccount = root.path("kakao_account");
        JsonNode profile = kakaoAccount.path("profile");

        return new KakaoUserInfo(
                providerUserId,
                textOrNull(kakaoAccount.path("email")),
                textOrNull(profile.path("nickname")),
                textOrNull(profile.path("profile_image_url"))
        );
    }

    private String resolveRedirectUri(String redirectUri) {
        if (redirectUri == null || redirectUri.isBlank()) {
            return defaultRedirectUri;
        }
        return redirectUri;
    }

    private String formParameter(String key, String value) {
        return URLEncoder.encode(key, StandardCharsets.UTF_8)
                + "="
                + URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String textOrNull(JsonNode jsonNode) {
        if (jsonNode == null || jsonNode.isMissingNode() || jsonNode.isNull()) {
            return null;
        }
        return jsonNode.asText();
    }
}
