package br.edu.zup.tax_calc_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaxCalcApiApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void mainMethodRunsApplication() {
        String[] args = {};
        TaxCalcApiApplication.main(args);
    }

    @Test
    void verifyBeansAreLoaded() {
        assertThat(applicationContext.getBean(TaxCalcApiApplication.class)).isNotNull();
    }
}
