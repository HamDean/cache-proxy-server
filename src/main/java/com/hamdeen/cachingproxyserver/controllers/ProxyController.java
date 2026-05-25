package com.hamdeen.cachingproxyserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
    @GetMapping
    public ResponseEntity<?> getResource() {
        return ResponseEntity.ok().build();
    }
}
