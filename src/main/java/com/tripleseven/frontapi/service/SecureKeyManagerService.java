package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.dto.key.KeyResponseDTO;
import com.tripleseven.frontapi.error.KeyManagerException;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.List;

import java.io.InputStream;


@Component
public class SecureKeyManagerService {

    @Value("${secret.url}")
    private String url;

    @Value("${secret.appKey}")
    private String appKey;

    @Value("${secret.password}")
    private String password;

    @Value("${spring.profiles.active}")
    private String profile;

    public String fetchSecretFromKeyManager(String keyId) {
        try {

            InputStream keyStoreInputStream;

            if (profile.equals("dev")) {
                keyStoreInputStream = getClass().getClassLoader().getResourceAsStream("triple-seven.p12");
                if (keyStoreInputStream == null) {
                    throw new IllegalArgumentException(); // 나중에 추가
                }
            } else {

                String p12Data = System.getenv("P12_FILE");
                if (p12Data == null || p12Data.isEmpty()) {
                    throw new KeyManagerException("P12 file data not found in environment variables");
                }
                // 디코딩된 P12 데이터를 InputStream으로 변환
                keyStoreInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(p12Data));

            }

            KeyStore clientStore = KeyStore.getInstance("PKCS12");


            clientStore.load(keyStoreInputStream, password.toCharArray());
            SSLContext sslContext = SSLContexts.custom()
                    .setProtocol("TLS")
                    .loadKeyMaterial(clientStore, password.toCharArray()) // 키를 로드하기 위해 p12 파일과 비밀번호로 비밀키 꺼내서 sslcontext에 넣음
                    .loadTrustMaterial(new TrustSelfSignedStrategy()) // 자체 서명된 인증서를 신뢰하도록 TrustManager 설정
                    .build();

            SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                    .setSslContext(sslContext)
                    .build();

            HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(sslSocketFactory)
                    .build();

            // CloseableHttpClient (httpclient + 자원 관리) 객체를 만들고 HttpClients.custom()로 httpclient 객체 커스텀
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .evictExpiredConnections()
                    .build();

            // HttpComponentsClientHttpRequestFactory는 Spring에서 HTTP 요청을 처리할 때 사용하는 클래스
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            RestTemplate restTemplate = new RestTemplate(requestFactory);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            // URL과 경로 지정해서 URI 생성
            URI uri = UriComponentsBuilder
                    .fromUriString(url)
                    .path("/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}")
                    .encode()
                    .build()
                    .expand(appKey, keyId)
                    .toUri();

            ResponseEntity<KeyResponseDTO> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KeyResponseDTO.class
            );


            if (response.getBody() != null && response.getBody() != null) {
                return response.getBody().getBody().getSecret();
            } else {
                throw new KeyManagerException("Invalid response from Key Manager");
            }

        } catch (KeyStoreException | IOException | CertificateException
                 | NoSuchAlgorithmException | UnrecoverableKeyException
                 | KeyManagementException e) {
            throw new KeyManagerException("Error while fetching secret: " + e.getMessage());
        }
    }
}