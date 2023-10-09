package SapGeneric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sap {

    @Autowired
    public Basic basic;

    @Autowired
    public BasicKey basicKey;
    @Autowired
    public ComboBox comboBox;
    @Autowired
    public GridControl gridControl;

    @Autowired
    public OkCode okCode;
    @Autowired
    public TableControl tableControl;
    @Autowired
    public UserArea userArea;

}
