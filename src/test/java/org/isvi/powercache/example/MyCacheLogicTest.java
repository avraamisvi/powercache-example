package org.isvi.powercache.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyCacheLogicTest {

    @Autowired
    private ServiceExample serviceExample;

    @Test
    public void testWhenGetShouldCache() {
        String firstResponse = serviceExample.getWithCache("abacate");
        String secondResponse = serviceExample.getWithCache("abacate");

        Assertions.assertThat(secondResponse).isNotNull().isEqualTo(firstResponse);
    }

    @Test
    public void testWhenSavingShouldEvictCache() {
        String firstResponse = serviceExample.getWithCache("abacate");
        serviceExample.saveWithEvict("abacate");
        String secondResponse = serviceExample.getWithCache("abacate");

        Assertions.assertThat(secondResponse).isNotNull().isNotEqualTo(firstResponse);
    }

    @Test
    public void testWhenSavingAnyKeyShouldEvictAllCaches() {
        String abacate = serviceExample.getWithCache("abacate");
        String melao = serviceExample.getWithCache("melao");
        String arroz = serviceExample.getWithCache("arroz");

        serviceExample.saveWithEvict("abacate");

        String abacate2 = serviceExample.getWithCache("abacate");
        String melao2 = serviceExample.getWithCache("melao");
        String arroz2 = serviceExample.getWithCache("arroz");

        Assertions.assertThat(abacate2).isNotNull().isNotEqualTo(abacate);
        Assertions.assertThat(melao2).isNotNull().isNotEqualTo(melao);
        Assertions.assertThat(arroz2).isNotNull().isNotEqualTo(arroz);
    }
}