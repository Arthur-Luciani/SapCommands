package SapGeneric;

import Conn.SapConn;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicKey {
    @Autowired
    Standart standart;

    /**
     * @param value Value to send
     */
    public void sendKey(int value){
        standart.isExisting("wnd[0]");
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", "wnd[0]").toDispatch());
        standart.obj.invoke("sendVKey", value);
    }

    /**
     * @param key Element of Keys Enum
     */
    public void keyBoardPress(Keys key){
        switch (key){
            case ENTER -> sendKey(0);
            case ESC, F12 -> sendKey(12);
            case F1 -> sendKey(1);
            case F2 -> sendKey(2);
            case F3 -> sendKey(3);
            case F4 -> sendKey(4);
            case F5 -> sendKey(5);
            case F6 -> sendKey(6);
            case F7 -> sendKey(7);
            case F8 -> sendKey(8);
            case F9 -> sendKey(9);
            case F10 -> sendKey(10);
            case F11 -> sendKey(11);
        }
    }

    /**
     * @param elementId GUI element ID
     */
    public void buttonSelect(String elementId){
        standart.isExisting(elementId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", elementId).toDispatch());
        Dispatch.call(standart.obj, "Select");
    }

    /**
     * @param elementId GUI element ID
     */
    public void buttonPress(String elementId){
        standart.isExisting(elementId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", elementId).toDispatch());
        Dispatch.call(standart.obj, "Press");
    }

    public void setFocus(String elementId){
        standart.isExisting(elementId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", elementId).toDispatch());
        Dispatch.call(standart.obj, "setFocus");
    }


}
