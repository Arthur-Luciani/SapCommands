package SapGeneric;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkCode{

    @Autowired
    public SapMessenger sapMessenger;

    /**
     * @param value Transaction name
     */
    public void setOkCode(String value){
        sapMessenger.isExisting("wnd[0]");
        sapMessenger.arg[0] = new Variant("okcd");
        sapMessenger.arg[1] = new Variant("GuiOkCodeField");
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindByName", sapMessenger.arg).toDispatch());
        sapMessenger.obj.setProperty("text", value);
        sapMessenger.session.invoke("sendVKey", 0);
        sapMessenger.parentSession.invoke("LockSessionUI");
    }


}
