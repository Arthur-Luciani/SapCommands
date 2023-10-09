package tests;

import static org.assertj.core.api.Assertions.assertThat;

import Config.BeanConfig;
import SapGeneric.Basic;
import SapGeneric.OkCode;
import SapGeneric.Sap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration(classes = BeanConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(BeanConfig.class)
public class SpringTest {
    @Autowired
    Sap sap;

    @SpyBean
    OkCode okCode;

    @Test
    public void contextLoads(){
        assertThat(sap.basic).isNotNull();
    }

    @Test
    public void usagesTest(){
        sap.okCode.setOkCode("/nVA03");
    }




}
