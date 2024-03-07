package Components;

import Connection.SapMessenger;
import Enums.Keys;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class BasicKey {
    SapMessenger sapMessenger;

    public BasicKey(SapMessenger sapMessenger) {
        this.sapMessenger = sapMessenger;
    }

    /**
     * @param value Value to send
     */
    public void sendKey(int value){
        sapMessenger.isExisting("wnd[0]");
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", "wnd[0]").toDispatch());
        sapMessenger.obj.invoke("sendVKey", value);
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
        sapMessenger.isExisting(elementId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", elementId).toDispatch());
        Dispatch.call(sapMessenger.obj, "Select");
    }

    /**
     * @param elementId GUI element ID
     */
    public void buttonPress(String elementId){
        sapMessenger.isExisting(elementId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", elementId).toDispatch());
        Dispatch.call(sapMessenger.obj, "Press");
    }

    public void setFocus(String elementId){
        sapMessenger.isExisting(elementId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", elementId).toDispatch());
        Dispatch.call(sapMessenger.obj, "setFocus");
    }
}
