package com.hamdeen.cachingproxyserver.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProxyService {
    Map<String, String> cache = new HashMap<String, String>();

    public void cache(String url, String response) {
        if (!cache.containsKey(url)) {
            cache.put(url, response);
        }

        cache.forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
