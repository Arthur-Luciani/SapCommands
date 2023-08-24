package SapGeneric;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class TableControl extends Standart{

    public int getColumnIndex(String tableId, String columnName){
        ActiveXComponent currentObj;
        obj = new ActiveXComponent(session.invoke("FindById", tableId).toDispatch());
        Dispatch dispatch = Dispatch.call(obj, "Columns").toDispatch();
        int count = Dispatch.call(dispatch, "count").toInt();
        Dispatch[] arr = new Dispatch[count];

        for (int i = 0; i < count; i++) {
            currentObj = new ActiveXComponent(obj.invoke("Columns", i).toDispatch());
            if( currentObj.getProperty("title").toString().trim().equals(columnName))
            {
                return i;
            }
        }
        return -1;
    }

}
