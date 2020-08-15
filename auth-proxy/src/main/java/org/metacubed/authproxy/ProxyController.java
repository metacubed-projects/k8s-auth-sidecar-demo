package org.metacubed.authproxy;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class ProxyController {

    private final RestTemplate restTemplate;

    public ProxyController() {

        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });
    }

    @RequestMapping("/**")
    public ResponseEntity<String> proxy(RequestEntity<String> requestEntity) throws URISyntaxException {

        URI uri = new URI(
                "http", requestEntity.getUrl().getUserInfo(),
                "localhost", 80, requestEntity.getUrl().getPath(),
                requestEntity.getUrl().getQuery(), requestEntity.getUrl().getFragment());

        if (!isAuthorized(requestEntity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }

        RequestEntity<String> forwardEntity = new RequestEntity<>(
                requestEntity.getBody(), requestEntity.getHeaders(), requestEntity.getMethod(), uri);

        return restTemplate.exchange(forwardEntity, String.class);
    }

    private boolean isAuthorized(RequestEntity<String> requestEntity) {

        String path = requestEntity.getUrl().getPath();
        HttpMethod method = requestEntity.getMethod();

        List<String> authorizationHeaders = requestEntity.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaders == null || authorizationHeaders.size() != 1) {
            return false;
        }

        String username = authorizationHeaders.get(0);
        switch (username) {
            case "alice":
                if ("/data".equals(path) && method == HttpMethod.GET) {
                    return true;
                }
                break;
            case "bob":
                if ("/data".equals(path) && (method == HttpMethod.GET || method == HttpMethod.POST)) {
                    return true;
                }
                break;
        }
        return false;
    }
}
