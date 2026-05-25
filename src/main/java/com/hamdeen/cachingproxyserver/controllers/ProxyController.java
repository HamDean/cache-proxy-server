package com.hamdeen.cachingproxyserver.controllers;

import com.hamdeen.cachingproxyserver.services.ProxyService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/proxy")
@AllArgsConstructor
public class ProxyController {
    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<?> getResource() {
        var restClient = RestClient.create();
        var response = restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        proxyService.cache("https://jsonplaceholder.typicode.com/posts/1", response);

        return ResponseEntity.ok(response);
    }
}
