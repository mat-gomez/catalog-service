package ar.edu.um.catalog.adapters.out.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogRedisAdapter {

    private final StringRedisTemplate redisTemplate;

    public Long getCurrentVersion() {
        String versionStr = redisTemplate.opsForValue().get("catedra:sync:current-version");
        return versionStr != null ? Long.parseLong(versionStr) : null;
    }

    public Long getOldestAvailableVersion() {
        String versionStr = redisTemplate.opsForValue().get("catedra:sync:oldest-available-version");
        return versionStr != null ? Long.parseLong(versionStr) : null;
    }

    public String getChangesJson(Long version) {
        return redisTemplate.opsForValue().get("catedra:sync:changes:" + version);
    }

    public String getEntityJson(String hashKey, Long id) {
        return (String) redisTemplate.opsForHash().get("catedra:sync:" + hashKey, id.toString());
    }
}
