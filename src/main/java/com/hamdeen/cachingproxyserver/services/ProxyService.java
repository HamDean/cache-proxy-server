package com.hamdeen.cachingproxyserver.services;

import com.hamdeen.cachingproxyserver.dtos.CacheResponse;
import com.hamdeen.cachingproxyserver.enums.CacheHeader;
import com.hamdeen.cachingproxyserver.models.CacheEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProxyService {
    @Value("${proxy.origin}")
    private String origin;

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    public CacheResponse fetch(String path) {
        var fullPath = origin + path;

        if ( cache.containsKey(fullPath) &&
                cache.get(fullPath).getExpiresAt().isAfter(Instant.now())
        ) {
            return new CacheResponse(cache.get(fullPath).getBody(), CacheHeader.HIT.toString());
        }

        cache.remove(fullPath);

        var restClient = RestClient.create();
        var response = restClient.get()
                .uri(fullPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        var expiresAt = Instant.now().plusSeconds(120);
        cache(fullPath, new CacheEntry(response, expiresAt));

        return new CacheResponse(response, CacheHeader.MISS.toString());
    }

    public void cache(String url, CacheEntry cacheEntry) {
        cache.put(url, cacheEntry);
    }

    public void clearCache() {
        cache.clear();
    }
}
