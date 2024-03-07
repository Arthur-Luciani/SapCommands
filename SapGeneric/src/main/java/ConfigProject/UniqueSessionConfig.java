package ConfigProject;

import Connection.SapConnector;
import Controller.Sap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan({"Utils"})
@Configuration
public class UniqueSessionConfig {

    @Bean
    public Sap sap() {
        SapConnector.initJacob();
        return new Sap(0);
    }

}
