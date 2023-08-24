package SapGeneric;

import com.jacob.activeX.ActiveXComponent;

public class ComboBox extends Standart{

    /**
     * Select some option in combobox
     * @param cbId ComboBoxID
     * @param item2Select Selec option
     */
    public void select(String cbId, String item2Select){
        checkElement.isExisting(cbId);
        obj = new ActiveXComponent(session.invoke("FindById", cbId).toDispatch());
        obj.setProperty("value", item2Select);
    }
}
