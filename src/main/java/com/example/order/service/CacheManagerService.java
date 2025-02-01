package com.example.order.service;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheManagerService {

	private final CacheManager cacheManager;
	
	@Scheduled(cron = "${cache.eviction.cron}")
	public void evictCaches() {
		log.info("Evicting all caches");
		Stream.of("ProductInfo")
			.map(cacheManager::getCache)
			.filter(Objects::nonNull)
			.forEach(Cache::clear);
	}
}
