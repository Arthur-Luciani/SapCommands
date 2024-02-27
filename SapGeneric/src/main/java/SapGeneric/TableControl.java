package SapGeneric;

import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;

@Service
public class TableControl{

    /*
        Future ideas:
            -ComboBoxSelection
     */

    @Autowired
    private SapMessenger sapMessenger;

    @Autowired
    private NumberConverter numberConverter;

    /**
     * Returns index of column
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @return The index of column in table matrix
     */
    public int getColumnIndex(String tableId, String columnName){
        sapMessenger.isExisting(tableId);
        ActiveXComponent currentObj;
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", tableId).toDispatch());
        Dispatch dispatch = Dispatch.call(sapMessenger.obj, "Columns").toDispatch();
        int count = Dispatch.call(dispatch, "count").toInt();


        for (int i = 0; i < count; i++) {
            currentObj = new ActiveXComponent(sapMessenger.obj.invoke("Columns", i).toDispatch());
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
        sapMessenger.isExisting(tableId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", tableId).toDispatch());
        ActiveXComponent scrollbarComponent = sapMessenger.obj.getPropertyAsComponent("VerticalScrollbar");
        return scrollbarComponent.getPropertyAsInt("Maximum") +1;
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @return ActiveXComponent of cell object
     */
    public ActiveXComponent getCellTableControlObj(String tableId, String columnName, int row){
        sapMessenger.isExisting(tableId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", tableId).toDispatch());
        int columnIndex = getColumnIndex(tableId, columnName);
        int visibleRow = sapMessenger.obj.getPropertyAsInt("VisibleRowCount");
        ActiveXComponent scrollbarComponent = sapMessenger.obj.getPropertyAsComponent("VerticalScrollbar");
        int multiplier = new BigDecimal((row/visibleRow)).setScale(RoundingMode.DOWN.ordinal()).intValue();
        int newActualScroll = (visibleRow * multiplier);
        scrollbarComponent.setProperty("Position", newActualScroll);
        sapMessenger.isExisting(tableId);
        sapMessenger.obj = new ActiveXComponent(sapMessenger.session.invoke("FindById", tableId).toDispatch());
        return new ActiveXComponent(sapMessenger.obj.invoke("getCell", row - newActualScroll, columnIndex).toDispatch());
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @return String value of cell
     */
    public String getCellStringValue(String tableId, String columnName, int row){
        sapMessenger.isExisting(tableId);
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
        sapMessenger.isExisting(tableId);
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
    @Deprecated
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
    @Deprecated
    public void setCellFloatValue(String tableId, String columnName, int row, float value){
        setCellStringValue(tableId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @return Long value of cell
     */
    public long getCellLongValue(String tableId, String columnName, int row){
        return numberConverter.getLong(getCellStringValue(tableId, columnName, row));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param value Any type of number value to put in
     */
    public <T> void setCellNumberValue(String tableId, String columnName, int row, T value){
        setCellStringValue(tableId, columnName, row, numberConverter.getString(value));
    }

    /**
     * @param tableId GUI element ID
     * @param columnName Title to search
     * @param row Row in matrix of table
     * @param value Exact value to put on ComboBox
     */
    public void setCellComboBoxValue(String tableId, String columnName, int row, String value){
        sapMessenger.isExisting(tableId);
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        currentObj.setProperty("value", value);
    }

    public void setCellComboBoxKey(String tableId, String columnName, int row, String key){
        sapMessenger.isExisting(tableId);
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        currentObj.setProperty("key", key);
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
     * @param row Row to select
     * @param option True or false
     */
    public void selectRow(String tableId, int row, Boolean option){
        sapMessenger.isExisting(tableId);
        sapMessenger.obj = new ActiveXComponent(this.sapMessenger.session.invoke("FindById", tableId).toDispatch());
        ActiveXComponent currentObj = new ActiveXComponent(this.sapMessenger.obj.invoke("GetAbsoluteRow", row).toDispatch());
        currentObj.setProperty("selected",option);
    }

    /**
     * @param tableId GUI element ID
     * @param column Column that contains searchTerm
     * @param searchTerm Term to search
     * @return First row that contains searchTerm, or -1 for false return
     */
    public int searchRowByValue(String tableId, String column, HashSet<String> searchTerm){
        for (int i = 0; i < getTotalRows(tableId); i++) {
            if (searchTerm.contains(getCellStringValue(tableId, column, i))){
                return i;
            }
        }
        return -1;
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

    public void setFocus(String tableId, String columnName, int row){
        ActiveXComponent currentObj = getCellTableControlObj(tableId, columnName, row);
        currentObj.invoke("SetFocus");
    }

}
