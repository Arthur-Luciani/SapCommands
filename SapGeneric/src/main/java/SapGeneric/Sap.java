package SapGeneric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sap {

    @Autowired
    public Basic basic;

    public BasicKey basicKey;

    public ComboBox comboBox;

    public GridControl gridControl;

    @Autowired
    public OkCode okCode;

    public TableControl tableControl;

    public UserArea userArea;

}
