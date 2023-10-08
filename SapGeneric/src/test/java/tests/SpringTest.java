package tests;

import static org.assertj.core.api.Assertions.assertThat;

import Config.BeanConfig;
import SapGeneric.Sap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(BeanConfig.class)
public class SpringTest {


    Sap sap = new Sap();

    @Test
    public void contextLoads(){
        assertThat(sap.basic).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {

    }
}
