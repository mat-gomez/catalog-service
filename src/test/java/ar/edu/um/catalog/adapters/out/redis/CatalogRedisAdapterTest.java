package ar.edu.um.catalog.adapters.out.redis;

import ar.edu.um.catalog.adapters.out.redis.CatalogRedisAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CatalogRedisAdapterTest {

    @InjectMocks
    private CatalogRedisAdapter catalogRedisAdapter;

    @Test
    void testAdapterInstantiation() {
        assertNotNull(catalogRedisAdapter);
    }
}
