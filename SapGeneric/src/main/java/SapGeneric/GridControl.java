package SapGeneric;

import Conn.SapConn;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.util.Arrays;

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
    public void setFloat(String gridId, String columnName, int row, float value){
        setText(gridId, columnName, row, numberConverter.getString(value));
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
