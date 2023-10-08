package SapGeneric;

import Conn.SapConn;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import org.springframework.stereotype.Service;

@Service
public class OkCode{

    Standart standart = new SapConn().getStandart(0);

    /**
     * @param value Transaction name
     */
    public void setOkCode(String value){
        standart.arg[0] = new Variant("okcd");
        standart.arg[1] = new Variant("GuiOkCodeField");
        standart.obj = new ActiveXComponent(standart.session.invoke("FindByName", standart.arg).toDispatch());
        standart.obj.setProperty("text", value);
        standart.session.invoke("sendVKey", 0);
    }


}
