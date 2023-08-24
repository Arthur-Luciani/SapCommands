package SapGeneric;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public class OkCode extends Standart{

    /**
     * Set transaction
     * @param value Transaction name
     */
    public void setOkCode(String value){
        arg[0] = new Variant("okcd");
        arg[1] = new Variant("GuiOkCodeField");
        obj = new ActiveXComponent(session.invoke("FindByName", arg).toDispatch());
        obj.setProperty("text", value);
    }


}
