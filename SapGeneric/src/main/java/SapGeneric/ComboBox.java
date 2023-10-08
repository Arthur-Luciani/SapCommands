package SapGeneric;

import Conn.SapConn;
import com.jacob.activeX.ActiveXComponent;
import org.springframework.stereotype.Service;

@Service
public class ComboBox{
    private Standart standart = new SapConn().getStandart(0);

    /**
     * Select some option in combobox
     * @param cbId GUI element ID
     * @param item2Select Select option
     */
    public void select(String cbId, String item2Select){
        standart.isExisting(cbId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", cbId).toDispatch());
        if (item2Select.isBlank()){
            standart.obj.invoke("SetKeySpace");
        } else{
            standart.obj.setProperty("value", item2Select);
        }
    }


}
