package Config;

import Conn.SapConn;
import SapGeneric.Standart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan({"Conn", "Utils", "ErrorHandler", "SapGeneric"})
@Configuration
public class BeanConfig {


    @Bean
    public Standart getStandard(){
        return new SapConn().getStandart(0);
    }

}
