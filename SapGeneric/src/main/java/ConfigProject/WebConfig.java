package ConfigProject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan({"Connection", "Utils", "ErrorHandler", "SapGeneric"})
@Configuration
public class WebConfig {
    /*@Bean("standart")
    public Standart standard(){
        System.out.println("Bean standart");
        return new SapConn().getStandart(0);
    }*/
}