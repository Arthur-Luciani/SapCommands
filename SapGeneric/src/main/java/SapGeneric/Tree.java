package SapGeneric;

import EnumSapController.SearchCriteria;
import EnumSapController.SearchOrder;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Tree {
    @Autowired
    SapMessenger sapMessenger;
    @Autowired
    NumberConverter numberConverter;

    /**
     * @param treeId GUI element ID
     * @return ActiveXComponent of NodeKeyValues
     */
    private ActiveXComponent getTree(String treeId) {
        sapMessenger.isExisting(treeId);
        return new ActiveXComponent(sapMessenger.session.invoke("FindById", treeId).toDispatch());
    }

    /**
     * @param tree ActiveComponent of tree element
     * @return ActiveXComponent of NodeKeyValues
     */
    private ActiveXComponent getNodeKeys(ActiveXComponent tree) {
        return tree.invokeGetComponent("GetAllNodeKeys");
    }

    /**
     * @param nodeKeys ActiveXComponent of NodeKeyValues
     * @return Total Rows in NodeKey of Tree element
     */
    private int getTotalRow(ActiveXComponent nodeKeys) {
        return nodeKeys.getPropertyAsInt("Count");
    }


    /**
     * Set true of false in checkbox on specific node that contains searchString value
     * @param treeId GUI element ID
     * @param searchString String to search
     * @param option Boolean value for checkbox
     */
    public void setCheckBox(String treeId, String searchString, boolean option) {
        ActiveXComponent tree = getTree(treeId);
        ActiveXComponent nodeKeyValues = getNodeKeys(tree);
        int totalRows = getTotalRow(nodeKeyValues);
        for (int i = 0; i < totalRows-1; i++) {
            Variant[] arg = new Variant[1];
            arg[0] = new Variant(i);
            String nodeKey = nodeKeyValues.invoke("Item", arg).getString();

            arg = new Variant[2];
            arg[0] = new Variant(nodeKey);
            arg[1] = new Variant("&Hierarchy");
            String itemText = tree.invoke("GetItemText", arg).getString();

            if (itemText.contains(searchString)) {
                arg = new Variant[1];
                arg[0] = new Variant(nodeKey);
                tree.invoke("SelectNode", arg);
                arg = new Variant[3];
                arg[0] = new Variant(nodeKey);
                arg[1] = new Variant("&Hierarchy");
                arg[2] = new Variant(option);
                tree.invoke("ChangeCheckBox", arg);
                i = totalRows;
            }
        }
    }

    /**
     * Select specific node that contains searchString value
     * @param treeId GUI element ID
     * @param searchString String to search
     */
    public boolean selectRow(String treeId, String searchString, SearchOrder order, SearchCriteria criteria) {
        ActiveXComponent tree = getTree(treeId);
        ActiveXComponent nodeKeyValues = getNodeKeys(tree);
        int totalRows = getTotalRow(nodeKeyValues);

        switch (order) {
            case ASCENDING -> {
                for (int i = 0; i < totalRows-1; i++) {
                    Variant[] arg = new Variant[1];
                    arg[0] = new Variant(i);
                    String nodeKey = nodeKeyValues.invoke("Item", arg).getString();

                    arg = new Variant[2];
                    arg[0] = new Variant(nodeKey);
                    arg[1] = new Variant("&Hierarchy");
                    String itemText = tree.invoke("GetItemText", arg).getString();

                    switch (criteria) {
                        case EQUALS -> {
                            if (itemText.equals(searchString)) {
                                arg = new Variant[1];
                                arg[0] = new Variant(nodeKey);
                                tree.invoke("SelectNode", arg);
                                return true;
                            }
                        }
                        case CONTAINS -> {
                            if (itemText.contains(searchString)) {
                                arg = new Variant[1];
                                arg[0] = new Variant(nodeKey);
                                tree.invoke("SelectNode", arg);
                                return true;
                            }
                        }
                    }
                }
            }
            case DESCENDING -> {
                for (int i = totalRows-1; i > 0; i--) {
                    Variant[] arg = new Variant[1];
                    arg[0] = new Variant(i);
                    String nodeKey = nodeKeyValues.invoke("Item", arg).getString();

                    arg = new Variant[2];
                    arg[0] = new Variant(nodeKey);
                    arg[1] = new Variant("&Hierarchy");
                    String itemText = tree.invoke("GetItemText", arg).getString();

                    switch (criteria) {
                        case EQUALS -> {
                            if (itemText.equals(searchString)) {
                                arg = new Variant[1];
                                arg[0] = new Variant(nodeKey);
                                tree.invoke("SelectNode", arg);
                                return true;
                            }
                        }
                        case CONTAINS -> {
                            if (itemText.contains(searchString)) {
                                arg = new Variant[1];
                                arg[0] = new Variant(nodeKey);
                                tree.invoke("SelectNode", arg);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public int getRowIndex(String treeId, String searchString, SearchOrder order, SearchCriteria criteria) {
        ActiveXComponent tree = getTree(treeId);
        ActiveXComponent nodeKeyValues = getNodeKeys(tree);
        int totalRows = getTotalRow(nodeKeyValues);

        switch (order) {
            case ASCENDING -> {
                for (int i = 0; i < totalRows-1; i++) {
                    Variant[] arg = new Variant[1];
                    arg[0] = new Variant(i);
                    String nodeKey = nodeKeyValues.invoke("Item", arg).getString();

                    arg = new Variant[2];
                    arg[0] = new Variant(nodeKey);
                    arg[1] = new Variant("&Hierarchy");
                    String itemText = tree.invoke("GetItemText", arg).getString();

                    switch (criteria) {
                        case EQUALS -> {
                            if (itemText.equals(searchString)) {
                                return i;
                            }
                        }
                        case CONTAINS -> {
                            if (itemText.contains(searchString)) {
                                return i;
                            }
                        }
                    }
                }
            }
            case DESCENDING -> {
                for (int i = totalRows-1; i > 0; i--) {
                    Variant[] arg = new Variant[1];
                    arg[0] = new Variant(i);
                    String nodeKey = nodeKeyValues.invoke("Item", arg).getString();

                    arg = new Variant[2];
                    arg[0] = new Variant(nodeKey);
                    arg[1] = new Variant("&Hierarchy");
                    String itemText = tree.invoke("GetItemText", arg).getString();

                    switch (criteria) {
                        case EQUALS -> {
                            if (itemText.equals(searchString)) {
                                return i;
                            }
                        }
                        case CONTAINS -> {
                            if (itemText.contains(searchString)) {
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }





}

