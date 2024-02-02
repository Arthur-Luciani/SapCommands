package SapGeneric;

import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserArea {

    @Autowired
    private Standart standart;

    @Autowired
    private NumberConverter numberConverter;

    /**
     * @param areaId GUI element ID
     * @return ActiveXComponent of userArea children
     */
    public ActiveXComponent getChildren(String areaId){
        standart.isExisting(areaId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", areaId).toDispatch());
        return standart.obj.getPropertyAsComponent("Children");
    }

    /**
     * @param children Children GuiObj of GuiArea
     * @param element Index of children
     * @return String value of this children component
     */
    public String getChildrenText(ActiveXComponent children, int element){
        Variant[] arg = new Variant[1];
        arg[0] = new Variant(element);
        ActiveXComponent childrenComponent = children.invokeGetComponent("Item", arg);
        return childrenComponent.getPropertyAsString("Text");
    }

    /**
     * @param children Children GuiObj of GuiArea
     * @param element Index of children
     * @return Partial ID of children
     */
    public String getChildrenId(ActiveXComponent children, int element){
        Variant[] arg = new Variant[1];
        arg[0] = new Variant(element);
        ActiveXComponent childrenComponent = children.invokeGetComponent("Item", arg);
        String childrenId = childrenComponent.getPropertyAsString("Id");
        childrenId = childrenId.substring(childrenId.lastIndexOf("ses"));
        return childrenId.substring(childrenId.indexOf("/") + 1);
    }

    public int getTotalChildren(ActiveXComponent children){
        return children.getPropertyAsInt("Count");
    }

    /**
     * Simple search method, util to search unique elements
     * @param areaId GUI element ID
     * @param searchText Text to search
     * @return Element ID or empty String if it doesn't find any match
     */
    public String getElementId(String areaId, String searchText){
        ActiveXComponent children = getChildren(areaId);
        for (int i = 0; i < getTotalChildren(children) -1; i++) {
            if (searchText.equals(getChildrenText(children, i))){
                return getChildrenId(children, i);
            }
        }
        return "";
    }

    /**
     * @param elementId GUI element ID
     * @return Column index or -1 if it doesn't find any match
     */
    public int getColumnIndex(String elementId){
        elementId = elementId.substring(elementId.lastIndexOf("[") + 1);
        int i = elementId.length();
        while (i > 0){
            try {
                return Integer.parseInt(elementId.substring(0, i));
            }catch (NumberFormatException e){
                i--;
            }
        }
        return -1;
    }

    /**
     * @param areaId GUI element ID
     * @param column Column to search
     * @return Index of column or -1 if it doesn't find any match
     */
    public int searchColumnIndex(String areaId, String column){
        return getColumnIndex(getElementId(areaId, column));
    }

    /**
     * @param elementId GUI element ID
     * @return Row index or -1 if it doesn't find any match
     */
    public int getRowIndex(String elementId){
        elementId = elementId.substring(elementId.lastIndexOf(",") + 1);
        int i = elementId.length();
        while (i > 0){
            try {
                return Integer.parseInt(elementId.substring(0, i));
            }catch (NumberFormatException e){
                i--;
            }
        }
        return -1;
    }


    /**
     * @param areaId GUI element ID
     * @param columnTitle Column that row value will be search
     * @param searchText String value to search
     * @return Row index of search or -1 if it doesn't find any match
     */
    public int getRowIndexSearchingByColumn(String areaId, String columnTitle, String searchText){
        int column = searchColumnIndex(areaId, columnTitle);
        ActiveXComponent children = getChildren(areaId);

        for (int i = 0; i < children.getPropertyAsInt("Count"); i++) {
            String childrenId = getChildrenId(children, i);
            if (searchText.equals(getChildrenText(children, i)) && getColumnIndex(childrenId) == column){
                return getRowIndex(childrenId);
            }
        }
        return -1;
    }

    /**
     * @param areaId GUI element ID
     * @return Total rows of table without count header
     */
    public int getTotalRows(String areaId){
        ActiveXComponent children = getChildren(areaId);
        int totalChildren = getTotalChildren(children);
        int column = 1;
        while (true){
            try {
                getText(areaId, 1, ((totalChildren-column) / column));
                return ((totalChildren - 1) / column);
            } catch (Exception e){
                column++;
            }
        }
    }

    /**
     * @param areaId GUI element ID
     * @param column Column of element
     * @param row Row of element
     * @return String value from element
     */
    public String getText(String areaId, int column, int row){
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById" ,getAreaId(areaId, column, row)).toDispatch());
        return standart.obj.getPropertyAsString("Text");
    }

    /**
     * @param areaId GUI element ID
     * @param column Column of element
     * @param row Row of element
     * @return Integer value from element
     */
    public int getInt(String areaId, int column, int row){
        return numberConverter.getInt(getText(areaId, column, row));
    }

    /**
     * @param areaId GUI element ID
     * @param column Column of element
     * @param row Row of element
     * @return Long value from element
     */
    public long getLong(String areaId, int column, int row){
        return numberConverter.getLong(getText(areaId, column, row));
    }

    /**
     * @param areaId GUI element ID
     * @param row Row to select
     */
    public void selectRow(String areaId, int row) {
        this.standart.isExisting(areaId);
        this.standart.obj = new ActiveXComponent(this.standart.session.invoke("FindById", areaId).toDispatch());
        this.standart.obj.setProperty("SelectedRows", String.valueOf(row));
    }

    /**
     * @param areaId GUI element ID
     * @param rows Rows to select
     */
    public void selectRows(String areaId, ArrayList<Integer> rows){
        standart.isExisting(areaId);
        standart.obj = new ActiveXComponent(standart.session.invoke("FindById", areaId).toDispatch());
        String arg = rows.toString();
        arg = arg.replace("[[]]","" );
        standart.obj.setProperty("SelectedRows", arg);
    }

    /**
     * @param areaId GUI element ID
     * @param column Column of element
     * @param row Row of element
     * @return String with Sap ID
     */
    private String getAreaId(String areaId, int column, int row){
        return (areaId + "/lbl[" + String.valueOf(column) + "," + String.valueOf(row) + "]");
    }
}
