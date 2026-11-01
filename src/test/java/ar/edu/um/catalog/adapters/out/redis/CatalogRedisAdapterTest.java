package ar.edu.um.catalog.adapters.out.redis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogRedisAdapterTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private CatalogRedisAdapter catalogRedisAdapter;

    @Test
    void testGetCurrentVersion_Success() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("catedra:sync:current-version")).thenReturn("42");

        Long version = catalogRedisAdapter.getCurrentVersion();

        assertEquals(42L, version);
    }

    @Test
    void testGetCurrentVersion_Null() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("catedra:sync:current-version")).thenReturn(null);

        Long version = catalogRedisAdapter.getCurrentVersion();

        assertNull(version);
    }

    @Test
    void testGetOldestAvailableVersion_Success() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("catedra:sync:oldest-available-version")).thenReturn("10");

        Long version = catalogRedisAdapter.getOldestAvailableVersion();

        assertEquals(10L, version);
    }

    @Test
    void testGetOldestAvailableVersion_Null() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("catedra:sync:oldest-available-version")).thenReturn(null);

        Long version = catalogRedisAdapter.getOldestAvailableVersion();

        assertNull(version);
    }

    @Test
    void testGetChangesJson() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("catedra:sync:changes:5")).thenReturn("{\"changes\":[]}");

        String json = catalogRedisAdapter.getChangesJson(5L);

        assertEquals("{\"changes\":[]}", json);
    }

    @Test
    void testGetEntityJson() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.get("catedra:sync:professional", "123")).thenReturn("{\"id\":123}");

        String json = catalogRedisAdapter.getEntityJson("professional", 123L);

        assertEquals("{\"id\":123}", json);
    }
}