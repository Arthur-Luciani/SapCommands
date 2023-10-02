package tests;


import Conn.SapConn;
import SapGeneric.Keys;
import SapGeneric.Sap;
import SapGeneric.Standart;
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

        int a = sap.tableControl.searchRowByValue(tblId, "TpC.", new HashSet<>(Arrays.asList("ICVA")));
        System.out.println(a);

    }


}
