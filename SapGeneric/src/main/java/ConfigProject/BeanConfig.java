package ConfigProject;

import Connection.SapConnector;
import Connection.SapMessenger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan({"Connection", "Utils", "ErrorHandler", "SapGeneric"})
@Configuration
public class BeanConfig {
    @Bean
    public SapMessenger getStandard(){
        return new SapConnector().getMessenger(0);
    }

}
