package SapGeneric;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class GridControl extends Standart{

    /**
     * @param gridId GUI ID element
     * @return Total rows of grid element
     */
    public int RowCount(String gridId){
        checkElement.isExisting(gridId);
        obj = new ActiveXComponent(session.invoke("FindById", gridId).toDispatch());
        return obj.getPropertyAsInt("RowCount"); //Need test
    }

    public String getText(String gridId, String columnName, int row){
        checkElement.isExisting(gridId);
        obj = new ActiveXComponent(session.invoke("FindById", gridId).toDispatch());
        return Dispatch.call(obj, "GetCellValue", new Object[]{ row, columnName}).toString(); //Need test
    }

    public int getInt(String gridId, String columnName, int row){
        return numberConverter.getInt(getText(gridId, columnName, row));
    }

    public float getFloat(String gridId, String columnName, int row){
        return numberConverter.getFloat(getText(gridId, columnName, row));
    }

}
