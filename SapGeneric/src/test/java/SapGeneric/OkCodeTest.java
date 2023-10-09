package SapGeneric;

import Config.BeanConfig;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = BeanConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(BeanConfig.class)
class OkCodeTest {

    @Autowired
    OkCode okCode;

    @SpyBean
    Standart standart;


    void test(){
        okCode.setOkCode("/nme23n");
        assertEquals("okcd", standart.arg[0]);
        assertEquals("GuiOkCodeField", standart.arg[1]);
    }

}