package tests;


import Conn.SapConn;
import SapGeneric.Keys;
import SapGeneric.Sap;
import SapGeneric.Standart;
import Utils.NumberConverter;
import com.jacob.activeX.ActiveXComponent;
import org.junit.Test;

import java.util.*;

class Va01Id{
    public static String userAreaUnidadeVendida = "wnd[1]/usr";
}


public class testsClass{

    @Test
    public void firstTest(){
        String userArea = "wnd[1]/usr";
        String tblId = "wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\06/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN";

        Sap sap = new Sap();

        NumberConverter converter = new NumberConverter();

        sap.okCode.setOkCode("/nVA03");

        System.out.println(converter.getInt("12,93654600"));

    }


}
