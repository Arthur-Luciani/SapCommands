package SapGeneric;

import com.jacob.activeX.ActiveXComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboBox{
    @Autowired
    private SapMessenger sapMessenger;

    /**
     * Select some option in combobox
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
