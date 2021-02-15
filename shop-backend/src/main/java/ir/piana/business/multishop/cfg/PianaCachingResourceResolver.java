package ir.piana.business.multishop.cfg;

import org.springframework.cache.Cache;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.resource.CachingResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PianaCachingResourceResolver extends CachingResourceResolver {
    public PianaCachingResourceResolver(Cache cache) {
        super(cache);
    }

//    @Autowired
//    public PianaCachingResourceResolver(CacheManager cacheManager, @Qualifier("spring-resource-chain-cache") String cacheName) {
//        super(cacheManager, cacheName);
//    }

    @Override
    protected Resource resolveResourceInternal(@Nullable HttpServletRequest request, String requestPath,
                                               List<? extends Resource> locations, ResourceResolverChain chain) {

        String key = computeKey(request, requestPath);
        Resource resource = this.getCache().get(key, Resource.class);

        if (resource != null) {
            if (logger.isTraceEnabled()) {
                logger.trace("Resource resolved from cache");
            }
            return resource;
        }

        resource = chain.resolveResource(request, requestPath, locations);
        if (resource != null) {
            this.getCache().put(key, resource);
        }

        return resource;
    }
}
