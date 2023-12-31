package ConfigProject;

import Conn.SapConn;
import SapGeneric.Standart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan({"Conn", "Utils", "ErrorHandler", "SapGeneric"})
@Configuration
public class WebConfig {
    @Bean("standart")
    public Standart standard(){
        System.out.println("Bean standart");
        return new SapConn().getStandart(0);
    }

}
