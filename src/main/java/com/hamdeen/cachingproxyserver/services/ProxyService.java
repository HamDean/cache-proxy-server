package com.hamdeen.cachingproxyserver.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class ProxyService {
    private Map<String, String> cache = new HashMap<String, String>();

    public void cache(String url, String response) {
        cache.put(url, response);
    }
}
