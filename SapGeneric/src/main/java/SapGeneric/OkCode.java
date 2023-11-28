package SapGeneric;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkCode{

    @Autowired
    public Standart standart;

    /**
     * @param value Transaction name
     */
    public void setOkCode(String value){
        standart.isExisting("wnd[0]");
        standart.arg[0] = new Variant("okcd");
        standart.arg[1] = new Variant("GuiOkCodeField");
        standart.obj = new ActiveXComponent(standart.session.invoke("FindByName", standart.arg).toDispatch());
        standart.obj.setProperty("text", value);
        standart.session.invoke("sendVKey", 0);
        standart.parentSession.invoke("LockSessionUI");
    }


}
