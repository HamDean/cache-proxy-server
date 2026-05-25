package com.hamdeen.cachingproxyserver.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
    @GetMapping
    public ResponseEntity<?> getResource() {
        var restClient = RestClient.create();
        var response = restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok(response);
    }
}
