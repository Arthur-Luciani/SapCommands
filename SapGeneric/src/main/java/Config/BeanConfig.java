package Config;

import Conn.SapConn;
import SapGeneric.Standart;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {


    public Standart getStandard(){
        return new SapConn().getStandart(0);
    }

}
