package ir.piana.business.multishop.common.cfg;

import ir.piana.business.multishop.common.data.entity.SiteEntity;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;

@Configuration
@EnableCaching
public class PianaCacheConfig extends CachingConfigurerSupport {
    @Bean
    public CacheManager ehCacheManager() {
        CacheConfiguration<String, SiteEntity> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, SiteEntity.class, ResourcePoolsBuilder.heap(10))
                .build();

        javax.cache.CacheManager cacheManager = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider")
                .getCacheManager();

        String cacheName = "sitesByDomain";
        cacheManager.destroyCache(cacheName);
        cacheManager.createCache(cacheName, Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig));

        return new JCacheCacheManager(cacheManager);
    }

    /*@Bean
    @Override
    public KeyGenerator keyGenerator() {

    }*/
}
