package com.hamdeen.cachingproxyserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CacheResponse {
    private String data;
    private String cacheHeader;
}
