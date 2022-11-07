package com.solmaz.clientserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@CrossOrigin
public class ClientRestController {

    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/hello")
    public String getArticles(
            @RegisteredOAuth2AuthorizedClient("gateway-client-id-authorization-code") OAuth2AuthorizedClient authorizedClient
    ) {
        System.err.println("asd");
        var x = this.webClient
                .get()
                .uri("http://127.0.0.1:4040/api/v1/user/hello")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.err.println(x);
        return x;
    }
}
