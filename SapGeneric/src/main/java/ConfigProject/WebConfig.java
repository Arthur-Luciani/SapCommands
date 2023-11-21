package ConfigProject;

import Conn.SapConn;
import SapGeneric.Sap;
import SapGeneric.Standart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@ComponentScan({"Conn", "Utils", "ErrorHandler", "SapGeneric"})
@Configuration
public class WebConfig {
    @Bean("standart")
    public Standart standard(){
        System.out.println("Bean standart");
        Standart s = new SapConn().getStandart(0);
        System.out.println("--------------Bean Standart--------------");
        System.out.println(s.parentSession.m_pDispatch);
        System.out.println("--------------Bean Standart--------------");
        return s;
    }

}
