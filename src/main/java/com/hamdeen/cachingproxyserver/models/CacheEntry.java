package com.hamdeen.cachingproxyserver.models;

import com.hamdeen.cachingproxyserver.enums.CacheHeader;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CacheEntry {
    private String body;
    private Instant expiresAt;
}
