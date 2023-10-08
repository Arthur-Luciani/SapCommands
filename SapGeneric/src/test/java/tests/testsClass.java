package tests;


import Conn.SapConn;
import SapGeneric.Keys;
import SapGeneric.Sap;
import SapGeneric.Standart;
import com.jacob.activeX.ActiveXComponent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;




public class testsClass{

    @Autowired
    Sap sap;

    @Test
    public void firstTest(){


        String userArea = "wnd[1]/usr";
        String tblId = "wnd[0]/usr/tabsTAXI_TABSTRIP_ITEM/tabpT\\06/ssubSUBSCREEN_BODY:SAPLV69A:6201/tblSAPLV69ATCTRL_KONDITIONEN";



        int a = sap.tableControl.searchRowByValue(tblId, "TpC.", new HashSet<>(Arrays.asList("ICVA")));
        System.out.println(a);

    }


}
