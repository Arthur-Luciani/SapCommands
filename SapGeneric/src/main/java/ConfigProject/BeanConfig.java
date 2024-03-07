package ConfigProject;

import Connection.SapConnector;
import Connection.SapMessenger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan({"Connection", "Utils", "ErrorHandler", "Components"})
@Configuration
public class BeanConfig {
    @Bean
    public SapMessenger getMessenger(){
        SapMessenger sapMessenger;
        try {
            sapMessenger = SapConnector.getMessenger(0);
        } catch (Exception e) {
            System.out.println("Unable to get Sap connection");
            throw new RuntimeException(e);
        }
        return sapMessenger;
    }

}
