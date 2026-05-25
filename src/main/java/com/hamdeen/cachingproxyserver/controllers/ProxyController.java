package com.hamdeen.cachingproxyserver.controllers;

import com.hamdeen.cachingproxyserver.enums.CacheHeader;
import com.hamdeen.cachingproxyserver.services.ProxyService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/proxy")
@AllArgsConstructor
public class ProxyController {
    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<String> getResource(
            @RequestParam(name = "url", required = true) String url
    ) {
        if (proxyService.getCache().containsKey(url)) {
            return ResponseEntity
                    .ok()
                    .header("X-Cache", String.valueOf(CacheHeader.HIT))
                    .body(proxyService.getCache().get(url));
        }

        var restClient = RestClient.create();
        var response = restClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        proxyService.cache(url, response);

        return ResponseEntity
                .ok()
                .header("X-Cache", String.valueOf(CacheHeader.MISS))
                .body(response);
    }
}
