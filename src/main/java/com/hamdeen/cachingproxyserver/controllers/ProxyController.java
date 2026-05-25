package com.hamdeen.cachingproxyserver.controllers;

import com.hamdeen.cachingproxyserver.services.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
@AllArgsConstructor
public class ProxyController {
    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<String> getResource(HttpServletRequest request) {
//        var path = request.getRequestURI();
        var path = "/posts/5";
        var response = proxyService.fetch(path);

        return ResponseEntity
                .ok()
                .header("X-Cache", response.getCacheHeader())
                .body(response.getData());
    }
}
