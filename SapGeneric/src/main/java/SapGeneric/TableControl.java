package SapGeneric;

import Conn.SapConn;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.Dispatch;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TableControl{

    /*
        Future ideas:
            -ComboBoxSelection
     */

    private Standart standart = new SapConn().getStandart(0);

    private NumberConverter numberConverter = new NumberConverter();

    /**
     * Returns index of column
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @return The index of column in table matrix
     */
    public int getColumnIndex(String tableId, String columnName){
        standart.isExisting(tableId);
        ActiveXComponent currentObj;
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", tableId).toDispatch());
        Dispatch dispatch = Dispatch.call(standart.obj, "Columns").toDispatch();
        int count = Dispatch.call(dispatch, "count").toInt();


        for (int i = 0; i < count; i++) {
            currentObj = new ActiveXComponent(standart.obj.invoke("Columns", i).toDispatch());
            String a = currentObj.getProperty("title").toString().trim();
            if(currentObj.getProperty("title").toString().trim().equals(columnName)){
                return i;
            }
        }
        return -1;
    }

    /**
     * @param tableId GUI element ID
     * @return Total rows of table includes invisible rows including empty rows
     */
    public int getTotalRows(String tableId){
        standart.isExisting(tableId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", tableId).toDispatch());
        ActiveXComponent scrollbarComponent = standart.obj.getPropertyAsComponent("VerticalScrollbar");
        return scrollbarComponent.getPropertyAsInt("Maximum") +1;
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @return ActiveXComponent of cell object
     */
    public ActiveXComponent getCellTableControlObj(String tableId, String columnName, int row){
        standart.isExisting(tableId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", tableId).toDispatch());
        int columnIndex = getColumnIndex(tableId, columnName);
        int visibleRow = standart.obj.getPropertyAsInt("VisibleRowCount");
        ActiveXComponent scrollbarComponent = standart.obj.getPropertyAsComponent("VerticalScrollbar");
        int multiplier = new BigDecimal((row/visibleRow)).setScale(RoundingMode.DOWN.ordinal()).intValue();
        int newActualScroll = (visibleRow * multiplier);
        scrollbarComponent.setProperty("Position", newActualScroll);
        standart.isExisting(tableId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", tableId).toDispatch());
        return new ActiveXComponent(standart.obj.invoke("getCell", row - newActualScroll, columnIndex).toDispatch());
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @return String value of cell
     */
    public String getCellStringValue(String tableId, String columnName, int row){
        standart.isExisting(tableId);
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        return currentObj.getPropertyAsString("text");
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param value String value to put on cell
     */
    public void setCellStringValue(String tableId, String columnName, int row, String value){
        standart.isExisting(tableId);
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        currentObj.setProperty("text", value);
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @return Int value of cell
     */
    public int getCellIntValue(String tableId, String columnName, int row){
        return numberConverter.getInt(getCellStringValue(tableId, columnName, row));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param value Int value to put on cell
     */
    public void setCellIntValue(String tableId, String columnName, int row, String value){
        setCellStringValue(tableId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @return Float value of cell
     */
    public Float getCellFloatValue(String tableId, String columnName, int row){
        return numberConverter.getFloat(getCellStringValue(tableId, columnName, row));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param value Float value to put on cell
     */
    public void setCellFloatValue(String tableId, String columnName, int row, float value){
        setCellStringValue(tableId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param value Exact value to put on ComboBox
     */
    public void setCellComboBoxValue(String tableId, String columnName, int row, String value){
        standart.isExisting(tableId);
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        currentObj.setProperty("value", value);
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param option True or false
     */
    public void setCellCheckBoxValue(String tableId, String columnName, int row, boolean option){
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        currentObj.invoke("setFocus");
        if (option)
            currentObj.setProperty("selected", 1);
        else
            currentObj.setProperty("selected", 0);
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     */
    public void pressCellButton(String tableId, String columnName, int row){
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        Dispatch.call(currentObj, "press");
    }

}