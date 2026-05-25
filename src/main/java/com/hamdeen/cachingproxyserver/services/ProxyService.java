package com.hamdeen.cachingproxyserver.services;

import com.hamdeen.cachingproxyserver.dtos.CacheResponse;
import com.hamdeen.cachingproxyserver.enums.CacheHeader;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class ProxyService {
    @Value("${proxy.origin}")
    private String origin;

    private final Map<String, String> cache = new HashMap<String, String>();

    public CacheResponse fetch(String path) {
        var fullPath = origin + path;

        if (getCache().containsKey(fullPath)) {
            return new CacheResponse(getCache().get(fullPath), CacheHeader.HIT.toString());
        }

        var restClient = RestClient.create();
        var response = restClient.get()
                .uri(fullPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        cache(fullPath, response);

        return new CacheResponse(response, CacheHeader.MISS.toString());
    }

    public void cache(String url, String response) {
        cache.put(url, response);
    }
}
