package SapGeneric;

import org.springframework.beans.factory.annotation.Autowired;

public class SapGeneric {

    @Autowired
    Basic basic;
    @Autowired
    BasicBtns basicBtns;
    @Autowired
    ComboBox comboBox;
    @Autowired
    GridControl gridControl;
    @Autowired
    OkCode okCode;
    @Autowired
    TableControl tableControl;

}
