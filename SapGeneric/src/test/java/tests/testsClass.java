package tests;


import SapGeneric.Sap;
import com.SapController.ControllerTest;
import org.junit.Test;


public class testsClass{

    @Test
    public void firstTest(){
        Sap sap = new Sap();

        sap.basic.isExisting("wnd[2]/usr/txtMESSTXT1");
    }
}
