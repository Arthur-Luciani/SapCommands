package com.SapController.SapGeneric;

import SapGeneric.Keys;
import SapGeneric.Sap;

public class ControllerTest {

    Sap sap = new Sap();

    public void testOk(){

        sap.okCode.setOkCode("/nYOTC_E10284_CTRC");
        sap.basic.setText("wnd[0]/usr/ctxtP_VKORG", "BR11");
        sap.basic.setText("wnd[0]/usr/ctxtP_BRANCH", "0012");
        sap.basic.setText("wnd[0]/usr/ctxtP_TKNUM", "100183198");
        sap.basic.setText("wnd[0]/usr/txtP_NFTYPE", "YA");
        sap.basicKey.keyBoardPress(Keys.F8);
        int a = sap.gridControl.getTotalRows("wnd[0]/usr/cntlGRID1/shellcont/shell");
        System.out.println(a +"\n");
        for (int i = 0; i < a; i++) {
            System.out.println(sap.gridControl.getText("wnd[0]/usr/cntlGRID1/shellcont/shell", "LBLNI", i));
            System.out.println(sap.gridControl.getFloat("wnd[0]/usr/cntlGRID1/shellcont/shell", "KZWI3", i));
            sap.gridControl.setText("wnd[0]/usr/cntlGRID1/shellcont/shell", "NETPR1", i, "1");

            sap.gridControl.setCheckBox("wnd[0]/usr/cntlGRID1/shellcont/shell", "CHKBOX", i, true);
        }
    }

    public void testMiro(){
        sap.okCode.setOkCode("/nMIRO");
        sap.basic.setText("wnd[0]/usr/subHEADER_AND_ITEMS:SAPLMR1M:6005/tabsHEADER/tabpHEADER_TOTAL/ssubHEADER_SCREEN:SAPLFDCB:0010/ctxtINVFO-BLDAT",
                "04.05.2023");
        sap.basic.setText("wnd[0]/usr/subHEADER_AND_ITEMS:SAPLMR1M:6005/tabsHEADER/tabpHEADER_TOTAL/ssubHEADER_SCREEN:SAPLFDCB:0010/txtINVFO-XBLNR",
                "123");

        //sap.basicKey.buttonPress("wnd[0]/usr/subHEADER_AND_ITEMS:SAPLMR1M:6005/tabsHEADER/tabpHEADER_FI");

        sap.basicKey.buttonSelect("wnd[0]/usr/subHEADER_AND_ITEMS:SAPLMR1M:6005/tabsHEADER/tabpHEADER_NOTE");
        sap.basicKey.buttonPress("wnd[0]/tbar[1]/btn[6]");
    }

}
