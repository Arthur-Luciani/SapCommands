package Components;

import Connection.SapMessenger;
import com.jacob.activeX.ActiveXComponent;

public class ComboBox{
    private final SapMessenger sapMessenger;

    public ComboBox(SapMessenger sapMessenger) {
        this.sapMessenger = sapMessenger;
    }

    /**
     * Select some option in combo box
     * @param cbId GUI element ID
     * @param item2Select Select option
     */
    public void select(String cbId, String item2Select){
        sapMessenger.isExisting(cbId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", cbId).toDispatch());
        if (item2Select.isBlank()){
            sapMessenger.obj.invoke("SetKeySpace");
        } else{
            sapMessenger.obj.setProperty("value", item2Select);
        }
    }

    public void selectKey(String cbId, String key){
        sapMessenger.isExisting(cbId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", cbId).toDispatch());
        if (key.isBlank()){
            sapMessenger.obj.invoke("SetKeySpace");
        } else {
            sapMessenger.obj.setProperty("value", key);
        }
    }


}
