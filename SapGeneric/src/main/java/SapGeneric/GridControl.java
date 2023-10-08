package SapGeneric;

import Conn.SapConn;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GridControl{

    private Standart standart = new SapConn().getStandart(0);
    private NumberConverter numberConverter = new NumberConverter();

    /**
     * @param gridId GUI ID element
     * @return Total rows of grid element
     */
    public int getTotalRows(String gridId){
        standart.isExisting(gridId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", gridId).toDispatch());
        return standart.obj.getPropertyAsInt("RowCount"); //Need test
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @return String value of cell
     */
    public String getText(String gridId, String columnName, int row){
        standart.isExisting(gridId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", gridId).toDispatch());
        return Dispatch.call(standart.obj, "GetCellValue", new Object[]{ row, columnName}).toString(); //Need test
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @param value String value to put on cell
     */
    public void setText(String gridId, String columnName, int row, String value){
        standart.isExisting(gridId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", gridId).toDispatch());
        Variant[] arg = new Variant[3];
        arg[0] = new Variant(row);
        arg[1] = new Variant(columnName);
        arg[2] = new Variant(value);
        standart.obj.invoke("ModifyCell", arg);
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @return Int value of cell
     */
    public int getInt(String gridId, String columnName, int row){
        return numberConverter.getInt(getText(gridId, columnName, row));
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @param value Int value to put on cell
     */
    @Deprecated
    public void setInt(String gridId, String columnName, int row, int value){
        setText(gridId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @return Float value of cell
     */
    public float getFloat(String gridId, String columnName, int row){
        return numberConverter.getFloat(getText(gridId, columnName, row));
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @param value Float value to put on cell
     */
    @Deprecated
    public void setFloat(String gridId, String columnName, int row, float value){
        setText(gridId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @return Long value of cell
     */
    public long getLong(String gridId, String columnName, int row){
        return numberConverter.getLong(getText(gridId, columnName, row));
    }

    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @param value Long value to put on cell
     */
    @Deprecated
    public void setLong(String gridId, String columnName, int row, long value){
        setText(gridId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param value Any type of number value to put in
     */
    public <T> void setCellNumberValue(String tableId, String columnName, int row, T value){
        setText(tableId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param gridId GUI ID element
     * Select all grid lines
     */
    public void selectAll(String gridId){
        standart.isExisting(gridId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", gridId).toDispatch());
        standart.obj.invoke("SelectAll");
    }

    /**
     * @param gridId GUI ID element
     * @param row Row to select
     */
    public void selectRow(String gridId, int row){
        standart.isExisting(gridId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", gridId).toDispatch());
        standart.obj.setProperty("SelectedRows", String.valueOf(row));
    }

    /**
     * @param tableId GUI element ID
     * @param column Name collected in SAP script
     * @param searchTerm Term to search
     * @return First row that contains searchTerm, or -1 for false return
     */
    public int searchRowByValue(String tableId, String column, String searchTerm){
        for (int i = 0; i < getTotalRows(tableId); i++) {
            if (getText(tableId, column, i).equals(searchTerm)){
                return i;
            }
        }
        return -1;
    }


    /**
     * @param gridId GUI ID element
     * @param columnName Name collected in SAP script
     * @param row Row in matrix of Grid
     * @param option True of false
     */
    public void setCheckBox(String gridId, String columnName, int row, boolean option){
        standart.isExisting(gridId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", gridId).toDispatch());
        Variant[] arg = new Variant[3];
        arg[0] = new Variant(row);
        arg[1] = new Variant(columnName);
        arg[2] = new Variant(option);
        standart.obj.invoke("ModifyCheckBox", arg);
    }

}
