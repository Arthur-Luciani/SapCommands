package tests;

import static org.assertj.core.api.Assertions.assertThat;

import SapGeneric.Sap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringTest {

    @Autowired
    Sap sap;

    @Test
    public void contextLoads(){
        assertThat(sap).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {

    }
}
